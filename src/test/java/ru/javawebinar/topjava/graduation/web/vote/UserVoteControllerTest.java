package ru.javawebinar.topjava.graduation.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.graduation.service.VoteService;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.RESTAURANT_2_ID;
import static ru.javawebinar.topjava.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.graduation.UserTestData.USER;
import static ru.javawebinar.topjava.graduation.VoteTestData.*;
import static ru.javawebinar.topjava.graduation.web.vote.UserVoteController.REST_URL;

class UserVoteControllerTest extends AbstractControllerTest {

    @Autowired
    protected VoteService service;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
            .param("date", "2019-06-27")
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJson(USER_VOTE_1));
    }

    @Test
    void getUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void getForCurrentDate() throws Exception {
        mockMvc.perform(get(REST_URL)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(REST_URL + "/all")
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJson(USER_VOTE_1, USER_VOTE_2));
    }

    @Test
    void voteNewBeforeDecisionTime() throws Exception {
        service.setClockAndTimeZone(VOTE_DATE_TIME_NEW_AFTER);
        mockMvc.perform(post(REST_URL + '/' + RESTAURANT_2_ID).contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    void voteNewAfterDecisionTime() throws Exception {
        service.setClockAndTimeZone(VOTE_DATE_TIME_NEW_AFTER);
        mockMvc.perform(post(REST_URL + '/' + RESTAURANT_2_ID).contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    void voteUpdateBeforeDecisionTime() throws Exception {
        service.setClockAndTimeZone(VOTE_DATE_TIME_BEFORE);
        mockMvc.perform(put(REST_URL + '/' + RESTAURANT_2_ID).contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    void voteUpdateAfterDecisionTime() throws Exception {
        service.setClockAndTimeZone(VOTE_DATE_TIME_AFTER);
        mockMvc.perform(post(REST_URL + '/' + RESTAURANT_2_ID).contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isLocked());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isLocked());
    }
}