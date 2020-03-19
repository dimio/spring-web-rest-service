package ru.javawebinar.topjava.graduation.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.graduation.service.VoteService;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.graduation.MealTestData.*;
import static ru.javawebinar.topjava.graduation.MenuTestData.*;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.*;
import static ru.javawebinar.topjava.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.graduation.UserTestData.USER;
import static ru.javawebinar.topjava.graduation.web.restaurant.UserRestaurantController.*;


class UserRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    protected VoteService service;

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllRestaurantsWithActualMenuExist() throws Exception {
        mockMvc.perform(get(REST_URL)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJsonRestaurant(RESTAURANT_1, RESTAURANT_2));
    }

    @Test
    void getRestaurantById() throws Exception {
        mockMvc.perform(get(REST_URL + "/{restaurantId}", RESTAURANT_1_ID)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJsonRestaurant(RESTAURANT_1));
    }

    @Test
    void getRestaurantByName() throws Exception {
        mockMvc.perform(get(REST_URL + "/byName")
            .param("name", RESTAURANT_1.getName())
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJsonRestaurant(RESTAURANT_1));
    }

    @Test
    void getAllMenusForRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + REST_URL_MENUS, RESTAURANT_1_ID)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJsonMenu(MENU_R1_D27, MENU_R1_D28, MENU_R1_NOW));
    }

    @Test
    void getMenuForRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + REST_URL_MENUS + "/{menuId}", RESTAURANT_1_ID, MENU_R1_NOW.getId())
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJsonMenu(MENU_R1_NOW));
    }

    @Test
    void getAllMealsForMenu() throws Exception {
        mockMvc.perform(get(REST_URL + REST_URL_MEALS, RESTAURANT_1_ID, MENU_R1_NOW.getId())
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJsonMeal(MEALS_R1_NOW));
    }

    @Test
    void getMealForMenu() throws Exception {
        mockMvc.perform(get(REST_URL + REST_URL_MEALS + "/{mealId}",
            RESTAURANT_1_ID, MENU_R1_NOW.getId(), MEAL_R1_NOW_1.getId())
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJsonMeal(MEAL_R1_NOW_1));
    }
}
