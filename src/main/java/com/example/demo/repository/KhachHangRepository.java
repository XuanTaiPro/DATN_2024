package com.example.demo.repository;

import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KhachHangRepository extends JpaRepository<KhachHang,Integer> {
    @Query("SELECT k.ma FROM KhachHang k ORDER BY k.id DESC LIMIT 1")
    String findLastCustomerCode();
    boolean existsByMa(String ma);
    KhachHang getById(Integer id);
}
