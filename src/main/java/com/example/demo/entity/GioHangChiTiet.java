package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "GIOHANGCHITIET")
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MA")
    private String ma;

    @Column(name = "SOLUONG")
    private Integer soLuong;

    @Column(name = "TRANGTHAI")
    private Integer trangThai;

    @Column(name = "NGAYTAO")
    private String ngayTao;

    @ManyToOne
    @JoinColumn(name = "IDKH")
    private KhachHang khachHang;

}
