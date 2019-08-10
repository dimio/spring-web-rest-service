package ru.javawebinar.topjava.graduation.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.graduation.model.Vote;
import ru.javawebinar.topjava.graduation.service.VoteService;

import java.time.LocalDate;
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

    @GetMapping
    public List<Vote> getAll() {
        log.info("get all votes");
        return service.getAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @GetMapping(value = "/user/{userId}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAllForUser(@PathVariable int userId) {
        log.info("get all votes for user {}", userId);
        return service.getAllForUser(userId);
    }

    //"/user/{userId}/filter" @RequestParam(required = false) LocalDate date
    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote getForUserAndDate(@PathVariable int userId,
                                  @RequestParam(required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        date = (date == null) ? LocalDate.now() : date;
        log.info("get all votes for user {} and date {}", userId, date);
        return service.getForUserAndDate(userId, date);
    }

    @GetMapping(value = "/restaurant/{restaurantId}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAllForRestaurant(@PathVariable int restaurantId) {
        log.info("get all votes for restaurant {}", restaurantId);
        return service.getAllForRestaurant(restaurantId);
    }

    @GetMapping(value = "/restaurant/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getForRestaurantAndDate(@PathVariable int restaurantId,
                                              @RequestParam(required = false)
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        date = (date == null) ? LocalDate.now() : date;
        log.info("get all votes for restaurant {} and date {}", restaurantId, date);
        return service.getForRestaurantAndDate(restaurantId, date);
    }
}
