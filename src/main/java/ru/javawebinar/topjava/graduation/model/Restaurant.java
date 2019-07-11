package ru.javawebinar.topjava.graduation.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("voteDate DESC")
    @BatchSize(size = 200)
    private List<Vote> votes;

    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("added DESC")
    @BatchSize(size = 200)
    private List<Menu> lunchMenus;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, List<Vote> votes, List<Menu> lunchMenus) {
        super(id, name);
        this.votes = votes;
        this.lunchMenus = lunchMenus;
    }

    public Restaurant(Restaurant r) {
        this(r.getId(), r.getName(), r.getVotes(), r.getLunchMenus());
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Menu> getLunchMenus() {
        return lunchMenus;
    }

    public void setLunchMenus(List<Menu> lunchMenus) {
        this.lunchMenus = lunchMenus;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
            ", id=" + id +
            "name='" + name + '\'' +
            ", votes=" + votes +
            ", lunchMenus=" + lunchMenus +
            '}';
    }
}
