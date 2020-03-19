package ru.javawebinar.topjava.graduation.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.graduation.model.Vote;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    // find... vs get... ? https://stackoverflow.com/a/24486114/11130103
    //NOTE: maybe change it to SQL/HQL query for select only restaurant ID and Name if Restaurant entity will be too big
    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Vote> findAllByUserId(int userId);

    //equal to findByUserIdAndDate if dateFrom == dateTo
    //NOTE: maybe change it to SQL/HQL query ...
    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Vote> findByUserIdAndDateBetween(int userId, @NotNull LocalDate dateFrom, @NotNull LocalDate dateTo);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Vote> findAllByRestaurantId(int restaurantId);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Vote> findByRestaurantIdAndDate(int restaurantId, @NotNull LocalDate date);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Vote> findByRestaurantIdAndDateBetween(int restaurantId, @NotNull LocalDate dateFrom, @NotNull LocalDate dateTo);
}
