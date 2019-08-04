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
public class VotingService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private Clock clock;
    private ZoneId zoneId;

    public VotingService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.clock = Clock.systemDefaultZone();
        this.zoneId = ZoneId.systemDefault();
    }

    public void setClockAndTimeZone(LocalDateTime dateTime) {
        this.clock = Clock.fixed(dateTime.atZone(zoneId).toInstant(), zoneId);
    }

    //    @CacheEvict(value = "votes", allEntries = true)
    public Vote vote(Integer userId, Integer restaurantId) {
        return vote(userId, restaurantId, LocalDate.now(clock), LocalTime.now(clock));
    }

    @Transactional
    Vote vote(Integer userId, Integer restaurantId, LocalDate date, LocalTime time) {
        Assert.notNull(restaurantId, "restaurantId must not be null");
        Vote vote = null;
        if (time.isBefore(DECISION_TIME)){
            LocalDateTime currentDateTime = date.atTime(time);
            vote = getForUserAndDate(userId, currentDateTime);
            if (vote != null){
                voteRepository.deleteById(vote.getId());
            }
            vote = new Vote(
                null, currentDateTime,
                restaurantRepository.getOne(restaurantId),
                userRepository.getOne(userId)
            );
            voteRepository.save(vote);
        }
        return vote;
    }

    public List<Vote> getAllForUser(Integer userId) {
        return voteRepository.findByUserId(userId);
    }

    public List<Vote> getAllForRestaurant(Integer restaurantId) {
        return voteRepository.findByRestaurantId(restaurantId);
    }

    public List<Vote> getAll(Sort sort) {
        return voteRepository.findAll(sort);
    }

    public Vote getForUserAndDate(Integer userId, LocalDateTime dateTime) {
        List<Vote> votes = voteRepository.findByUserIdAndDateTimeBetween(userId, dateTime, dateTime);
        return votes.isEmpty() ? null : votes.get(0);
    }

    public Vote getForRestaurantAndDate(Integer restaurantId, LocalDateTime dateTime) {
        List<Vote> votes = voteRepository.findByRestaurantIdAndDateTimeBetween(restaurantId, dateTime, dateTime);
        return votes.isEmpty() ? null : votes.get(0);
    }

}
