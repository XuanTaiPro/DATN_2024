package com.example.demo.repository;

import com.example.demo.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NhanVienRepository extends JpaRepository<NhanVien,String> {
    boolean existsByMa(String ma);
    boolean existsByMaAndIdNot(String ma, String id);
    List<NhanVien> findByTenContainingIgnoreCase(String ten);

    @Query("SELECT nv FROM NhanVien nv WHERE " +
            "(:gioiTinh IS NULL OR nv.gioiTinh = :gioiTinh) AND " +
            "(:diaChi IS NULL OR nv.diaChi LIKE %:diaChi%) AND " +
            "(:trangThai IS NULL OR nv.trangThai = :trangThai)")
    List<NhanVien> locNhanVien(@Param("gioiTinh") String gioiTinh,
                                  @Param("diaChi") String diaChi,
                                  @Param("trangThai") Integer trangThai);
}
