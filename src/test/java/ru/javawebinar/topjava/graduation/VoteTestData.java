package ru.javawebinar.topjava.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.graduation.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.RESTAURANT_1;
import static ru.javawebinar.topjava.graduation.RestaurantTestData.RESTAURANT_2;
import static ru.javawebinar.topjava.graduation.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.TestUtil.readListFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.UserTestData.ADMIN;
import static ru.javawebinar.topjava.graduation.UserTestData.USER;
import static ru.javawebinar.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final int VOTE_ID = START_SEQ + 8;

    public static final LocalDateTime VOTE_DATE_TIME_NEW = LocalDateTime.of(2019, 6, 29, 10, 5);
    public static final LocalDateTime VOTE_DATE_TIME_BEFORE = LocalDateTime.of(2019, 6, 27, 10, 3);
    public static final LocalDateTime VOTE_DATE_TIME_AFTER = LocalDateTime.of(2019, 6, 28, 11, 5);

    public static final Vote USER_VOTE_1 = new Vote(VOTE_ID, LocalDate.of(2019, 6, 27), RESTAURANT_1, USER);
    public static final Vote USER_VOTE_2 = new Vote(VOTE_ID + 2, LocalDate.of(2019, 6, 28), RESTAURANT_1, USER);

    public static final Vote ADMIN_VOTE_1 = new Vote(VOTE_ID + 1, LocalDate.of(2019, 6, 27), RESTAURANT_1, ADMIN);
    public static final Vote ADMIN_VOTE_2 = new Vote(VOTE_ID + 3, LocalDate.of(2019, 6, 28), RESTAURANT_2, ADMIN);

    public static final List<Vote> RESTAURANT_1_VOTES = new ArrayList<>(Arrays.asList(USER_VOTE_1, USER_VOTE_2, ADMIN_VOTE_1));
    public static final List<Vote> RESTAURANT_2_VOTES = new ArrayList<>(Collections.singletonList(ADMIN_VOTE_2));


    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user", "restaurant");
        //      assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user", "restaurant").isEqualTo(expected);
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
