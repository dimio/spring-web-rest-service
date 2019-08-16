package ru.javawebinar.topjava.graduation.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.graduation.model.Vote;
import ru.javawebinar.topjava.graduation.repository.RestaurantRepository;
import ru.javawebinar.topjava.graduation.repository.UserRepository;
import ru.javawebinar.topjava.graduation.repository.VoteRepository;

import java.time.*;
import java.util.List;

import static ru.javawebinar.topjava.graduation.model.Vote.DECISION_TIME;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private Clock clock;
    private ZoneId zoneId;

    public VoteService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.clock = Clock.systemDefaultZone();
        this.zoneId = ZoneId.systemDefault();
    }

    public void setClockAndTimeZone(LocalDateTime dateTime) {
        this.clock = Clock.fixed(dateTime.atZone(zoneId).toInstant(), zoneId);
    }

    public Vote vote(Integer userId, Integer restaurantId) {
        return vote(userId, restaurantId, LocalDate.now(clock), LocalTime.now(clock));
    }

    //TODO: update vote if it exist, don't create new,
    // if new vote restaurantId is equal to exist vote restaurantId - do nothing
    @Transactional
    Vote vote(Integer userId, Integer restaurantId, LocalDate date, LocalTime time) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(restaurantId, "restaurantId must not be null");
        Vote vote = null;
        if (time.isBefore(DECISION_TIME)){
            vote = getForUserAndDate(userId, date);
            if (vote != null){
                voteRepository.deleteById(vote.getId());
            }
            vote = new Vote(
                null, date,
                restaurantRepository.getOne(restaurantId),
                userRepository.getOne(userId)
            );
            vote = voteRepository.save(vote);
        }
        return vote;
    }

    @Transactional
    public boolean delete(int userId, LocalDate date) {
        if (LocalTime.now(clock).isBefore(DECISION_TIME)){
            Vote vote = getForUserAndDate(userId, date);
            if (vote != null){
                voteRepository.deleteById(vote.getId());
                return true;
            }
        }
        return false;
    }

    public List<Vote> getAllForUser(Integer userId) {
        return voteRepository.findAllByUserId(userId);
    }

    public List<Vote> getAllForRestaurant(Integer restaurantId) {
        return voteRepository.findByRestaurantId(restaurantId);
    }

    public List<Vote> getAll(Sort sort) {
        return voteRepository.findAll(sort);
    }

    //make DateBetween for most universality?
    public Vote getForUserAndDate(Integer userId, LocalDate date) {
        List<Vote> votes = voteRepository.findByUserIdAndDateBetween(userId, date, date);
        // use Optional votes.stream().findXXX ?
        return votes.isEmpty() ? null : votes.get(0);
    }

    //make DateBetween for most universality?
    public List<Vote> getForRestaurantAndDate(Integer restaurantId, LocalDate date) {
        return voteRepository.findByRestaurantIdAndDateBetween(restaurantId, date, date);
    }

}
