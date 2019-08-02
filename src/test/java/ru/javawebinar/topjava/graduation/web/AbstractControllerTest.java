package ru.javawebinar.topjava.graduation.web;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.javawebinar.topjava.graduation.service.UserService;
import ru.javawebinar.topjava.graduation.util.exception.ErrorType;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringJUnitWebConfig(locations = {
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-mvc.xml",
    "classpath:spring/spring-db.xml"
})

@Transactional
abstract public class AbstractControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    protected MockMvc mockMvc;

    //    @Autowired
    //    private CacheManager cacheManager;

    //    @Autowired(required = false)
    //    private JpaUtil jpaUtil;

    @Autowired
    protected UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    //    @Autowired
    //    protected MessageUtil messageUtil;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(CHARACTER_ENCODING_FILTER)
            .apply(springSecurity())
            .build();
    }

    @BeforeEach
    void setUp() {
        //        cacheManager.getCache("users").clear();
        //        jpaUtil.clear2ndLevelHibernateCache();
    }

    //    private String getMessage(String code) {
    //        return messageUtil.getMessage(code, MessageUtil.RU_LOCALE);
    //    }

    public ResultMatcher errorType(ErrorType type) {
        return jsonPath("$.type").value(type.name());
    }

    //    public ResultMatcher detailMessage(String code) {
    //            return jsonPath("$.details").value(getMessage(code));
    //    }
}
