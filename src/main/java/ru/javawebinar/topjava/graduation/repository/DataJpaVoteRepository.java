package ru.javawebinar.topjava.graduation.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.graduation.model.Vote;

import java.util.Date;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private static final Sort SORT_NAME_ID = new Sort(Sort.Direction.ASC, "name", "id");

    private final CrudVoteRepository crudVoteRepository;
    private final CrudRestaurantRepository restaurantRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository, CrudUserRepository crudUserRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Vote save(Vote vote, int restaurantId, int userId) {
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        vote.setUser(crudUserRepository.getOne(userId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public Vote update(Vote vote, int restaurantId) {
        if (vote.getId() == null) {
            return null;
        }
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public boolean delete(int id) {
        return crudVoteRepository.delete(id) != 0;
    }

    @Override
    public Vote get(int id) {
        return crudVoteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Vote> getAll(){
        return crudVoteRepository.findAll(SORT_NAME_ID);
    }

    @Override
    public List<Vote> getAllByUserId(int userId) {
//        return crudVoteRepository.getAllByUserId(userId);
        return crudVoteRepository.getAllByUser_IdOrderByVoteDateDesc(userId);
    }

    @Override
    public List<Vote> getByUserIdBetweenDate(int userId, Date dateFrom, Date dateTo) {
        return crudVoteRepository.getByUser_IdAndVoteDateBetweenOrderByVoteDateDesc(userId, dateFrom, dateTo);
//        return crudVoteRepository.getByUserIdAndDate(userId, dateFrom, dateTo);
    }

    @Override
    public List<Vote> getAllByRestaurantId(int restaurantId) {
        return crudVoteRepository.getAllByRestaurant_IdOrderByVoteDateDesc(restaurantId);
    }

    @Override
    public List<Vote> getByRestaurantIdBetweenDate(int restaurantId, Date dateFrom, Date dateTo) {
        return crudVoteRepository.getByRestaurant_IdAndVoteDateBetweenOrderByVoteDateDesc(restaurantId,
            dateFrom, dateTo);
    }

    @Override
    public List<Vote> getBetweenDate(Date dateFrom, Date dateTo) {
        return crudVoteRepository.getByVoteDateBetweenOrderByVoteDateDesc(dateFrom, dateTo);
    }

}
