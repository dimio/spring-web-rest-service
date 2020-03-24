package ru.javawebinar.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.graduation.model.Meal;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal meal);

    @Override
    Optional<Meal> findById(Integer id);

    @Query("SELECT m FROM Meal m WHERE m.id=:mealId AND m.menu.id=:menuId")
    Optional<Meal> getForMenu(@Param("menuId") int menuId, @Param("mealId") int mealId);

    List<Meal> getAllByMenu_Id(Integer menuId);
}
