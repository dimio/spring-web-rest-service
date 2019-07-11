package ru.javawebinar.topjava.graduation;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()){
            //            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            //            mockAuthorize(USER);

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            //            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", 1500, Role.ROLE_ADMIN));
            //            System.out.println();

            //            MealRestController mealController = appCtx.getBean(MealRestController.class);
            //            List<MealTo> filteredMealsWithExceeded =
            //                    mealController.getBetween(
            //                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
            //                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            //            filteredMealsWithExceeded.forEach(System.out::println);
        }
    }
}
