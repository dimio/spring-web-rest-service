package ru.javawebinar.topjava.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.javawebinar.topjava.graduation.model.Vote;

import static ru.javawebinar.topjava.graduation.RestaurantTestData.*;
import static ru.javawebinar.topjava.graduation.UserTestData.USER;
import static ru.javawebinar.topjava.graduation.UserTestData.USER_ID;
import static ru.javawebinar.topjava.graduation.VoteTestData.*;

//TODO fix lazy init problem
class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService service;

    @Test
    void vote() {
        Vote newVote = new Vote(null, VOTE_DATE_TIME_NEW.toLocalDate(), RESTAURANT_2, USER);
        service.setClockAndTimeZone(VOTE_DATE_TIME_NEW);
        service.vote(USER_ID, RESTAURANT_2_ID);
        assertMatch(service.getForRestaurantAndDate(RESTAURANT_2_ID, VOTE_DATE_TIME_NEW.toLocalDate()), newVote);
    }

    @Test
    void voteUpdate() {
        Vote newVote = new Vote(VOTE_ID, VOTE_DATE_TIME_BEFORE.toLocalDate(), RESTAURANT_2, USER);
        service.setClockAndTimeZone(VOTE_DATE_TIME_BEFORE);
        Vote updated = service.vote(USER_ID, RESTAURANT_2_ID);
        assertMatch(updated, newVote);
    }

    @Test
    void voteAfterDecisionTime() {
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
        assertMatch(service.getAllForRestaurant(RESTAURANT_1_ID), USER_VOTE_1, USER_VOTE_2, ADMIN_VOTE_1);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(Sort.unsorted()), USER_VOTE_1, USER_VOTE_2, ADMIN_VOTE_1, ADMIN_VOTE_2);
    }

    @Test
    void getForUserAndDate() {
        assertMatch(service.getForUserAndDate(USER_ID, VOTE_DATE_TIME_BEFORE.toLocalDate()), USER_VOTE_1);
    }

    @Test
    void getForRestaurantAndDate() {
        assertMatch(service.getForRestaurantAndDate(RESTAURANT_2_ID, VOTE_DATE_TIME_AFTER.toLocalDate()), ADMIN_VOTE_2);
    }
}