package ru.javawebinar.topjava.graduation;

import ru.javawebinar.topjava.graduation.model.Menu;
import ru.javawebinar.topjava.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {

    public static final int RESTAURANT_1_ID = START_SEQ + 2;
    public static final int RESTAURANT_2_ID = RESTAURANT_1_ID + 1;
    public static final int MENU_ID = START_SEQ + 4;

    public static final Menu MENU_R1_D27 = new Menu(MENU_ID, "menuR1", LocalDate.of(2019, 6, 27), "A, B", 99L, 99L);
    public static final Menu MENU_R1_D28 = new Menu(MENU_ID + 1, "menuR1", LocalDate.of(2019, 6, 28), "A, C", 101L, 55L);
    public static final List<Menu> RESTAURANT_1_MENUS = new ArrayList<>(Arrays.asList(MENU_R1_D27, MENU_R1_D28));
    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_1_ID, "McDownalds", RESTAURANT_1_MENUS);

    public static final Menu MENU_R2_D27 = new Menu(MENU_ID + 2, "menuR2", LocalDate.of(2019, 6, 27), "B, C", 100L, 0L);
    public static final Menu MENU_R2_D28 = new Menu(MENU_ID + 3, "menuR2", LocalDate.of(2019, 6, 28), "B, D", 98L, 30L);
    public static final List<Menu> RESTAURANT_2_MENUS = new ArrayList<>(Arrays.asList(MENU_R2_D27, MENU_R2_D28));
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_2_ID + 1, "Dock Clownalds", RESTAURANT_2_MENUS);

}
