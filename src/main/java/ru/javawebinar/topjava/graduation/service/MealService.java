package ru.javawebinar.topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.graduation.model.Meal;
import ru.javawebinar.topjava.graduation.repository.MealRepository;
import ru.javawebinar.topjava.graduation.repository.MenuRepository;

import java.util.List;

import static ru.javawebinar.topjava.graduation.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MenuRepository menuRepository;
    private final MealRepository mealRepository;

    //TODO: 16.03.2020 check menu owned for a restaurant (make overloaded methods, accept a restaurantId, menuId & meal)

    @Autowired
    public MealService(MenuRepository menuRepository, MealRepository mealRepository) {
        this.menuRepository = menuRepository;
        this.mealRepository = mealRepository;
    }

    public Meal add(Integer restaurantId, Integer menuId, Meal meal) {
        Assert.notNull(restaurantId, "restaurantId must be not null");
        Assert.notNull(menuId, "menuId must be not null");
        checkNotFound(menuRepository.getForRestaurant(menuId, restaurantId), "menu not found");
        return this.add(menuId, meal);
    }

    @Transactional
    public Meal add(Integer menuId, Meal meal) {
        Assert.notNull(meal, "meal must not be null");
        meal.setMenu(menuRepository.getOne(menuId));
        return mealRepository.save(meal);
    }

    public void delete(int menuId, int mealId) {
        checkNotFound(getForMenu(menuId, mealId), "meal not found"); //threw checkNotFoundWithId
        mealRepository.deleteById(mealId);
    }

    @Transactional
    public void update(int menuId, Meal meal) {
        checkNotFound(getForMenu(menuId, meal.getId()), "meal not found"); //threw checkNotFoundWithId
        meal.setMenu(menuRepository.getOne(menuId));
        checkNotFoundWithId(mealRepository.save(meal), meal.getId());
    }

    public Meal getForMenu(Integer menuId, Integer mealId) {
        Assert.notNull(menuId, "menuId must be not null");
        Assert.notNull(mealId, "mealId must not be null");
        Meal meal = mealRepository.getForMenu(menuId, mealId).orElse(null);
        checkNotFoundWithId(meal, mealId);
        return meal;
    }

    public List<Meal> getAllForMenu(Integer menuId) {
        Assert.notNull(menuId, "menuId must be not null");
        return mealRepository.getAllByMenu_Id(menuId);
    }
}
