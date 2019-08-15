package ru.javawebinar.topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"added", "name", "restaurant_id"}, name = "menus_unique_added_name_restaurant_idx")})
@BatchSize(size = 200)
public class Menu extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    @NotNull
    @Column(name = "added", nullable = false, columnDefinition = "timestamp default now()")
    //    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate added;

    @NotBlank
    @Size(min = 1)
    @SafeHtml
    @Column(name = "dishes", nullable = false)
    private String dishes;

    @NotNull
    @Range(min = 0)
    @Column(name = "price_int", nullable = false)
    private Long priceInt;

    @NotNull
    @Range(min = 0)
    @Column(name = "price_fract", nullable = false)
    private Long priceFract;

    public Menu() {
    }

    public Menu(Menu m) {
        this(m.getId(), m.getName(), m.getAdded(), m.getDishes(), m.getPriceInt(), m.getPriceFract());
    }

    public Menu(Integer id, String name, LocalDate added, String dishes, Long priceInt, Long priceFract) {
        super(id, name);
        setAdded(added);
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

    public LocalDate getAdded() {
        return added;
    }

    public void setAdded(LocalDate added) {
        this.added = (added == null) ? LocalDate.now() : added;
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
