package ru.javawebinar.topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.graduation.model.Restaurant;
import ru.javawebinar.topjava.graduation.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant add(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(id, restaurantRepository.delete(id));
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    public Restaurant get(Integer restaurantId) {
        Assert.notNull(restaurantId, "restaurantId must be not null");
        return restaurantRepository.findById(restaurantId).orElse(null);
    }

    public Restaurant getByName(String name) {
        Assert.notNull(name, "restaurant name must be not null");
        return restaurantRepository.findByName(name).orElse(null);
    }

    public Restaurant getWithMenus(Integer restaurantId) {
        Assert.notNull(restaurantId, "restaurantId must be not null");
        return restaurantRepository.getWithMenus(restaurantId).orElse(null);
    }

    public Restaurant getWithVotes(Integer restaurantId) {
        Assert.notNull(restaurantId, "restaurantId must be not null");
        return restaurantRepository.getWithVotes(restaurantId).orElse(null);
    }

    public List<Restaurant> getAll(Sort sort) {
        return restaurantRepository.findAll(sort);
    }

    public List<Restaurant> getAllWithMenusForDate(LocalDate date) {
        return restaurantRepository.getAllWithMenusForDate(date);
    }
}
