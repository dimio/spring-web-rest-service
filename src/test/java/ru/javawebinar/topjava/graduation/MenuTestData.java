package ru.javawebinar.topjava.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.graduation.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.graduation.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.TestUtil.readListFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final int MENU_ID = START_SEQ + 4;

    public static final Menu MENU_R1_D27 = new Menu(MENU_ID, "menuR1", LocalDate.of(2019, 6, 27), "A, B", 99L, 99L);
    public static final Menu MENU_R1_D28 = new Menu(MENU_ID + 1, "menuR1", LocalDate.of(2019, 6, 28), "A, C", 101L, 55L);

    public static final Menu MENU_R2_D27 = new Menu(MENU_ID + 2, "menuR2", LocalDate.of(2019, 6, 27), "B, C", 100L, 0L);
    public static final Menu MENU_R2_D28 = new Menu(MENU_ID + 3, "menuR2", LocalDate.of(2019, 6, 28), "B, D", 98L, 30L);

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Menu... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Menu.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Iterable<Menu> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Menu.class), expected);
    }

    public static ResultMatcher contentJson(Menu expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Menu.class), expected);
    }
}
