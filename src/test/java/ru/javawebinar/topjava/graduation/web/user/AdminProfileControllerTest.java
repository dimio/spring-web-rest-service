package ru.javawebinar.topjava.graduation.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.graduation.model.Role;
import ru.javawebinar.topjava.graduation.model.User;
import ru.javawebinar.topjava.graduation.service.UserService;
import ru.javawebinar.topjava.graduation.web.AbstractControllerTest;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.graduation.TestUtil.readFromJson;
import static ru.javawebinar.topjava.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.graduation.UserTestData.*;

class AdminProfileControllerTest extends AbstractControllerTest {

    @Autowired
    protected UserService userService;

    private static final String REST_URL = AdminProfileController.REST_URL;

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
            .with(userHttpBasic(USER)))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(get(REST_URL + "/{id}", ADMIN_ID)
            .with(userHttpBasic(ADMIN)))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(ADMIN));
    }

    @Test
    void getByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "/by")
            .param("email", ADMIN.getEmail())
            .with(userHttpBasic(ADMIN)))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(ADMIN));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(REST_URL)
            .with(userHttpBasic(ADMIN)))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(USER, ADMIN));
    }

    @Test
    void createWithLocation() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(ADMIN))
            .content(jsonWithPassword(expected, "newPass")))
            .andExpect(status().isCreated());

        User returned = readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(Sort.by("id")), USER, ADMIN, expected);
    }

    @Test
    void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + "/{id}", USER_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .with(userHttpBasic(ADMIN))
            .content(jsonWithPassword(updated, USER.getPassword())))
            .andExpect(status().isNoContent());

        assertMatch(userService.get(USER_ID), updated);
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(REST_URL + "/{id}", USER_ID)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isNoContent());
        assertMatch(userService.getAll(Sort.unsorted()), ADMIN);
    }
}
