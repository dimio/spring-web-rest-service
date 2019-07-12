package ru.javawebinar.topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "menus")
@BatchSize(size = 200)
public class Menu extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "added", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date added = new Date();

    @Column(name = "dishes", nullable = false)
    private String dishes;

    @Column(name = "price_int", nullable = false)
    private Long priceInt;

    @Column(name = "price_fract", nullable = false)
    private Long priceFract;

    public Menu() {
    }

    public Menu(Menu m) {
        this(m.getId(), m.getAdded(), m.getDishes(), m.getPriceInt(), m.getPriceFract(), m.getRestaurant());
    }

    public Menu(Integer id, @NotNull Date added, String dishes, Long priceInt, Long priceFract, Restaurant restaurant) {
        super(id);
        this.restaurant = restaurant;
        this.added = added;
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

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public String getDishes() {
        return dishes;
    }

    public void setDishes(String dishes) {
        this.dishes = dishes;
    }

    public Long getPriceInt() {
        return priceInt;
    }

    public void setPriceInt(Long priceInt) {
        this.priceInt = priceInt;
    }

    public Long getPriceFract() {
        return priceFract;
    }

    public void setPriceFract(Long priceFract) {
        this.priceFract = priceFract;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", restaurant=" + restaurant +
            ", added=" + added +
            ", dishes='" + dishes + '\'' +
            ", priceInt=" + priceInt +
            ", priceFract=" + priceFract +

            '}';
    }
}
