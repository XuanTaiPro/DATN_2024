package com.example.demo.repository;

import com.example.demo.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NhanVienRepository extends JpaRepository<NhanVien,String> {
    boolean existsByMa(String ma);
    boolean existsByMaAndIdNot(String ma, String id);
}
