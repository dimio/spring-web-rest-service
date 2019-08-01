package ru.javawebinar.topjava.graduation.service;


import org.springframework.data.domain.Sort;
import ru.javawebinar.topjava.graduation.model.User;
import ru.javawebinar.topjava.graduation.to.UserTo;
import ru.javawebinar.topjava.graduation.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    void update(UserTo user);

    List<User> getAll(Sort sort);

    void enable(int id, boolean enable);

}