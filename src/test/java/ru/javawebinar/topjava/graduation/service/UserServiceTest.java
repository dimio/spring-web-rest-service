package ru.javawebinar.topjava.graduation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import ru.javawebinar.topjava.graduation.model.Role;
import ru.javawebinar.topjava.graduation.model.User;
import ru.javawebinar.topjava.graduation.service.AbstractServiceTest;
import ru.javawebinar.topjava.graduation.service.UserService;
import ru.javawebinar.topjava.graduation.util.exception.NotFoundException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javawebinar.topjava.graduation.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    //    @Autowired
    //    private CacheManager cacheManager;

    @BeforeEach
    void setUp() throws Exception {
        //        cacheManager.getCache("users").clear();
    }

    @Test
    void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", Collections.emptyList(), false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = service.create(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(newUser, created);
        assertMatch(service.getAll(Sort.by(Sort.Direction.DESC, "id")), newUser, ADMIN, USER);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
            service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
    void delete() throws Exception {
        service.delete(USER_ID);
        assertMatch(service.getAll(Sort.unsorted()), ADMIN);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
            service.delete(1));
    }

    @Test
    void get() throws Exception {
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
            service.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(new User(updated));
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<User> all = service.getAll(Sort.by(Sort.Direction.ASC, "name"));
        assertMatch(all, ADMIN, USER);
    }

    @Test
    void enable() {
        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }
}