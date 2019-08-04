package ru.javawebinar.topjava.graduation.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time", "restaurant_id"}, name = "votes_unique_ucd_idx")})
@BatchSize(size = 200)
public class Vote extends AbstractBaseEntity {

    public static final LocalTime DECISION_TIME = LocalTime.of(11, 00);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    //    TODO @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateTime = LocalDateTime.now();

    public Vote() {
    }

    public Vote(Vote v) {
        this(v.getId(), v.getDateTime(), v.getRestaurant(), v.getUser());
    }

    public Vote(Integer id, @NotNull LocalDateTime dateTime, @NotNull Restaurant restaurant, @NotNull User user) {
        this.id = id;
        this.restaurant = restaurant;
        this.user = user;
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Vote{" +
            "id=" + id +
            ", user=" + user +
            ", dateTime=" + dateTime +
            ", restaurant=" + restaurant +
            '}';
    }
}
