package ru.javawebinar.topjava.graduation.repository;

import ru.javawebinar.topjava.graduation.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {

    // null if updated meal do not belong to restaurantId
    Menu save(Menu menu, int restaurantId);

    // false if meal do not belong to restaurantId
    boolean delete(int id, int restaurantId);

    // null if meal do not belong to restaurantId
    Menu get(int id, int restaurantId);

    // ORDERED dateTime desc
    List<Menu> getAll(int restaurantId);

    // ORDERED dateTime desc
    List<Menu> getBetween(int restaurantId, LocalDate startDate, LocalDate endDate);

    default Menu getWithRestaurant(int id, int restaurantId) {
        throw new UnsupportedOperationException();
    }
}
