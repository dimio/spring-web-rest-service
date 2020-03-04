package ru.javawebinar.topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "meals")
public class Meal extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @JsonBackReference
    private Menu menu;

    @Column(name = "price", nullable = false)
    @Positive
    private int price;

    public Meal() {
    }

    public Meal(Meal m) {
        this(m.getId(), m.getName(), m.getMenu(), m.getPrice());
    }

    public Meal(Integer id, String name, @Positive int price) {
        super(id, name);
        this.menu = null;
        this.price = price;
    }

    public Meal(Integer id, String name, Menu menu, @Positive int price) {
        super(id, name);
        this.menu = menu;
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
