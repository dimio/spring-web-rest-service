package ru.javawebinar.topjava.graduation.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.graduation.service.VoteService;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.RESTAURANT_2_ID;
import static ru.javawebinar.topjava.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.graduation.UserTestData.USER;

class AdminVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserVoteController.REST_URL + '/';

    @Autowired
    protected VoteService service;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void testVote() throws Exception {
        mockMvc.perform(post(REST_URL + RESTAURANT_2_ID).contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(USER)))
            //            .content(JsonUtil.writeValue(updatedTo)))
            .andDo(print())
            .andExpect(status().isNoContent());
    }
}