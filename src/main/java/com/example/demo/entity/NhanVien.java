package com.example.demo.entity;

import com.example.demo.dto.NhanVienResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "NHANVIEN")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MA")
    private String ma;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSW")
    private String passw;

    @Column(name = "GIOITINH")
    private String gioiTinh;

    @Column(name = "IMG")
    private String img;

    @Column(name = "DIACHI")
    private String diaChi;

    @Column(name = "TRANGTHAI")
    private Integer trangThai;

    @Column(name = "NGAYTAO")
    private String ngayTao;

    @Column(name = "NGAYSUA")
    private String ngaySua;

    @OneToOne
    @JoinColumn(name = "IDQUYEN")
    private Quyen quyen;

    public NhanVienResponse toResponse() {
        return new NhanVienResponse(id, ma, ten, email, passw, gioiTinh, img, diaChi, trangThai, ngayTao, ngaySua, quyen.getTen(), quyen.getTrangThai());
    }
}
