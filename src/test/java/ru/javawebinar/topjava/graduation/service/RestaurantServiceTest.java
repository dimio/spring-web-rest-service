package ru.javawebinar.topjava.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.javawebinar.topjava.graduation.model.Menu;
import ru.javawebinar.topjava.graduation.model.Restaurant;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import java.time.LocalDate;

import static ru.javawebinar.topjava.graduation.MenuTestData.*;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractControllerTest {

    @Autowired
    RestaurantService service;

    @Test
    void add() {
        Restaurant newRestaurant = new Restaurant(null, "New restaurant");
        service.add(newRestaurant);
        assertMatchRestaurant(service.getAll(Sort.by(Sort.Direction.ASC, "id")),
            RESTAURANT_1, RESTAURANT_2, newRestaurant);
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_2_ID);
        assertMatchRestaurant(service.getAll(Sort.by(Sort.Direction.ASC, "id")), RESTAURANT_1);
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
        Restaurant restaurant = service.get(RESTAURANT_1_ID);
        assertMatchRestaurant(restaurant, RESTAURANT_1);
        assertMatch(restaurant.getLunchMenus(), MENU_R1_D28, MENU_R1_D27);
    }

    @Test
    void getAll() {
        assertMatchRestaurant(service.getAll(Sort.by(Sort.Direction.ASC, "id")),
            RESTAURANT_1, RESTAURANT_2);
    }

    @Test
    void addMenu() {
        service.addMenu(RESTAURANT_1_ID, MENU_R2_D27);
        assertMatch(service.getAllMenusForRestaurant(RESTAURANT_1_ID), MENU_R1_D28, MENU_R1_D27, MENU_R2_D27);
    }

    @Test
    void deleteMenu() {
        service.deleteMenu(RESTAURANT_1_ID, MENU_ID);
        assertMatch(service.getAllMenusForRestaurant(RESTAURANT_1_ID), MENU_R1_D28);
    }

    @Test
    void updateMenu() {
        Menu updated = new Menu(MENU_R1_D27);
        updated.setName("UpdatedName");
        updated.setRestaurant(RESTAURANT_1);
        service.updateMenu(RESTAURANT_1_ID, updated);
        assertMatch(service.getMenuForRestaurant(RESTAURANT_1_ID, MENU_ID), updated);
    }

    @Test
    void getMenuForRestaurant() {
        assertMatch(service.getMenuForRestaurant(RESTAURANT_1_ID, MENU_ID), MENU_R1_D27);
    }

    @Test
    void getMenusForRestaurantBetweenDate() {
        LocalDate startDate = LocalDate.of(2019, 6, 20);
        LocalDate endDate = LocalDate.of(2019, 6, 28);
        assertMatch(service.getMenusForRestaurantBetweenDate(RESTAURANT_1_ID, startDate, endDate), MENU_R1_D27);
    }

    @Test
    void getAllMenusForRestaurant() {
        assertMatch(service.getAllMenusForRestaurant(RESTAURANT_2_ID), MENU_R2_D28, MENU_R2_D27);
    }
}