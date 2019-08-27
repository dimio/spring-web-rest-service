package ru.javawebinar.topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "actual", "dishes"}, name = "menus_unique_restaurant_actual_dishes_idx")})
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

    @NotBlank
    @Size(min = 1, max = 8000)
    @SafeHtml
    @Column(name = "dishes", nullable = false)
    private String dishes;

    @Column(name = "price_int", nullable = false)
    private Integer priceInt;

    @Column(name = "price_fract", nullable = false)
    private Integer priceFract;

    public Menu() {
    }

    public Menu(Menu m) {
        this(m.getId(), m.getActual(), m.getDishes(), m.getPriceInt(), m.getPriceFract());
    }

    public Menu(Integer id, String dishes, Integer priceInt, Integer priceFract) {
        this(id, LocalDate.now(), dishes, priceInt, priceFract);
    }

    public Menu(Integer id, LocalDate actual, String dishes, Integer priceInt, Integer priceFract) {
        super(id);
        //        setAdded(added);
        this.actual = actual;
        this.dishes = dishes;
        this.priceInt = priceInt;
        this.priceFract = priceFract;
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
        //        this.added = (added == null) ? LocalDate.now() : added;
    }

    public String getDishes() {
        return dishes;
    }

    public void setDishes(String dishes) {
        this.dishes = dishes;
    }

    public Integer getPriceInt() {
        return priceInt;
    }

    public void setPriceInt(Integer priceInt) {
        this.priceInt = priceInt;
    }

    public Integer getPriceFract() {
        return priceFract;
    }

    public void setPriceFract(Integer priceFract) {
        this.priceFract = priceFract;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", actual=" + actual +
            ", dishes='" + dishes + '\'' +
            ", priceInt=" + priceInt +
            ", priceFract=" + priceFract +
            '}';
    }
}
