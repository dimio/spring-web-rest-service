package ru.javawebinar.topjava.graduation.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.graduation.AuthorizedUser;
import ru.javawebinar.topjava.graduation.model.Vote;
import ru.javawebinar.topjava.graduation.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController {
    static final String REST_URL = "/rest/profile/votes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService service;

    public UserVoteController(VoteService service) {
        this.service = service;
    }

//    @PostMapping(value = "/{restaurantId}")
//    @PutMapping(value = "/{restaurantId}")
    @RequestMapping(value = "/{restaurantId}", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<Vote> vote(@PathVariable int restaurantId, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        log.info("vote for restaurant {} by user {}", restaurantId, userId);
        Vote vote = service.vote(userId, restaurantId);

        if (vote != null){
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(vote.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(vote);
        }
        return ResponseEntity.status(HttpStatus.LOCKED).body(null);
    }

    @DeleteMapping()
    public ResponseEntity delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        return service.delete(authUser.getId(), LocalDate.now())
            ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
            : ResponseEntity.status(HttpStatus.LOCKED).body(null);
    }

    @GetMapping()
    public Vote get(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                    @AuthenticationPrincipal AuthorizedUser authUser) {

        int userId = authUser.getId();
        date = (date == null) ? LocalDate.now() : date;
        //        LocalDate currentDate = date.orElse(LocalDate.now());
        log.info("get vote for user {} and date {}", userId, date);
        return service.getForUserAndDate(userId, date);
    }

    @GetMapping(value = "/all")
    public List<Vote> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        log.info("get all votes for user {}", userId);
        return service.getAllForUser(userId);
    }
}