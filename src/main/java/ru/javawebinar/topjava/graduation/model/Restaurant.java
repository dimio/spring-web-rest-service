package ru.javawebinar.topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("added DESC")
    @BatchSize(size = 200)
    @JsonManagedReference
    private List<Menu> lunchMenus;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, List<Menu> lunchMenus) {
        super(id, name);
        this.lunchMenus = lunchMenus;
    }

    public Restaurant(Restaurant r) {
        this(r.getId(), r.getName(), r.getLunchMenus());
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
            "id=" + id +
            ", name='" + name + '\'' +
            //            ", lunchMenus=" + lunchMenus +
            '}';
    }
}
