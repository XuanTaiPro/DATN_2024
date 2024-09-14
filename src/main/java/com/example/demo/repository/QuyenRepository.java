package com.example.demo.repository;

import com.example.demo.entity.Quyen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuyenRepository extends JpaRepository<Quyen,Integer> {
    Quyen getById(Integer id);
}
