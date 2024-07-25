package com.example.project.repository;

import com.example.project.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodRepository extends JpaRepository<Good, String> {
    List<Good> findByOrderId(String orderId);
    void deleteByOrderId(String orderId);
}
