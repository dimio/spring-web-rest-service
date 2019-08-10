package ru.javawebinar.topjava.graduation.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.graduation.service.VoteService;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.RESTAURANT_2_ID;
import static ru.javawebinar.topjava.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.graduation.UserTestData.ADMIN;
import static ru.javawebinar.topjava.graduation.UserTestData.USER_ID;
import static ru.javawebinar.topjava.graduation.VoteTestData.*;
import static ru.javawebinar.topjava.graduation.web.vote.AdminVoteController.REST_URL;

class AdminVoteControllerTest extends AbstractControllerTest {

    @Autowired
    protected VoteService service;

    @Test
    void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(USER_VOTE_2, ADMIN_VOTE_2, USER_VOTE_1, ADMIN_VOTE_1));
    }

    @Test
    void testGetAllForUser() throws Exception {
        mockMvc.perform(get(REST_URL + "/user/{userId}/all", USER_ID)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(USER_VOTE_1, USER_VOTE_2));
    }

    @Test
    void testGetForUserAndDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/user/{userId}", USER_ID)
            .param("date", "2019-06-27")
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(USER_VOTE_1));
    }

    @Test
    void testGetAllForRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurant/{restaurantId}/all", RESTAURANT_2_ID)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(Collections.singletonList(ADMIN_VOTE_2)));
    }

    @Test
    void testGetForRestaurantAndDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurant/{restaurantId}", RESTAURANT_2_ID)
            .param("date", "2019-06-28")
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(Collections.singletonList(ADMIN_VOTE_2)));
    }
}