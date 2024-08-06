package com.example.project.tester;

import com.example.project.entity.Good;
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
public class GoodControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAndFetchGoods() throws Exception {
        List<Good> goods = DataGenerator.generateRandomGoods(20000);
        //Testing create a good
        for (Good good : goods) {
            mockMvc.perform(post("/goods")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"goodId\":\"" + good.getGoodId() + "\", \"orderId\":\"" + good.getOrderId() + "\", \"description\":\"" + good.getDescription() + "\",\"quantity\": \""+good.getQuantity()+"\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.goodId").value(good.getGoodId()))
                    .andExpect(jsonPath("$.orderId").value(good.getOrderId()))
                    .andExpect(jsonPath("$.message").value("good added successfully"));
        }
        System.out.println("Complete testing create good");

        //Testing interface for get a good by goodId

        for (Good good : goods) {
            mockMvc.perform(get("/goods")
                            .param("goodId", good.getGoodId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.goodId").value(good.getGoodId()))
                    .andExpect(jsonPath("$.orderId").value(good.getOrderId()))
                    .andExpect(jsonPath("$.description").value(good.getDescription()))
                    .andExpect(jsonPath("$.quantity").value(good.getQuantity()));
        }
        System.out.println("Complete testing for getting a good");

        //Testing interface for get goods by orderId

        for (Good good : goods) {
            mockMvc.perform(get("/goods/orderId")
                            .param("orderId", good.getOrderId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].goodId").value(good.getGoodId()))
                    .andExpect(jsonPath("$[0].orderId").value(good.getOrderId()))
                    .andExpect(jsonPath("$[0].description").value(good.getDescription()))
                    .andExpect(jsonPath("$[0].quantity").value(good.getQuantity()));
        }
        System.out.println("Complete testing for getting goods for a order");

        //Testing interface for update user
        for (Good good : goods) {
            mockMvc.perform(put("/goods")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"goodId\":\"" + good.getGoodId() + "\", \"orderId\":\"" + good.getOrderId() + "\", \"description\":\"" + good.getDescription()+"updated" + "\",\"quantity\": \""+good.getQuantity()+"\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.goodId").value(good.getGoodId()))
                    .andExpect(jsonPath("$.message").value("good updated successfully"));
        }
        System.out.println("Complete testing for updating goods");

    }
}