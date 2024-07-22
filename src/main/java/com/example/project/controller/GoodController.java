package com.example.project.controller;

import com.example.project.entity.Good;
import com.example.project.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private GoodService goodService;

    @GetMapping
    public ResponseEntity<Good> getGoodByGoodId(@RequestParam String goodId) {
        Optional<Good> good = goodService.findByGoodId(goodId);
        if (good.isPresent()) {
            return ResponseEntity.ok(good.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/orderId")
    public ResponseEntity<List<Good>> getGoodByOrderId(@RequestParam String orderId) {
        Optional<List<Good>> goods = goodService.findByOrderId(orderId);
        if (goods.isPresent()) {
            return ResponseEntity.ok(goods.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<Map<String,String>> updateGood(@RequestBody Good good) {
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
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String,String>> deleteGood(@RequestParam String goodId) {
        goodService.deleteByGoodId(goodId);
        Map<String,String> info = new HashMap<>();
        info.put("goodId",goodId);
        info.put("message","good deleted successfully");
        return ResponseEntity.ok(info);

    }
}
