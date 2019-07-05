package ru.javawebinar.topjava.graduation.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("voteDate DESC")
    @BatchSize(size = 200)
    private List<Vote> votes;

    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("added DESC")
    @BatchSize(size = 200)
    private List<Menu> lunchsMenus;

}
