package com.example.demo.repository;

import com.example.demo.entity.ThongTinGiaoHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThongTinGiaoHangRepository extends JpaRepository<ThongTinGiaoHang, String> {
    boolean existsByKhachHang_IdAndDcNguoiNhan(String khachHangId, String dcNguoiNhan);
}
