package ru.javawebinar.topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.graduation.model.Menu;
import ru.javawebinar.topjava.graduation.model.Restaurant;
import ru.javawebinar.topjava.graduation.repository.MenuRepository;
import ru.javawebinar.topjava.graduation.repository.RestaurantRepository;
import ru.javawebinar.topjava.graduation.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.graduation.util.DateTimeUtil.MAX_DATE;
import static ru.javawebinar.topjava.graduation.util.DateTimeUtil.MIN_DATE;
import static ru.javawebinar.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, MenuRepository menuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }

    public Restaurant add(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(id, restaurantRepository.delete(id));
    }

    public void update(Restaurant restaurant) throws NotFoundException {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    public Restaurant get(Integer restaurantId) {
        Assert.notNull(restaurantId, "restaurantId must be not null");
        return restaurantRepository.findById(restaurantId).orElse(null);
    }

    public List<Restaurant> getAll(Sort sort) {
        return restaurantRepository.findAll(sort);
    }

    public Menu addMenu(Integer restaurantId, Menu menu) {
        Assert.notNull(restaurantId, "restaurantId must be not null");
        Assert.notNull(menu, "menu must not be null");
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        return menuRepository.save(menu);
    }

    public void deleteMenu(int restaurantId, int menuId) throws NotFoundException {
        Assert.notNull(getMenuForRestaurant(restaurantId, menuId), "menu not found");
        menuRepository.deleteById(menuId);
    }

    public void updateMenu(Integer restaurantId, Menu menu) throws NotFoundException {
        Assert.notNull(restaurantId, "restaurantId must be not null");
        Assert.notNull(menu, "menu must not be null");
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(menuRepository.save(menu), menu.getId());
    }

    public Menu getMenuForRestaurant(int restaurantId, int menuId) throws NotFoundException {
        Menu menu = menuRepository.getForRestaurant(menuId, restaurantId);
        checkNotFoundWithId(menu, menuId);
        return menu;
    }

    public List<Menu> getMenusForRestaurantBetweenDate(int restaurantId, LocalDate startDate, LocalDate endDate) throws NotFoundException {
        //include limits startDate and endDate in auto-generated query
        startDate = startDate.minusDays(1);
        endDate = endDate.plusDays(1);
        return menuRepository.getByRestaurant_IdAndAddedBetweenOrderByAddedDesc(restaurantId, startDate, endDate);
    }

    public List<Menu> getAllMenusForRestaurant(int restaurantId) throws NotFoundException {
        return menuRepository.getByRestaurant_IdAndAddedBetweenOrderByAddedDesc(
            restaurantId, MIN_DATE, MAX_DATE
        );
    }
}
