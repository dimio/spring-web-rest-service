package ru.javawebinar.topjava.graduation.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.javawebinar.topjava.graduation.model.AbstractBaseEntity;
import ru.javawebinar.topjava.graduation.model.User;
import ru.javawebinar.topjava.graduation.service.UserService;
import ru.javawebinar.topjava.graduation.to.UserTo;
import ru.javawebinar.topjava.graduation.util.exception.ModificationRestrictionException;

import java.util.List;

import static ru.javawebinar.topjava.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.graduation.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    @Autowired
    private UniqueMailValidator emailValidator;

    private boolean modificationRestriction;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll(Sort.unsorted());
    }

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkModificationAllowed(id);
        service.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        checkModificationAllowed(id);
        service.update(user);
    }

    public void update(UserTo userTo, int id) {
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        checkModificationAllowed(id);
        service.update(userTo);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        checkModificationAllowed(id);
        service.enable(id, enabled);
    }

    private void checkModificationAllowed(int id) {
        if (modificationRestriction && id < AbstractBaseEntity.START_SEQ + 2){
            throw new ModificationRestrictionException();
        }
    }
}