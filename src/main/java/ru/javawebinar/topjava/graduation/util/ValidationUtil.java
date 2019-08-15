package ru.javawebinar.topjava.graduation.util;

import org.slf4j.Logger;
import ru.javawebinar.topjava.graduation.HasId;
import ru.javawebinar.topjava.graduation.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.graduation.util.exception.NotFoundException;

import javax.lang.model.type.ErrorType;
import javax.servlet.http.HttpServletRequest;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String arg) {
        if (!found){
            throw new NotFoundException(arg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()){
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        //      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()){
            bean.setId(id);
        }
        else if (bean.getId() != id){
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)){
            result = cause;
        }
        return result;
    }
}