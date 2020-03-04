package ru.javawebinar.topjava.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.graduation.model.Menu;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import java.time.LocalDate;

import static ru.javawebinar.topjava.graduation.MenuTestData.*;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.RESTAURANT_1_ID;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.RESTAURANT_2_ID;

class MenuServiceTest extends AbstractControllerTest {

    @Autowired
    MenuService service;
    RestaurantService restaurantService;

    @Test
    void add() {
        service.add(RESTAURANT_1_ID, MENU_R2_D27);
        assertMatch(service.getAllForRestaurant(RESTAURANT_1_ID), MENU_R1_NOW, MENU_R1_D28, MENU_R1_D27, MENU_R2_D27);
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_1_ID, MENU_ID);
        assertMatch(service.getAllForRestaurant(RESTAURANT_1_ID), MENU_R1_NOW, MENU_R1_D28);
    }

    //    @Test
    //    void deleteNotFound() {
    //        service.delete(RESTAURANT_2_ID, MENU_ID);
    //        assertMatchRestaurant(service.getAll(Sort.by(Sort.Direction.ASC, "id")), RESTAURANT_1);
    //    }

    @Test
    void update() {
        Menu updated = new Menu(MENU_R1_D27);
        updated.setActual(LocalDate.now());
        service.update(RESTAURANT_1_ID, updated);
        assertMatch(service.getForRestaurant(RESTAURANT_1_ID, updated.getId()), updated);
    }

    //    @Test
    //    void updateNotFound() {
    //        service.delete(RESTAURANT_2_ID, MENU_ID);
    //        assertMatchRestaurant(service.getAll(Sort.by(Sort.Direction.ASC, "id")), RESTAURANT_1);
    //    }

    @Test
    void getForRestaurant() {
        assertMatch(service.getForRestaurant(RESTAURANT_1_ID, MENU_ID), MENU_R1_D27);
    }

    @Test
    void getForRestaurantAndDate() {
        assertMatch(service.getForRestaurantAndDate(RESTAURANT_2_ID, LocalDate.now()), MENU_R2_NOW);
    }

    @Test
    void getAllForRestaurant() {
        assertMatch(service.getAllForRestaurant(RESTAURANT_2_ID), RESTAURANT_2_MENUS);
    }
}
