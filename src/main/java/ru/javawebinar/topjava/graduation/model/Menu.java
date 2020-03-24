package ru.javawebinar.topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menus",
    indexes = {@Index(name = "menus_restaurant_actual_idx", columnList = "restaurant_id, actual")}
)
@BatchSize(size = 200)
public class Menu extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    @NotNull
    @Column(name = "actual", nullable = false, columnDefinition = "date default now()")
    private LocalDate actual = LocalDate.now();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @BatchSize(size = 200)
    @JsonManagedReference
    private List<Meal> meals;

    public Menu() {
    }

    public Menu(Menu m) {
        this(m.getId(), m.getRestaurant(), m.getMeals(), m.getActual());
    }

    public Menu(Integer id, Restaurant restaurant, List<Meal> meals) {
        this(id, restaurant, meals, LocalDate.now());
    }

    public Menu(Integer id, List<Meal> meals, LocalDate actual) {
        this(id, null, meals, actual);
    }

    public Menu(Integer id, Restaurant restaurant, List<Meal> meals, LocalDate actual) {
        super(id);
        this.restaurant = restaurant;
        this.meals = meals;
        this.actual = actual;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getActual() {
        return actual;
    }

    public void setActual(LocalDate actual) {
        this.actual = actual;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meal) {
        this.meals = meal;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", restaurant=" + restaurant +
            ", actual=" + actual +
            ", meals=" + meals +
            '}';
    }
}
