package ru.javawebinar.topjava.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.graduation.model.Vote;

import static ru.javawebinar.topjava.graduation.RestaurantTestData.*;
import static ru.javawebinar.topjava.graduation.UserTestData.*;
import static ru.javawebinar.topjava.graduation.VoteTestData.assertMatch;
import static ru.javawebinar.topjava.graduation.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService service;

    @Test
    void voteNewBeforeDecisionTime() {
        Vote newVote = new Vote(null, VOTE_DATE_TIME_NEW_BEFORE.toLocalDate(), RESTAURANT_2, USER);
        service.setClockAndTimeZone(VOTE_DATE_TIME_NEW_BEFORE);
        newVote.setId(service.vote(USER_ID, RESTAURANT_2_ID).getId());
        assertMatch(service.getForRestaurantAndDate(RESTAURANT_2_ID, VOTE_DATE_TIME_NEW_AFTER.toLocalDate()), newVote);

    }

    @Test
    void voteNewAfterDecisionTime() {
        Vote newVote = new Vote(null, VOTE_DATE_TIME_NEW_AFTER.toLocalDate(), RESTAURANT_2, ADMIN);
        service.setClockAndTimeZone(VOTE_DATE_TIME_NEW_AFTER);
        newVote.setId(service.vote(ADMIN_ID, RESTAURANT_2_ID).getId());
        assertMatch(service.getForRestaurantAndDate(RESTAURANT_2_ID, VOTE_DATE_TIME_NEW_AFTER.toLocalDate()), newVote);
    }

    @Test
    void voteUpdateBeforeDecisionTime() {
        Vote newVote = new Vote(null, VOTE_DATE_TIME_BEFORE.toLocalDate(), RESTAURANT_2, USER);
        service.setClockAndTimeZone(VOTE_DATE_TIME_BEFORE);
        Vote updated = service.vote(USER_ID, RESTAURANT_2_ID);
        newVote.setId(updated.getId());
        assertMatch(updated, newVote);
        assertMatch(service.getForRestaurantAndDate(RESTAURANT_2_ID, VOTE_DATE_TIME_BEFORE.toLocalDate()), ADMIN_VOTE_2, updated);
    }

    @Test
    void voteUpdateAfterDecisionTime() {
        service.setClockAndTimeZone(VOTE_DATE_TIME_AFTER);
        service.vote(USER_ID, RESTAURANT_2_ID);
        assertMatch(service.getForRestaurantAndDate(RESTAURANT_2_ID, VOTE_DATE_TIME_AFTER.toLocalDate()), ADMIN_VOTE_2);
    }

    @Test
    void getAllForUser() {
        assertMatch(service.getAllForUser(USER_ID), USER_VOTE_1, USER_VOTE_2);
    }

    @Test
    void getAllForRestaurant() {
        assertMatch(service.getAllForRestaurant(RESTAURANT_1_ID), USER_VOTE_1, ADMIN_VOTE_1, USER_VOTE_2);
    }

    @Test
    void getForUserAndDate() {
        assertMatch(service.getForUserAndDate(USER_ID, VOTE_DATE_TIME_BEFORE.toLocalDate()), USER_VOTE_2);
    }

    @Test
    void getForRestaurantAndDate() {
        assertMatch(service.getForRestaurantAndDate(RESTAURANT_2_ID, VOTE_DATE_TIME_AFTER.toLocalDate()), ADMIN_VOTE_2);
    }
}