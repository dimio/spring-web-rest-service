package ru.javawebinar.topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.topjava.graduation.util.ValidationUtil.getRootCause;

@SpringJUnitConfig(locations = {
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
//@ExtendWith(TimingExtension.class)
public abstract class AbstractServiceTest {

    @Autowired
    private Environment env;

    //    static {
    //        // needed only for java.util.logging (postgres driver)
    //        SLF4JBridgeHandler.install();
    //    }

    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        assertThrows(exceptionClass, () -> {
            try{
                runnable.run();
            }
            catch (Exception e){
                throw getRootCause(e);
            }
        });
    }
}