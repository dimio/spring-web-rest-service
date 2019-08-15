package ru.javawebinar.topjava.graduation.repository;

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
    List<Vote> findAllByUserId(int userId);

    //    Vote findByUserIdAndDate(int userId, LocalDateTime dateTime);
    List<Vote> findByUserIdAndDateBetween(int userId, @NotNull LocalDate dateFrom, @NotNull LocalDate dateTo);

    List<Vote> findByRestaurantId(int restaurantId);

    List<Vote> findByRestaurantIdAndDateBetween(int restaurantId, LocalDate dateFrom, LocalDate dateTo);

}
