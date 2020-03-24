package ru.javawebinar.topjava.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.graduation.model.Menu;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.graduation.MealTestData.*;
import static ru.javawebinar.topjava.graduation.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.TestUtil.readListFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final int MENU_ID = START_SEQ + 5;

    //  R1: Mc'Downalds menus
    public static final Menu MENU_R1_D27 = new Menu(MENU_ID, MEALS_R1_D27, LocalDate.of(2019, 6, 27));
    public static final Menu MENU_R1_D28 = new Menu(MENU_ID + 1, MEALS_R1_D28, LocalDate.of(2019, 6, 28));
    public static final Menu MENU_R1_NOW = new Menu(MENU_ID + 2, MEALS_R1_NOW, LocalDate.now());
    public static final Set<Menu> RESTAURANT_1_MENUS = new HashSet<>(Arrays.asList(MENU_R1_D27, MENU_R1_D28, MENU_R1_NOW));

    //  R2: Dock Clownalds menus
    public static final Menu MENU_R2_D27 = new Menu(MENU_ID + 3, MEALS_R2_D27, LocalDate.of(2019, 6, 27));
    public static final Menu MENU_R2_D28 = new Menu(MENU_ID + 4, MEALS_R2_D28, LocalDate.of(2019, 6, 28));
    public static final Menu MENU_R2_NOW = new Menu(MENU_ID + 5, MEALS_R2_NOW, LocalDate.now());
    public static final Set<Menu> RESTAURANT_2_MENUS = new HashSet<>(Arrays.asList(MENU_R2_D27, MENU_R2_D28, MENU_R2_NOW));

    //  R3: Not this time menus
    public static final Menu MENU_R3_D27 = new Menu(MENU_ID + 6, MEALS_R3_D27, LocalDate.of(2019, 6, 27));
    public static final Set<Menu> RESTAURANT_3_MENUS = new HashSet<>(Collections.singletonList(MENU_R3_D27));


    public static void assertMatchMenu(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant", "meals");
    }

    public static void assertMatchMenu(Iterable<Menu> actual, Menu... expected) {
        assertMatchMenu(actual, List.of(expected));
    }

    public static void assertMatchMenu(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "meals").isEqualTo(expected);
    }

    public static ResultMatcher contentJsonMenu(Menu... expected) {
        return result -> assertMatchMenu(readListFromJsonMvcResult(result, Menu.class), List.of(expected));
    }

    public static ResultMatcher contentJsonMenu(Iterable<Menu> expected) {
        return result -> assertMatchMenu(readListFromJsonMvcResult(result, Menu.class), expected);
    }

    public static ResultMatcher contentJsonMenu(Menu expected) {
        return result -> assertMatchMenu(readFromJsonMvcResult(result, Menu.class), expected);
    }
}
