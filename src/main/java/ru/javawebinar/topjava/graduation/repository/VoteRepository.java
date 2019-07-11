package ru.javawebinar.topjava.graduation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.javawebinar.topjava.graduation.model.Vote;

import java.util.Date;
import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int restaurantId, int userId);

    Vote update(Vote vote, int restaurantId);

    boolean delete(int id);

    Vote get(int id);

    List<Vote> getAll();

    List<Vote> getAllByUserId(int userId);

    List<Vote> getByUserIdBetweenDate(int userId, Date dateFrom, Date dateTo);

    List<Vote> getAllByRestaurantId(int restaurantId);

    List<Vote> getByRestaurantIdBetweenDate(int restaurantId, Date dateFrom, Date dateTo);

    List<Vote> getBetweenDate(Date dateFrom, Date dateTo);

//    List<Vote> findByDateAndUserId(int userId, Date dateFrom, Date dateTo);

//    List<Vote> findByDate(Date dateFrom, Date dateTo);

    // ORDERED dateTime desc
    default List<Vote> getWithPagination(int userId, Pageable page) {
        throw new UnsupportedOperationException();
    }

}
