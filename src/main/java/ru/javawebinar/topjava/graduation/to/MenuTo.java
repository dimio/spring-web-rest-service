package ru.javawebinar.topjava.graduation.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class MenuTo extends BaseTo {

    @NotBlank
    @Size(min = 1, max = 200)
    private String name;

    private LocalDate added;

    private String dishes;

    @NotNull
    private Long priceInt;

    @NotNull
    private Long priceFract;

    public MenuTo() {
    }

    public MenuTo(Integer id, String name, LocalDate added, String dishes, Long priceInt, Long priceFract) {
        super(id);
        this.name = name;
        this.added = added;
        this.dishes = dishes;
        this.priceInt = priceInt;
        this.priceFract = priceFract;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getAdded() {
        return added;
    }

    public void setAdded(LocalDate added) {
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
        return "MenuTo{" +
            "id='" + id + '\'' +
            ", added=" + added +
            ", dishes='" + dishes + '\'' +
            ", priceInt=" + priceInt +
            ", priceFract=" + priceFract +
            ", name=" + name +
            '}';
    }
}
