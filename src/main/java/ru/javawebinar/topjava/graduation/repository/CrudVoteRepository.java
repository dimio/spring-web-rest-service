package ru.javawebinar.topjava.graduation.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.graduation.model.Vote;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Vote> get(Integer id);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    //    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.id DESC")
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.voteDate DESC")
    List<Vote> getAllByUserId(@Param("userId") Integer userId);

    //TODO
    List<Vote> getAllByUser_IdOrderByVoteDateDesc(Integer userId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.voteDate BETWEEN :dateFrom AND :dateTo ORDER BY v.voteDate DESC")
    List<Vote> getByUserIdBetweenDate(@Param("userId") Integer userId,
                                      @Param("dateFrom") Date dateFrom,
                                      @Param("dateTo") Date dateTo);

    //TODO
    List<Vote> getByUser_IdAndVoteDateBetweenOrderByVoteDateDesc(Integer userId, Date dateFrom, Date dateTo);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId ORDER BY v.voteDate DESC")
    List<Vote> getAllByRestaurantId(@Param("restaurantId") Integer restaurantId);

    //TODO
    List<Vote> getAllByRestaurant_IdOrderByVoteDateDesc(Integer restaurantId);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.voteDate BETWEEN :dateFrom AND :dateTo ORDER BY v.voteDate DESC ")
    List<Vote> getByRestaurantIdBetweenDate(@Param("restaurantId") Integer restaurantId,
                                            @Param("dateFrom") Date dateFrom,
                                            @Param("dateTo") Date dateTo
    );

    //TODO
    List<Vote> getByRestaurant_IdAndVoteDateBetweenOrderByVoteDateDesc(Integer restaurantId,
                                                                       Date dateFrom,
                                                                       Date dateTo);

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.voteDate BETWEEN :dateFrom AND :dateTo ORDER BY v.voteDate DESC")
    List<Vote> getBetweenDate(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

    //TODO
    List<Vote> getByVoteDateBetweenOrderByVoteDateDesc(Date dateFrom, Date dateTo);
}
