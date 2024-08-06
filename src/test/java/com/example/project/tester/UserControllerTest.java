package com.example.project.tester;

import com.example.project.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.example.project.utils.DataGenerator;
import org.springframework.http.MediaType;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAndFetchUsers() throws Exception {
        List<User> users = DataGenerator.generateRandomUsers(20000);
        //Testing create a user(sign up)
        for (User user: users) {
            mockMvc.perform(post("/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"userId\":\"" + user.getUserId() + "\", \"username\":\"" + user.getUsername() + "\", \"password\":\"" + user.getPassword() + "\",\"role\": \""+user.getRole()+"\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.userId").value(user.getUserId()))
                    .andExpect(jsonPath("$.message").value("User registered successfully"));
        }
        System.out.println("Complete testing user sign up");

        //Testing interface for user sign in

        for (User user: users) {
            mockMvc.perform(post("/users/signin")
                            .param("username", user.getUsername())
                            .param("password",user.getPassword()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.userId").value(user.getUserId()))
                    .andExpect(jsonPath("$.message").value("Login successfully"));
        }
        System.out.println("Complete testing for user sign in");

        //Testing interface for get a user by userId
        for (User user: users) {
            mockMvc.perform(get("/users").param("userId", user.getUserId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value(user.getUsername()))
                    .andExpect(jsonPath("$.userId").value(user.getUserId()))
                    .andExpect(jsonPath("$.role").value(user.getRole()));
        }

        System.out.println("Complete testing for get a user");


        //Testing interface for update user
        for (User user: users) {
            mockMvc.perform(put("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"userId\":\"" + user.getUserId() + "\", \"username\":\"" + user.getUsername() + "\", \"password\":\"" + user.getPassword() + "\",\"role\": \""+user.getRole()+"updated"+"\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.userId").value(user.getUserId()))
                    .andExpect(jsonPath("$.message").value("User updated successfully"));
        }
        System.out.println("Complete testing for updating users");


    }


}