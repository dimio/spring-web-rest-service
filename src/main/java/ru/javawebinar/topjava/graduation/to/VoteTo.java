package ru.javawebinar.topjava.graduation.to;

import java.time.LocalDateTime;

public class VoteTo extends BaseTo {

    private LocalDateTime dateTime;

    public VoteTo() {
    }

    public VoteTo(Integer id, LocalDateTime dateTime) {
        super(id);
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
