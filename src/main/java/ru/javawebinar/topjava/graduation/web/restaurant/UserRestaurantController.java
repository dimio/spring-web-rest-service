package ru.javawebinar.topjava.graduation.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.graduation.model.Meal;
import ru.javawebinar.topjava.graduation.model.Menu;
import ru.javawebinar.topjava.graduation.model.Restaurant;
import ru.javawebinar.topjava.graduation.service.MealService;
import ru.javawebinar.topjava.graduation.service.MenuService;
import ru.javawebinar.topjava.graduation.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(
    value = UserRestaurantController.REST_URL,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController {

    public static final String REST_URL = "/rest/restaurants";
    public static final String REST_URL_MENUS = "/{restaurantId}/menus";
    public static final String REST_URL_MEALS =  REST_URL_MENUS + "/{menuId}/meals";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final MealService mealService;

    // TODO: 13.03.2020 add caching

    public UserRestaurantController(RestaurantService restaurantService, MenuService menuService, MealService mealService) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
        this.mealService = mealService;
    }

    /**
     * Get restaurants with actual menus (with meals) for today
     * @return List of restaurants, who contains menus actual for current date, with menus and meals
     * {@link Restaurant}
     */
    @GetMapping
    public List<Restaurant> getAllActual() {
        log.info("get all restaurants with actual menus for today");
        return restaurantService.getAllWithMenusAndMealsForDate(LocalDate.now());
    }

    /**
     * Get restaurant information by specified ID
     * @param restaurantId - ID (int) of restaurant
     * @return Restaurant entity with menus and meals {@link Restaurant}
     */
    @GetMapping("/{restaurantId}")
    public Restaurant getById(@PathVariable int restaurantId) {
        log.info("get restaurant {}", restaurantId);
        return restaurantService.get(restaurantId);
    }

    /**
     * Get restaurant information by specified name
     * @param name - name (String) of restaurant
     * @return Restaurant entity with menus and meals {@link Restaurant}
     */
    @GetMapping("/byName")
    public Restaurant getByName(@RequestParam String name) {
        log.info("get restaurant by name {}", name);
        return restaurantService.getByName(name);
    }

    /**
     * Get all menus (without meals) of a restaurant as array
     * @param restaurantId - ID (int) of restaurant
     * @return List of menus for a restaurant with specified restaurantId
     * {@link Menu}
     */
    @GetMapping(REST_URL_MENUS)
    public List<Menu> getMenus(@PathVariable int restaurantId) {
        log.info("get all menus for a restaurant {}", restaurantId);
        return menuService.getAllForRestaurant(restaurantId);
    }

    /**
     * Get menu (without meals) for restaurant
     * @param restaurantId - ID (int) of restaurant
     * @param menuId - ID (int) of menu
     * @return Menu entity with specified menuId for a restaurant with specified restaurantId
     * {@link Menu}
     */
    @GetMapping(REST_URL_MENUS  + "/{menuId}")
    public Menu getMenu(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("get menu {} for a restaurant {}", menuId, restaurantId);
        return menuService.getForRestaurant(restaurantId, menuId);
    }

    /**
     * Get all menu items only (i.e. meals) for a menu as array
     * @param menuId - ID (int) of restaurant
     * @return List of menu items for a menu with specified menuId
     * {@link Meal}
     */
    @GetMapping(REST_URL_MEALS)
    public List<Meal> getMeals(@PathVariable int menuId) {
        log.info("get all meals for a menu {}", menuId);
        return mealService.getAllForMenu(menuId);
    }

    /**
     * Get menu item (i.e. meal) for menu
     * @param menuId - ID (int) of menu
     * @param mealId - ID (int) of meal
     * @return Meal entity with specified mealId for a menu with specified menuId
     * {@link Meal}
     */
    @GetMapping(REST_URL_MEALS  + "/{mealId}")
    public Meal getMeal(@PathVariable int menuId, @PathVariable int mealId) {
        log.info("get meal {} for a menu {}", mealId, menuId);
        return mealService.getForMenu(menuId, mealId);
    }
}
