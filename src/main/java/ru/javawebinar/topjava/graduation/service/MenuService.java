package ru.javawebinar.topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.graduation.model.Menu;
import ru.javawebinar.topjava.graduation.repository.MenuRepository;
import ru.javawebinar.topjava.graduation.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.graduation.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Menu add(Integer restaurantId, Menu menu) {
        Assert.notNull(restaurantId, "restaurantId must be not null");
        Assert.notNull(menu, "menu must not be null");
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        return menuRepository.save(menu);
    }

    public void delete(int restaurantId, int menuId) {
        checkNotFound(getForRestaurant(restaurantId, menuId), "menu not found");
        menuRepository.deleteById(menuId);
    }

    @Transactional
    public void update(int restaurantId, Menu menu) {
        checkNotFound(getForRestaurant(restaurantId, menu.getId()), "menu not found");
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(menuRepository.save(menu), menu.getId());
    }

    public Menu getForRestaurant(Integer restaurantId, Integer menuId) {
        Assert.notNull(restaurantId, "restaurantId must be not null");
        Assert.notNull(menuId, "menuId must not be null");
        Menu menu = menuRepository.getForRestaurant(menuId, restaurantId).orElse(null);
        checkNotFoundWithId(menu, menuId);
        return menu;
    }

    public List<Menu> getForRestaurantAndDate(Integer restaurantId, LocalDate date) {
        Assert.notNull(restaurantId, "restaurantId must be not null");
        return menuRepository.getByRestaurant_IdAndActual(restaurantId, date);
    }

    public List<Menu> getAllForRestaurant(Integer restaurantId) {
        Assert.notNull(restaurantId, "restaurantId must be not null");
        return menuRepository.getAllByRestaurant_Id(restaurantId);
    }
}
