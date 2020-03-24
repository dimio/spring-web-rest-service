package ru.javawebinar.topjava.graduation.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.graduation.model.Meal;
import ru.javawebinar.topjava.graduation.model.Menu;
import ru.javawebinar.topjava.graduation.model.Restaurant;
import ru.javawebinar.topjava.graduation.service.MealService;
import ru.javawebinar.topjava.graduation.service.MenuService;
import ru.javawebinar.topjava.graduation.service.RestaurantService;
import ru.javawebinar.topjava.graduation.util.exception.NotFoundException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.javawebinar.topjava.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.graduation.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(
    value = AdminRestaurantController.REST_URL,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String REST_URL = "/rest/admin/restaurants";
    public static final String REST_URL_MENUS = "/{restaurantId}/menus";
    public static final String REST_URL_MEALS = REST_URL_MENUS + "/{menuId}/meals";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final MealService mealService;

    public AdminRestaurantController(RestaurantService service, MenuService menuService, MealService mealService) {
        this.restaurantService = service;
        this.menuService = menuService;
        this.mealService = mealService;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public List<Restaurant> getAll
        (@RequestParam(value = "sort", required = false) String sortBy,
         @RequestParam(value = "direction", defaultValue = "ASC", required = false) String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Sort sort = (sortBy == null) ? Sort.unsorted() : Sort.by(direction, sortBy);
        log.info("get all restaurants sorted to {}", sort);
        return restaurantService.getAll(sort);
    }

    @PostMapping
    public ResponseEntity<Restaurant> add(@Valid @RequestBody Restaurant restaurant) {
        log.info("add a restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant newRestaurant = restaurantService.add(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(REST_URL + "/{restaurantId}")
            .buildAndExpand(newRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newRestaurant);
    }

    @PutMapping("/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "updated")
    public void update(@PathVariable int restaurantId, @Valid @RequestBody Restaurant restaurant) throws NotFoundException {
        log.info("update restaurant {} with an id {}", restaurant, restaurantId);
        assureIdConsistent(restaurant, restaurantId);
        restaurantService.update(restaurant);
    }

    @DeleteMapping(value = "/{restaurantId}", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "deleted")
    public void delete(@PathVariable int restaurantId) throws NotFoundException {
        log.info("delete a restaurant {}", restaurantId);
        restaurantService.delete(restaurantId);
    }

    @PostMapping(REST_URL_MENUS)
    public ResponseEntity<Menu> addMenu(@Valid @RequestBody Menu menu, @PathVariable int restaurantId) {
        log.info("add a menu {} for a restaurant {}", menu, restaurantId);
        checkNew(menu);
        Menu newMenu = menuService.add(restaurantId, menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(REST_URL + REST_URL_MENUS + "/{menuId}")
            .buildAndExpand(restaurantId, newMenu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newMenu);
    }

    @PutMapping(REST_URL_MENUS + "/{menuId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "updated")
    public void updateMenu(@Valid @RequestBody Menu menu, @PathVariable int restaurantId, @PathVariable int menuId)
        throws NotFoundException {
        log.info("update menu {} with an id {} for a restaurant {}", menu, menuId, restaurantId);
        assureIdConsistent(menu, menuId);
        menuService.update(restaurantId, menu);
    }

    @DeleteMapping(value = REST_URL_MENUS + "/{menuId}", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "deleted")
    public void deleteMenu(@PathVariable int restaurantId, @PathVariable int menuId) throws NotFoundException {
        log.info("delete a menu {} for a restaurant {}", menuId, restaurantId);
        menuService.delete(restaurantId, menuId);
    }

    @PostMapping(REST_URL_MEALS)
    public ResponseEntity<Meal> addMeal(@Valid @RequestBody Meal meal,
                                        @PathVariable int restaurantId,
                                        @PathVariable int menuId) {
        log.info("add a meal {} in a menu {} for a restaurant {}", meal, menuId, restaurantId);
        checkNew(meal);
        Meal newMeal = mealService.add(restaurantId, menuId, meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(REST_URL + REST_URL_MEALS + "/{mealId}")
            .buildAndExpand(restaurantId, menuId, newMeal.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newMeal);
    }

    @PutMapping(REST_URL_MEALS + "/{mealId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "updated")
    public void updateMeal(@Valid @RequestBody Meal meal,
                           @PathVariable int restaurantId,
                           @PathVariable int menuId,
                           @PathVariable int mealId)
        throws NotFoundException {
        log.info("update meal {} in a menu {} for a restaurant {}", meal, menuId, restaurantId);
        assureIdConsistent(meal, mealId);
        mealService.update(restaurantId, menuId, meal);
    }

    @DeleteMapping(value = REST_URL_MEALS + "/{mealId}", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "deleted")
    public void deleteMeal(@PathVariable int restaurantId,
                           @PathVariable int menuId,
                           @PathVariable int mealId) throws NotFoundException {
        log.info("delete a meal {} for a menu {} on a restaurant {}", mealId, menuId, restaurantId);
        mealService.delete(restaurantId, menuId, mealId);
    }
}
