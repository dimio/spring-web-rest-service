package ru.javawebinar.topjava.graduation.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.graduation.model.Vote;
import ru.javawebinar.topjava.graduation.service.VoteService;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.*;
import static ru.javawebinar.topjava.graduation.TestUtil.readFromJson;
import static ru.javawebinar.topjava.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.graduation.UserTestData.*;
import static ru.javawebinar.topjava.graduation.VoteTestData.assertMatch;
import static ru.javawebinar.topjava.graduation.VoteTestData.contentJson;
import static ru.javawebinar.topjava.graduation.VoteTestData.*;
import static ru.javawebinar.topjava.graduation.web.vote.UserVoteController.REST_URL;

class UserVoteControllerTest extends AbstractControllerTest {

    @Autowired
    protected VoteService service;

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllForYourself() throws Exception {
        mockMvc.perform(get(REST_URL)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(contentJson(USER_VOTE_1, USER_VOTE_2, USER_VOTE_NOW));
    }

    @Test
    void getForDateForYourself() throws Exception {
        mockMvc.perform(get(REST_URL)
            .param("date", "2019-06-27")
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJson(Collections.singletonList(USER_VOTE_1)));
    }

    @Test
    void getForDateForRestaurantWithDateFromUndefinedAndDateToNow() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurant/{restaurantId}", RESTAURANT_1_ID)
            .param("dateTo", LocalDate.now().toString())
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJson(Collections.singletonList(USER_VOTE_NOW)));
    }

    @Test
    void getForDateForRestaurantWithDateFromNowAndDateToUndefined() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurant/{restaurantId}", RESTAURANT_1_ID)
            .param("dateFrom", LocalDate.now().toString())
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJson(Collections.singletonList(USER_VOTE_NOW)));
    }

    @Test
    void getForDateForRestaurantWithDateFromUndefinedAndDateToUndefined() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurant/{restaurantId}", RESTAURANT_1_ID)
            // it's work without specify request parameters
            //            .param("dateFrom", "")
            //            .param("dateTo", "")
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJson(Collections.singletonList(USER_VOTE_NOW)));
    }

    @Test
    void getForDateForRestaurantWithDateBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurant/{restaurantId}", RESTAURANT_1_ID)
            .param("dateFrom", LocalDate.of(2019, 6, 27).toString())
            .param("dateTo", LocalDate.of(2019, 6, 28).toString())
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJson(USER_VOTE_1, ADMIN_VOTE_1, USER_VOTE_2));
    }

    @Test
    void getForDateForRestaurantWithDateBetweenAndDateFromAfterDateTo() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurant/{restaurantId}", RESTAURANT_1_ID)
            .param("dateFrom", LocalDate.of(2019, 6, 28).toString())
            .param("dateTo", LocalDate.of(2019, 6, 27).toString())
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJson(USER_VOTE_1, ADMIN_VOTE_1, USER_VOTE_2));
    }

    @Test
    void voteNewBeforeDecisionTime() throws Exception {
        Vote expected = new Vote(null, VOTE_DATE_TIME_NEW_BEFORE.toLocalDate(), RESTAURANT_2, ADMIN);
        service.setClockAndTimeZone(VOTE_DATE_TIME_NEW_BEFORE);
        ResultActions action = mockMvc.perform(post(REST_URL + "/restaurant/{restaurantId}", RESTAURANT_2_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        Vote returned = readFromJson(action, Vote.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getForUserAndDate(ADMIN_ID, VOTE_DATE_TIME_NEW_BEFORE.toLocalDate()), expected);
    }

    @Test
    void voteNewAfterDecisionTime() throws Exception {
        Vote expected = new Vote(null, VOTE_DATE_TIME_NEW_AFTER.toLocalDate(), RESTAURANT_3, ADMIN);
        service.setClockAndTimeZone(VOTE_DATE_TIME_NEW_AFTER);
        ResultActions action = mockMvc.perform(post(REST_URL + "/restaurant/{restaurantId}", RESTAURANT_3_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        Vote returned = readFromJson(action, Vote.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getForUserAndDate(ADMIN_ID, VOTE_DATE_TIME_NEW_AFTER.toLocalDate()), expected);
    }

    @Test
    void voteUpdateBeforeDecisionTime() throws Exception {
        Vote updated = new Vote(USER_VOTE_2);
        updated.setRestaurant(RESTAURANT_2);
        updated.setDate(VOTE_DATE_TIME_BEFORE.toLocalDate());

        service.setClockAndTimeZone(VOTE_DATE_TIME_BEFORE);
        mockMvc.perform(put(REST_URL + "/restaurant/{restaurantId}", RESTAURANT_2_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(contentJson(updated));
    }

    @Test
    void voteUpdateAfterDecisionTime() throws Exception {
        service.setClockAndTimeZone(VOTE_DATE_TIME_AFTER);
        mockMvc.perform(post(REST_URL + "/restaurant/{restaurantId}", RESTAURANT_2_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteBeforeDecisionTime() throws Exception {
        service.setClockAndTimeZone(VOTE_DATE_TIME_BEFORE);
        mockMvc.perform(delete(REST_URL)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteAfterDecisionTime() throws Exception {
        service.setClockAndTimeZone(VOTE_DATE_TIME_AFTER);
        mockMvc.perform(delete(REST_URL)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isUnprocessableEntity());
    }
}
