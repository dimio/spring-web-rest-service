package ru.javawebinar.topjava.graduation.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.graduation.MenuTestData.MENU_R1_D27;
import static ru.javawebinar.topjava.graduation.MenuTestData.contentJson;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.*;
import static ru.javawebinar.topjava.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.graduation.UserTestData.USER;
import static ru.javawebinar.topjava.graduation.web.restaurant.UserRestaurantController.REST_URL;


class UserRestaurantControllerTest extends AbstractControllerTest {

    @Test
    void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllRestaurants() throws Exception {
        mockMvc.perform(get(REST_URL + "/all")
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJsonRestaurant(RESTAURANT_1, RESTAURANT_2));
    }

    @Test
    void getRestaurantById() throws Exception {
        mockMvc.perform(get(REST_URL + "/{restaurantId}", RESTAURANT_1_ID)
            .with(userHttpBasic(USER)))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJsonRestaurant(RESTAURANT_1));
    }

    @Test
    void getMenuForRestaurantToday() throws Exception {
        mockMvc.perform(get(REST_URL + "/{restaurantId}/menu", RESTAURANT_1_ID)
            .with(userHttpBasic(USER)))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getMenuForRestaurantForDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/{restaurantId}/menu", RESTAURANT_1_ID)
            .param("date", "2019-06-27")
            .with(userHttpBasic(USER)))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(Collections.singleton(MENU_R1_D27)));
    }
}