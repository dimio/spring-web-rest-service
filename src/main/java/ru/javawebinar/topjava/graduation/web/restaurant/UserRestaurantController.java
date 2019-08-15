package ru.javawebinar.topjava.graduation.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.graduation.model.Menu;
import ru.javawebinar.topjava.graduation.model.Restaurant;
import ru.javawebinar.topjava.graduation.service.RestaurantService;
import ru.javawebinar.topjava.graduation.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(
    value = UserRestaurantController.REST_URL,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController {

    public static final String REST_URL = "/rest/profile/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService restaurantService;

    public UserRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/all")
    //public List<Restaurant> getAllRestaurants(@RequestParam(required = false) Sort sort) {
    public List<Restaurant> getAllRestaurants() {
        //converter needed
        //sort = (sort == null) ? Sort.unsorted() : sort;
        log.info("get all restaurants");
        return restaurantService.getAll(Sort.unsorted());
    }

    @GetMapping(value = "/{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable int restaurantId) {
        log.info("get restaurant {}", restaurantId);
        return restaurantService.get(restaurantId);
    }

    @GetMapping(value = "/{restaurantId}/menu")
    public List<Menu> getMenuForRestaurantForDate(@PathVariable int restaurantId,
                                                  @RequestParam(required = false)
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws NotFoundException {
        log.info("get menu for restaurant {} today", restaurantId);
        date = (date == null) ? LocalDate.now() : date;
        return restaurantService.getMenusForRestaurantBetweenDate(restaurantId, date, date);
    }
}
