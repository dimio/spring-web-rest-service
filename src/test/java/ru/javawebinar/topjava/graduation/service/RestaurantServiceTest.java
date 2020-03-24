package ru.javawebinar.topjava.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.javawebinar.topjava.graduation.model.Restaurant;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import java.time.LocalDate;

import static ru.javawebinar.topjava.graduation.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractControllerTest {

    @Autowired
    RestaurantService service;

    @Test
    void add() {
        Restaurant newRestaurant = new Restaurant(null, "New restaurant");
        service.add(newRestaurant);
        assertMatchRestaurant(service.getAll(Sort.by(Sort.Direction.ASC, "id")),
            RESTAURANT_1, RESTAURANT_2, RESTAURANT_3, newRestaurant);
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_2_ID);
        assertMatchRestaurant(service.getAll(Sort.by(Sort.Direction.ASC, "id")), RESTAURANT_1, RESTAURANT_3);
    }

    @Test
    void update() {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatchRestaurant(service.get(RESTAURANT_1_ID), updated);
    }

    @Test
    void get() {
        assertMatchRestaurant(service.get(RESTAURANT_1_ID), RESTAURANT_1);
    }

    @Test
    void getByName() {
        Restaurant restaurantByName = service.getByName(service.get(RESTAURANT_2_ID).getName());
        assertMatchRestaurant(restaurantByName, RESTAURANT_2);
    }

    @Test
    void getAll() {
        assertMatchRestaurant(service.getAll(Sort.by(Sort.Direction.ASC, "id")),
            RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    }


    @Test
    void getAllWithMenusForDate() {
        assertMatchRestaurant(service
                .getAllWithMenusAndMealsForDate(LocalDate.of(2019, 6, 27)),
            RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    void getAllWithActualMenus() {
        assertMatchRestaurant(service.getAllWithMenusAndMealsForDate(LocalDate.now()), RESTAURANT_1, RESTAURANT_2);
    }
}
