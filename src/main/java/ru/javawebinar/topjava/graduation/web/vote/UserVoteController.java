package ru.javawebinar.topjava.graduation.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.graduation.AuthorizedUser;
import ru.javawebinar.topjava.graduation.model.Vote;
import ru.javawebinar.topjava.graduation.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.graduation.web.vote.UserVoteController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class UserVoteController {
    static final String REST_URL = "/rest/votes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService service;

    public UserVoteController(VoteService service) {
        this.service = service;
    }

    /**
     * Vote for a restaurant or change own vote to another restaurant (before decision time is end)
     *
     * @param restaurantId - ID (int) of restaurant
     * @param authUser     - auth user credentials
     * @return {@code 201 "Created"} and {@link Vote} entity if vote was successful,
     * {@code 200 OK} if vote change was successful or
     * {@code 422 "Unprocessable Entity"} if vote change was not successful
     */
    @RequestMapping(value = "/restaurant/{restaurantId}", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<Vote> vote(@PathVariable Integer restaurantId, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        log.info("vote for a restaurant {} by user {}", restaurantId, userId);
        Vote vote = service.vote(userId, restaurantId);
        if (vote != null){
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(vote.getId()).toUri();

            return vote.isUpdated() ?
                ResponseEntity.ok().body(vote) :
                ResponseEntity.created(uriOfNewResource).body(vote);
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    }

    // TODO: 13.03.2020 add caching

    /**
     * Get votes (for today or in specified dates diapason) for a restaurant as array
     *
     * @param restaurantId - ID ({@literal int}) of restaurant
     * @param dateFrom     -  start date for get votes (LocalDate), not required
     * @param dateTo       -  end date for get votes (LocalDate), not required
     * @return List of {@link Vote votes} for a restaurant with specified restaurantId
     */
    @GetMapping(value = "/restaurant/{restaurantId}")
    public List<Vote> getForRestaurant(@PathVariable int restaurantId,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
        dateFrom = (dateFrom == null) ? LocalDate.now() : dateFrom;
        dateTo = (dateTo == null) ? LocalDate.now() : dateTo;
        log.info("get votes for a restaurant {} and date between {} and {}", restaurantId, dateFrom, dateTo);
        return service.getForRestaurantAndDate(restaurantId, dateFrom, dateTo);
    }

    /**
     * Get own votes (all or for specified date) as array
     *
     * @param date     - date for which to get votes
     * @param authUser - auth user credentials
     * @return List of own {@link Vote votes}
     */
    @GetMapping()
    public List<Vote> getOwn(@RequestParam(required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        log.info("get votes for user {} and date {}", userId, date);
        return (date == null)
            ? service.getAllForUser(userId)
            : Collections.singletonList(service.getForUserAndDate(userId, date));
    }

    /**
     * Delete own vote for today (before decision time is end)
     *
     * @param authUser - auth user credentials
     * @return {@code 204 "No Content"} if vote deleting was successful or
     * {@code 422 "Unprocessable Entity"} if vote deleting was not successful
     */
    @DeleteMapping()
    public ResponseEntity<Void> delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        return service.delete(authUser.getId(), LocalDate.now())
            ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
            : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    }
}
