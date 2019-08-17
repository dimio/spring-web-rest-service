package ru.javawebinar.topjava.graduation.web.user;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javawebinar.topjava.graduation.HasEmail;
import ru.javawebinar.topjava.graduation.model.User;
import ru.javawebinar.topjava.graduation.repository.UserRepository;


@Component
public class UniqueMailValidator implements org.springframework.validation.Validator {

    private final UserRepository repository;

    public UniqueMailValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return HasEmail.class.isAssignableFrom(clazz);
    }

    //ISSUE: by profile update with curl request, userTo has null id (not equal to user's id) and email will not valid
    @Override
    public void validate(Object target, Errors errors) {
        HasEmail user = ((HasEmail) target);
        User dbUser = repository.getByEmail(user.getEmail().toLowerCase());
        if (dbUser != null && !dbUser.getId().equals(user.getId())){
            errors.rejectValue("email", "User with this email already exists");
        }
    }
}
