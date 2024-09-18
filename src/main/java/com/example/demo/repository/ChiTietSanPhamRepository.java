package com.example.demo.repository;

import com.example.demo.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham,String> {
    ChiTietSanPham getById(String id);
}
