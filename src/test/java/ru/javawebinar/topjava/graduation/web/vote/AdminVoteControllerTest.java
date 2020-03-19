package ru.javawebinar.topjava.graduation.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.graduation.service.VoteService;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.RESTAURANT_2_ID;
import static ru.javawebinar.topjava.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.graduation.UserTestData.*;
import static ru.javawebinar.topjava.graduation.VoteTestData.contentJson;
import static ru.javawebinar.topjava.graduation.VoteTestData.*;
import static ru.javawebinar.topjava.graduation.web.vote.AdminVoteController.REST_URL;

class AdminVoteControllerTest extends AbstractControllerTest {

    @Autowired
    protected VoteService service;

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
    void getAllForUser() throws Exception {
        mockMvc.perform(get(REST_URL + "/user/{userId}", USER_ID)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(USER_VOTE_1, USER_VOTE_2, USER_VOTE_NOW));
    }

    @Test
    void getForUserAndDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/user/{userId}", USER_ID)
            .param("date", LocalDate.now().toString())
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(Collections.singletonList(USER_VOTE_NOW)));
    }

    @Test
    void getAllForRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurant/{restaurantId}", RESTAURANT_2_ID)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(List.of(ADMIN_VOTE_2, ADMIN_VOTE_3)));
    }
}
