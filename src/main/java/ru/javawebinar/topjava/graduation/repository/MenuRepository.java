package ru.javawebinar.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.graduation.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Override
    @Transactional
    Menu save(Menu menu);

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Override
    Optional<Menu> findById(Integer id);

    //getBetween and getAll
    List<Menu> getByRestaurant_IdAndAddedBetweenOrderByAddedDesc(Integer restaurantId, LocalDate startDate, LocalDate endDate);

    //getWithRestaurant
    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    Menu getWitRestaurant(@Param("id") int id, @Param("restaurantId") int restaurantId);
}
