package ru.javawebinar.topjava.graduation.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.graduation.model.Vote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Vote> findById(Integer id);

    @Override
    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Vote> findAll(Sort sort);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    //    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.id DESC")
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.dateTime DESC")
    List<Vote> getAllByUserId(@Param("userId") Integer userId);

    //TODO
    List<Vote> getAllByUser_IdOrderByDateTimeDesc(Integer userId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.dateTime BETWEEN :dateFrom AND :dateTo ORDER BY v.dateTime DESC")
    List<Vote> getByUserIdBetween(@Param("userId") Integer userId,
                                  @Param("dateFrom") LocalDateTime dateFrom,
                                  @Param("dateTo") LocalDateTime dateTo);

    //TODO
    List<Vote> getByUser_IdAndDateTimeBetweenOrderByDateTimeDesc(
        Integer userId, LocalDateTime dateFrom, LocalDateTime dateTo
    );

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId ORDER BY v.dateTime DESC")
    List<Vote> getAllByRestaurantId(@Param("restaurantId") Integer restaurantId);

    //TODO
    List<Vote> getAllByRestaurant_IdOrderByDateTimeDesc(Integer restaurantId);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.dateTime BETWEEN :dateFrom AND :dateTo ORDER BY v.dateTime DESC ")
    List<Vote> getByRestaurantIdBetween(@Param("restaurantId") Integer restaurantId,
                                        @Param("dateFrom") LocalDateTime dateFrom,
                                        @Param("dateTo") LocalDateTime dateTo
    );

    //TODO
    List<Vote> getByRestaurant_IdAndDateTimeBetweenOrderByDateTimeDesc(Integer restaurantId,
                                                                       LocalDateTime dateFrom,
                                                                       LocalDateTime dateTo
    );

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT v FROM Vote v WHERE v.dateTime BETWEEN :dateFrom AND :dateTo ORDER BY v.dateTime DESC")
    List<Vote> getBetween(@Param("dateFrom") LocalDateTime dateFrom,
                          @Param("dateTo") LocalDateTime dateTo
    );

    //TODO
    List<Vote> getByDateTimeBetweenOrderByDateTimeDesc(LocalDateTime dateFrom, LocalDateTime dateTo);
}
