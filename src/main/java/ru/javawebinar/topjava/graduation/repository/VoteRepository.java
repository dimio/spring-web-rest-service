package ru.javawebinar.topjava.graduation.repository;

import ru.javawebinar.topjava.graduation.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int restaurantId, int userId);

    Vote update(Vote vote, int restaurantId);

    boolean delete(int id, int userId);

    Vote get(int id, int userId);

    List<Vote> getAll();

    List<Vote> getAllByUserId(int userId);

    List<Vote> getByUserIdBetween(int userId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<Vote> getAllByRestaurantId(int restaurantId);

    List<Vote> getByRestaurantIdBetween(int restaurantId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<Vote> getBetween(LocalDateTime dateFrom, LocalDateTime dateTo);
}
