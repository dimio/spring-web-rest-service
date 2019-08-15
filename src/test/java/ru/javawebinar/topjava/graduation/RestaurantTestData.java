package ru.javawebinar.topjava.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.graduation.model.Menu;
import ru.javawebinar.topjava.graduation.model.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.graduation.MenuTestData.*;
import static ru.javawebinar.topjava.graduation.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.TestUtil.readListFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {

    public static final int RESTAURANT_1_ID = START_SEQ + 2;
    public static final int RESTAURANT_2_ID = RESTAURANT_1_ID + 1;

    public static final List<Menu> RESTAURANT_1_MENUS = new ArrayList<>(Arrays.asList(MENU_R1_D27, MENU_R1_D28));
    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_1_ID, "McDownalds", RESTAURANT_1_MENUS);

    public static final List<Menu> RESTAURANT_2_MENUS = new ArrayList<>(Arrays.asList(MENU_R2_D27, MENU_R2_D28));
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_2_ID, "Dock Clownalds", RESTAURANT_2_MENUS);

    public static void assertMatchRestaurant(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "lunchMenus");
        //      assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatchRestaurant(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatchRestaurant(actual, List.of(expected));
    }

    public static void assertMatchRestaurant(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("lunchMenus").isEqualTo(expected);
    }

    public static ResultMatcher contentJsonRestaurant(Restaurant... expected) {
        return result -> assertMatchRestaurant(readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }

    public static ResultMatcher contentJsonRestaurant(Iterable<Restaurant> expected) {
        return result -> assertMatchRestaurant(readListFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher contentJsonRestaurant(Restaurant expected) {
        return result -> assertMatchRestaurant(readFromJsonMvcResult(result, Restaurant.class), expected);
    }

}
