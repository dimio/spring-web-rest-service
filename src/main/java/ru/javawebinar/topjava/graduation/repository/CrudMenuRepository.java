package ru.javawebinar.topjava.graduation.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.graduation.model.Menu;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {

    @Override
    @Transactional
    Menu save(Menu menu);

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    //get
    @Override
    Optional<Menu> findById(Integer id);

    //getAll
    List<Menu> getAllByRestaurant_IdOrderByAddedDesc(Integer restaurantId);

    //getBetween
    List<Menu> getByRestaurant_IdAndAddedBetweenOrderByAddedDesc(Integer restaurantId, Date startDate, Date endDate);

    //getWithRestaurant
    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    Menu getWitRestaurant(@Param("id") int id, @Param("restaurantId") int restaurantId);
}
