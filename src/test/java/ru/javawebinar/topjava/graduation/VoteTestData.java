package ru.javawebinar.topjava.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.graduation.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.RESTAURANT_1;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.RESTAURANT_2;
import static ru.javawebinar.topjava.graduation.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.TestUtil.readListFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.UserTestData.ADMIN;
import static ru.javawebinar.topjava.graduation.UserTestData.USER;
import static ru.javawebinar.topjava.graduation.model.AbstractBaseEntity.START_SEQ;
import static ru.javawebinar.topjava.graduation.model.Vote.DECISION_TIME;

public class VoteTestData {
    public static final int VOTE_ID = START_SEQ + 12;

    public static final LocalTime DECISION_TIME_BEFORE = DECISION_TIME.minusMinutes(10L);
    public static final LocalTime DECISION_TIME_AFTER = DECISION_TIME.plusMinutes(10L);

    public static final LocalDateTime VOTE_DATE_TIME_NEW_BEFORE = LocalDateTime.of(LocalDate.now(), DECISION_TIME_BEFORE);
    public static final LocalDateTime VOTE_DATE_TIME_NEW_AFTER = LocalDateTime.of(LocalDate.now(), DECISION_TIME_AFTER);
    public static final LocalDateTime VOTE_DATE_TIME_BEFORE = LocalDateTime.of(LocalDate.of(2019, 6, 28), DECISION_TIME_BEFORE);
    public static final LocalDateTime VOTE_DATE_TIME_AFTER = LocalDateTime.of(LocalDate.of(2019, 6, 28), DECISION_TIME_AFTER);

    public static final Vote USER_VOTE_1 = new Vote(VOTE_ID, LocalDate.of(2019, 6, 27), RESTAURANT_1, USER);
    public static final Vote USER_VOTE_2 = new Vote(VOTE_ID + 2, LocalDate.of(2019, 6, 28), RESTAURANT_1, USER);
    public static final Vote USER_VOTE_NOW = new Vote(VOTE_ID + 4, LocalDate.now(), RESTAURANT_1, USER);

    public static final Vote ADMIN_VOTE_1 = new Vote(VOTE_ID + 1, LocalDate.of(2019, 6, 27), RESTAURANT_1, ADMIN);
    public static final Vote ADMIN_VOTE_2 = new Vote(VOTE_ID + 3, LocalDate.of(2019, 6, 28), RESTAURANT_2, ADMIN);
    public static final Vote ADMIN_VOTE_3 = new Vote(VOTE_ID + 5, LocalDate.of(2020, 3, 3), RESTAURANT_2, ADMIN);

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualTo(expected);
        //        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        //        assertThat(actual).usingElementComparatorIgnoringFields("user", "restaurant").isEqualTo(expected);
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Vote... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Vote.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Iterable<Vote> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Vote.class), expected);
    }

    public static ResultMatcher contentJson(Vote expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Vote.class), expected);
    }
}
