package ru.javawebinar.topjava.graduation.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public abstract class UserVoteController {
    static final String REST_URL = "/rest/profile/votes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService service;

    public UserVoteController(VoteService service) {
        this.service = service;
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> vote(@PathVariable("id") int restaurantId, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        log.info("vote for restaurant {} by user {}", restaurantId, userId);
        Vote vote = service.vote(userId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(REST_URL + "/{id}")
            .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(vote);
    }

    //дата - если нет - вернуть на текущую
    //    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    public Vote get(@AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        log.info("get vote for user {}", userId);
        return service.getForUserAndDate(userId, LocalDate.now());
    }

    @GetMapping(value = "/all")
    public List<Vote> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        log.info("get all votes for user {}", userId);
        return service.getAllForUser(userId);
    }
}