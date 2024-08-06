package com.example.project.controller;

import com.example.project.entity.Good;
import com.example.project.service.GoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/goods")
public class GoodController {
    private final GoodService goodService;

    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @GetMapping
    public ResponseEntity<?> getGoodByGoodId(@RequestParam("goodId") String goodId) {
        Optional<Good> good = goodService.findByGoodId(goodId);
        if (good.isPresent()) {
            return ResponseEntity.ok(good.get());
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Good with goodId " + goodId + " not found");
        }
    }

    @GetMapping("/orderId")
    public ResponseEntity<?> getGoodByOrderId(@RequestParam("orderId") String orderId) {
        List<Good> goods = goodService.findByOrderId(orderId);
        if (goods.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goods of Order with OrderId " + orderId + " not found");
        }
        else
        {
            return ResponseEntity.ok(goods);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,String>> addGood(@RequestBody Good good) {
        Map<String,String> info = new HashMap<>();
        goodService.save(good);
        info.put("goodId",good.getGoodId());
        info.put("orderId",good.getOrderId());
        info.put("message","good added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(info);
    }

    @PutMapping
    public ResponseEntity<?> updateGood(@RequestBody Good good) {
        Optional<Good> oldGood = goodService.findByGoodId(good.getGoodId());
        if (oldGood.isPresent()) {
            goodService.save(good);
            Map<String,String> info = new HashMap<>();
            info.put("goodId",oldGood.get().getGoodId());
            info.put("message","good updated successfully");
            return ResponseEntity.ok(info);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Good with goodId " + good.getGoodId() + " not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String,String>> deleteGood(@RequestParam("goodId") String goodId) {
        goodService.deleteByGoodId(goodId);
        Map<String,String> info = new HashMap<>();
        info.put("goodId",goodId);
        info.put("message","good deleted successfully");
        return ResponseEntity.ok(info);

    }

    @DeleteMapping("/orderId")
    public ResponseEntity<Map<String,String>> deleteByOrderId(@RequestParam("orderId") String orderId) {
        goodService.deleteByOrderId(orderId);
        Map<String,String> info = new HashMap<>();
        info.put("orderId",orderId);
        info.put("message","goods of Order deleted successfully");
        return ResponseEntity.ok(info);
    }
}
