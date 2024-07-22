package com.example.project.service;

import com.example.project.entity.Good;
import com.example.project.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodService {
    @Autowired
    private GoodRepository goodRepository;

    public void save(Good good) {
        goodRepository.save(good);
    }

    public Optional<List<Good>> findByOrderId(String orderId) {
        Optional<List<Good>> goods = goodRepository.findByOrderId(orderId);
        return goods;
    }

    public Optional<Good> findByGoodId(String goodId) {
        Optional<Good> good = goodRepository.findById(goodId);
        return good;
    }

    public void deleteByGoodId(String goodId) {
        goodRepository.deleteById(goodId);
    }
}
