package ru.javawebinar.topjava.graduation.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.graduation.model.Vote;
import ru.javawebinar.topjava.graduation.service.VoteService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {
    static final String REST_URL = "/rest/admin/votes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService service;

    public AdminVoteController(VoteService service) {
        this.service = service;
    }

    /**
     * Get user votes (all or for specified date) as array
     *
     * @param userId - user ID (int)
     * @param date   - date for which to get votes ({@literal LocalDate}), not required
     * @return List of user votes {@link Vote votes}
     */
    @GetMapping(value = "/user/{userId}")
    public List<Vote> getForUserAndDate(@PathVariable int userId,
                                        @RequestParam(required = false)
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get votes for user {} and date {}", userId, date);
        return (date == null) ?
            service.getAllForUser(userId) :
            Collections.singletonList(service.getForUserAndDate(userId, date));
    }

    /**
     * Get all votes for a restaurant as array
     *
     * @param restaurantId - ID ({@literal int}) of restaurant
     * @return List of votes {@link Vote votes}
     */
    @GetMapping(value = "/restaurant/{restaurantId}")
    public List<Vote> getAllForRestaurant(@PathVariable int restaurantId) {
        log.info("get all votes for a restaurant {}", restaurantId);
        return service.getAllForRestaurant(restaurantId);
    }
}
