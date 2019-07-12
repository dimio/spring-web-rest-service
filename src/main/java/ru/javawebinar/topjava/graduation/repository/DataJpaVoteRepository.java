package ru.javawebinar.topjava.graduation.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.graduation.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private static final Sort SORT_USER_DATE = new Sort(Sort.Direction.DESC, "user_id", "date_time");

    private final CrudVoteRepository crudVoteRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository, CrudRestaurantRepository crudRestaurantRepository, CrudUserRepository crudUserRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Vote save(Vote vote, int restaurantId, int userId) {
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        vote.setUser(crudUserRepository.getOne(userId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public Vote update(Vote vote, int restaurantId) {
        if (vote.getId() == null){
            return null;
        }
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id, userId) != 0;
    }

    @Override
    public Vote get(int id, int userId) {
        return crudVoteRepository.findById(id)
            .filter(vote -> vote.getUser().getId() == userId)
            .orElse(null);
    }

    @Override
    public List<Vote> getAll() {
        return crudVoteRepository.findAll(SORT_USER_DATE);
    }

    @Override
    public List<Vote> getAllByUserId(int userId) {
        //        return crudVoteRepository.getAllByUserId(userId);
        return crudVoteRepository.getAllByUser_IdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Vote> getByUserIdBetween(int userId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return crudVoteRepository.getByUser_IdAndDateTimeBetweenOrderByDateTimeDesc(userId,
            dateFrom, dateTo);
        //        return crudVoteRepository.getByUserIdAndDate(userId, dateFrom, dateTo);
    }

    @Override
    public List<Vote> getAllByRestaurantId(int restaurantId) {
        return crudVoteRepository.getAllByRestaurant_IdOrderByDateTimeDesc(restaurantId);
    }

    @Override
    public List<Vote> getByRestaurantIdBetween(int restaurantId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return crudVoteRepository.getByRestaurant_IdAndDateTimeBetweenOrderByDateTimeDesc(restaurantId,
            dateFrom, dateTo);
    }

    @Override
    public List<Vote> getBetween(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return crudVoteRepository.getByDateTimeBetweenOrderByDateTimeDesc(dateFrom, dateTo);
    }

}
