package com.example.demo.repository;

import com.example.demo.entity.ThongTinGiaoHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThongTinGiaoHangRepository extends JpaRepository<ThongTinGiaoHang, Integer> {
    boolean existsByTenBaiHat(String tenBH);
    boolean existsByKhachHang_IdAndDcNguoiNhan(Integer khachHangId, String dcNguoiNhan);
}
