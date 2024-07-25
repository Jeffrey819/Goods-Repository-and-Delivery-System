package com.example.project.service;

import com.example.project.entity.Good;
import com.example.project.repository.GoodRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodService {
    private final GoodRepository goodRepository;

    public GoodService(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    public void save(Good good) {
        goodRepository.save(good);
    }

    public List<Good> findByOrderId(String orderId) {
        List<Good> goods = goodRepository.findByOrderId(orderId);
        return goods;
    }

    public Optional<Good> findByGoodId(String goodId) {
        Optional<Good> good = goodRepository.findById(goodId);
        return good;
    }

    public void deleteByGoodId(String goodId) {
        goodRepository.deleteById(goodId);
    }
    @Transactional
    public void deleteByOrderId(String orderId) {
        goodRepository.deleteByOrderId(orderId);
    }
}
