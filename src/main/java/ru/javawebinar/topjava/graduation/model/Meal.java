package ru.javawebinar.topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "meals")
public class Meal extends AbstractNamedEntity {

  //NOTE: check fetch type (maybe - LAZY?)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "menu_id", nullable = false)
  @JsonBackReference
  private Menu menu;

  @Column(name = "price", nullable = false)
//  @Min(0)
  @Positive
  private int price;

  public Meal(){}

  public Meal(Meal m){
    this(m.getId(), m.getName(), m.getPrice());
  }

  public Meal(Integer id, String name, @Positive int price) {
    super(id, name);
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
