package ru.javawebinar.topjava.graduation.repository;

import ru.javawebinar.topjava.graduation.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant user);

    boolean delete(int id);

    Restaurant get(int id);

    Restaurant getWithMenu(int id);

    Restaurant getByName(String name);

    List<Restaurant> getAll();
}
