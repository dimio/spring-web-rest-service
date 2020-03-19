package ru.javawebinar.topjava.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.graduation.model.Meal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.graduation.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.TestUtil.readListFromJsonMvcResult;
import static ru.javawebinar.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 18;

    //  R1: Mc'Downalds meals
    //  (int) 10.96 * 100; Double d = 1.55 * 100; Integer i = d.intValue();
    public static final Meal MEAL_R1_D27_1 = new Meal(MEAL_ID, "Big Muck", 1096);
    public static final Meal MEAL_R1_D27_2 = new Meal(MEAL_ID + 1, "Moo Duck", 575);
    public static final List<Meal> MEALS_R1_D27 = new ArrayList<>(Arrays.asList(MEAL_R1_D27_1, MEAL_R1_D27_2));

    public static final Meal MEAL_R1_D28_1 = new Meal(MEAL_ID + 2, "Big Muck", 1096);
    public static final Meal MEAL_R1_D28_2 = new Meal(MEAL_ID + 3, "Glue Cola", 100);
    public static final List<Meal> MEALS_R1_D28 = new ArrayList<>(Arrays.asList(MEAL_R1_D28_1, MEAL_R1_D28_2));

    public static final Meal MEAL_R1_NOW_1 = new Meal(MEAL_ID + 4, "Big Muck", 1096);
    public static final Meal MEAL_R1_NOW_2 = new Meal(MEAL_ID + 5, "What the Funk", 10050);
    public static final List<Meal> MEALS_R1_NOW = new ArrayList<>(List.of(MEAL_R1_NOW_1, MEAL_R1_NOW_2));

    //  R2: Dock Clownalds meals
    public static final Meal MEAL_R2_D27_1 = new Meal(MEAL_ID + 6, "Burger", 296);
    public static final Meal MEAL_R2_D27_2 = new Meal(MEAL_ID + 7, "Tea", 75);
    public static final List<Meal> MEALS_R2_D27 = new ArrayList<>(Arrays.asList(MEAL_R2_D27_1, MEAL_R2_D27_2));

    public static final Meal MEAL_R2_D28_1 = new Meal(MEAL_ID + 8, "Coffee", 150);
    public static final Meal MEAL_R2_D28_2 = new Meal(MEAL_ID + 9, "Sandwich", 300);
    public static final List<Meal> MEALS_R2_D28 = new ArrayList<>(Arrays.asList(MEAL_R2_D28_1, MEAL_R2_D28_2));

    public static final Meal MEAL_R2_NOW_1 = new Meal(MEAL_ID + 10, "Soup", 896);
    public static final List<Meal> MEALS_R2_NOW = new ArrayList<>(Collections.singletonList(MEAL_R2_NOW_1));

    //  R3: Not this time meals
    public static final Meal MEAL_R3_D27_1 = new Meal(MEAL_ID + 11, "Roast beef", 1055);
    public static final List<Meal> MEALS_R3_D27 = new ArrayList<>(Collections.singletonList(MEAL_R3_D27_1));


    public static void assertMatchMeal(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatchMeal(Iterable<Meal> actual, Meal... expected) {
        assertMatchMeal(actual, List.of(expected));
    }

    public static void assertMatchMeal(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu").isEqualTo(expected);
    }

    public static ResultMatcher contentJsonMeal(Meal... expected) {
        return result -> assertMatchMeal(readListFromJsonMvcResult(result, Meal.class), List.of(expected));
    }

    public static ResultMatcher contentJsonMeal(Iterable<Meal> expected) {
        return result -> assertMatchMeal(readListFromJsonMvcResult(result, Meal.class), expected);
    }

    public static ResultMatcher contentJsonMeal(Meal expected) {
        return result -> assertThat(readFromJsonMvcResult(result, Meal.class)).isEqualTo(expected);
    }

}
