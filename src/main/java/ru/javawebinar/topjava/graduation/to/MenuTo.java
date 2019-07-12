package ru.javawebinar.topjava.graduation.to;

import java.time.LocalDate;

public class MenuTo extends BaseTo {

    private LocalDate added;

    private String dishes;

    private Long priceInt;

    private Long priceFract;

    public MenuTo() {
    }

    public MenuTo(Integer id, LocalDate added, String dishes, Long priceInt, Long priceFract) {
        super(id);
        this.added = added;
        this.dishes = dishes;
        this.priceInt = priceInt;
        this.priceFract = priceFract;
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
}
