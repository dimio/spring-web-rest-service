package ru.javawebinar.topjava.graduation.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.graduation.model.Meal;
import ru.javawebinar.topjava.graduation.model.Menu;
import ru.javawebinar.topjava.graduation.model.Restaurant;
import ru.javawebinar.topjava.graduation.service.MealService;
import ru.javawebinar.topjava.graduation.service.MenuService;
import ru.javawebinar.topjava.graduation.service.RestaurantService;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.graduation.MealTestData.*;
import static ru.javawebinar.topjava.graduation.MenuTestData.*;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.*;
import static ru.javawebinar.topjava.graduation.TestUtil.readFromJson;
import static ru.javawebinar.topjava.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.graduation.UserTestData.ADMIN;
import static ru.javawebinar.topjava.graduation.UserTestData.USER;
import static ru.javawebinar.topjava.graduation.web.json.JsonUtil.writeValue;
import static ru.javawebinar.topjava.graduation.web.restaurant.AdminRestaurantController.*;

public class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected MealService mealService;

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(REST_URL)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJsonRestaurant(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3));
    }

    @Test
    void addRestaurant() throws Exception {
        Restaurant expected = new Restaurant(null, "New restaurant", null);
        ResultActions action = mockMvc.perform(post(REST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(ADMIN))
            .content(writeValue(expected)))
            .andDo(print())
            .andExpect(status().isCreated());

        Restaurant returned = readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatchRestaurant(returned, expected);
        assertMatchRestaurant(restaurantService.getAll(Sort.by("id")),
            RESTAURANT_1, RESTAURANT_2, RESTAURANT_3, expected);
    }

    @Test
    void updateRestaurant() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdatedName");
        updated.setMenus(RESTAURANT_2_MENUS);

        mockMvc.perform(put(REST_URL + "/{restaurantId}", RESTAURANT_1_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(ADMIN))
            .content(writeValue(updated)))
            .andDo(print())
            .andExpect(status().isNoContent());

        assertMatchRestaurant(restaurantService.get(RESTAURANT_1_ID), updated);
    }

    @Test
    void deleteRestaurant() throws Exception {
        mockMvc.perform(delete(REST_URL + "/{restaurantId}", RESTAURANT_1_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isNoContent());

        assertMatchRestaurant(restaurantService.getAll(Sort.unsorted()), RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    void addMenu() throws Exception {
        Menu expected = new Menu(null, MEALS_R1_NOW, LocalDate.now());

        ResultActions action = mockMvc.perform(post(REST_URL + REST_URL_MENUS, RESTAURANT_1_ID)
            .with(userHttpBasic(ADMIN))
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(expected)))
            .andDo(print())
            .andExpect(status().isCreated());

        Menu returned = readFromJson(action, Menu.class);
        expected.setId(returned.getId());
        expected.setActual(returned.getActual());

        assertMatchMenu(returned, expected);
        assertMatchMenu(menuService.getAllForRestaurant(RESTAURANT_1_ID),
            MENU_R1_D27, MENU_R1_D28, MENU_R1_NOW, expected);
    }

    @Test
    void updateMenu() throws Exception {
        Menu updated = new Menu(MENU_R1_D27);
        LocalDate actual = LocalDate.now();
        updated.setMeals(MEALS_R2_D28);
        updated.setActual(actual);

        mockMvc.perform(put(REST_URL + REST_URL_MENUS + "/{menuId}", RESTAURANT_1_ID, MENU_R1_D27.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(ADMIN))
            .content(writeValue(updated)))
            .andDo(print())
            .andExpect(status().isNoContent());

        assertMatchMenu(menuService.getForRestaurant(RESTAURANT_1_ID, MENU_R1_D27.getId()), updated);
    }

    @Test
    void deleteMenu() throws Exception {
        mockMvc.perform(delete(REST_URL + REST_URL_MENUS + "/{menuId}", RESTAURANT_1_ID, MENU_R1_D27.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isNoContent());

        assertMatchMenu(menuService.getAllForRestaurant(RESTAURANT_1_ID), MENU_R1_D28, MENU_R1_NOW);
    }

    @Test
    void addMeal() throws Exception {
        Meal expected = new Meal(null, "FunkChoose", 788);

        ResultActions action = mockMvc.perform(post(REST_URL + REST_URL_MEALS,
            RESTAURANT_1_ID, MENU_R1_D27.getId())
            .with(userHttpBasic(ADMIN))
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(expected)))
            .andDo(print())
            .andExpect(status().isCreated());

        Meal returned = readFromJson(action, Meal.class);
        expected.setId(returned.getId());

        assertMatchMeal(returned, expected);
        assertMatchMeal(mealService.getAllForMenu(MENU_R1_D27.getId()), MEAL_R1_D27_1, MEAL_R1_D27_2, expected);
    }

    @Test
    void updateMeal() throws Exception {
        Meal updated = new Meal(MEAL_R1_D27_1);
        updated.setName("Coffee Latte");
        updated.setPrice(355);

        mockMvc.perform(put(REST_URL + REST_URL_MEALS + "/{mealId}",
            RESTAURANT_1_ID, MENU_R1_D27.getId(), MEAL_R1_D27_1.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(ADMIN))
            .content(writeValue(updated)))
            .andDo(print())
            .andExpect(status().isNoContent());

        assertMatchMeal(mealService.getForMenu(MENU_R1_D27.getId(), MEAL_R1_D27_1.getId()), updated);
    }

    @Test
    void deleteMeal() throws Exception {
        mockMvc.perform(delete(REST_URL + REST_URL_MEALS + "/{mealId}",
            RESTAURANT_1_ID, MENU_R1_D27.getId(), MEAL_R1_D27_1.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isNoContent());

        assertMatchMeal(mealService.getAllForMenu(MENU_R1_D27.getId()), Collections.singletonList(MEAL_R1_D27_2));
    }
}
