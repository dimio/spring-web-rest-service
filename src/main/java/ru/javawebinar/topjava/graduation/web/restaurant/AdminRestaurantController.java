package ru.javawebinar.topjava.graduation.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.graduation.model.Menu;
import ru.javawebinar.topjava.graduation.model.Restaurant;
import ru.javawebinar.topjava.graduation.service.RestaurantService;
import ru.javawebinar.topjava.graduation.util.exception.NotFoundException;

import javax.validation.Valid;
import java.net.URI;

import static ru.javawebinar.topjava.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.graduation.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(
    value = AdminRestaurantController.REST_URL,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String REST_URL = "/rest/admin/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService restaurantService;

    public AdminRestaurantController(RestaurantService service) {
        this.restaurantService = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        log.info("add restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant newRestaurant = restaurantService.add(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(REST_URL + "/{restaurantId}")
            .buildAndExpand(newRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newRestaurant);
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurant(@PathVariable int restaurantId, @Valid @RequestBody Restaurant restaurant) throws NotFoundException {
        log.info("update restaurant {} with id {}", restaurant, restaurantId);
        assureIdConsistent(restaurant, restaurantId);
        restaurantService.update(restaurant);
    }

    @DeleteMapping(value = "/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable int restaurantId) throws NotFoundException {
        log.info("delete restaurant {}", restaurantId);
        restaurantService.delete(restaurantId);
    }

    @PostMapping(value = "/{restaurantId}")
    public ResponseEntity<Menu> addMenu(@Valid @RequestBody Menu menu, @PathVariable int restaurantId) {
        log.info("add menu {} for restaurant {}", menu, restaurantId);
        checkNew(menu);
        Menu newMenu = restaurantService.addMenu(restaurantId, menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(REST_URL + '/' + restaurantId + "/{menuId}")
            .buildAndExpand(newMenu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newMenu);
    }

    @PutMapping(value = "/{restaurantId}/{menuId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateMenu(@RequestBody Menu menu, @PathVariable int restaurantId, @PathVariable int menuId)
        throws NotFoundException {
        log.info("update menu {} with id {} for restaurant {}", menu, menuId, restaurantId);
        assureIdConsistent(menu, menuId);
        restaurantService.updateMenu(restaurantId, menu);
    }

    @DeleteMapping(value = "/{restaurantId}/{menuId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable int restaurantId, @PathVariable int menuId) throws NotFoundException {
        log.info("delete menu {} for restaurant {}", menuId, restaurantId);
        restaurantService.deleteMenu(restaurantId, menuId);
    }
}