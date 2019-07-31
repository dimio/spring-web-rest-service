package ru.javawebinar.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.graduation.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    // find... vs get... ? https://stackoverflow.com/a/24486114/11130103
    List<Vote> findByUserId(int userId);

    Vote findByUserIdAndDateTime(int userId, LocalDateTime dateTime);

    List<Vote> findByRestaurantId(int restaurantId);

    Vote findByRestaurantIdAndDateTime(int restaurantId, LocalDateTime dateTime);

    List<Vote> findByUserIdBetween(int userId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<Vote> getByRestaurantIdBetween(int restaurantId, LocalDateTime dateFrom, LocalDateTime dateTo);
}
