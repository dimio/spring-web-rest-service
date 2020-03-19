package ru.javawebinar.topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "restaurants")
@NamedEntityGraph(
    name = "graph.Restaurant.menus",
    attributeNodes = {
        @NamedAttributeNode(value = "menus", subgraph = "graph.Restaurant.menus.meals")
    },
    subgraphs = {
        @NamedSubgraph(
            name = "graph.Restaurant.menus.meals",
            attributeNodes = {@NamedAttributeNode("meals")}
        )
    }
)
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    //    @OrderBy("actual DESC")
    @BatchSize(size = 200)
    @JsonManagedReference
    private Set<Menu> menus; //to Set

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, Set<Menu> menus) {
        super(id, name);
        this.menus = menus;
    }

    public Restaurant(Restaurant r) {
        this(r.getId(), r.getName(), r.getMenus());
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
