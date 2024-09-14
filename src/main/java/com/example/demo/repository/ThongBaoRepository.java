package com.example.demo.repository;

import com.example.demo.entity.ThongBao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThongBaoRepository extends JpaRepository<ThongBao,Integer> {
    boolean existsByMa(String ma);
}
