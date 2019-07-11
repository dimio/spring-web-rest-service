package ru.javawebinar.topjava.graduation.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.graduation.model.Menu;

import java.util.Date;
import java.util.List;

@Repository
public class DataJpaMenuRepository implements MenuRepository {

    private final CrudMenuRepository crudMenuRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaMenuRepository(CrudMenuRepository crudMenuRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMenuRepository = crudMenuRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Menu save(Menu menu, int restaurantId) {
        if (!menu.isNew() && get(menu.getId(), restaurantId) == null){
            return null;
        }
        menu.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudMenuRepository.save(menu);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudMenuRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public Menu get(int id, int restaurantId) {
        return crudMenuRepository.findById(id)
            .filter(menu -> menu.getRestaurant().getId() == restaurantId)
            .orElse(null);
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return crudMenuRepository.getAllByRestaurant_IdOrderByAddedDesc(restaurantId);
    }

    @Override
    public List<Menu> getBetween(int restaurantId, Date startDate, Date endDate) {
        return crudMenuRepository.getByRestaurant_IdAndAddedBetweenOrderByAddedDesc(
            restaurantId, startDate, endDate
        );
    }

    @Override
    public Menu getWithRestaurant(int id, int restaurantId) {
        return crudMenuRepository.getWitRestaurant(id, restaurantId);
    }
}
