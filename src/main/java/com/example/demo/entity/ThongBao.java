package com.example.demo.entity;

import com.example.demo.dto.thongbao.ThongBaoResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "THONGBAO")
public class ThongBao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MA")
    private String ma;

    @Column(name = "NOIDUNG")
    private String noiDung;

    @Column(name = "NGAYGUI")
    private String ngayGui;

    @Column(name = "NGAYDOC")
    private String ngayDoc;

    @Column(name = "TRANGTHAI")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "IDKH")
    private KhachHang khachHang;

    public ThongBaoResponse toResponse() {
        return new ThongBaoResponse(id, ma, noiDung, ngayGui, ngayDoc, trangThai, khachHang.getTen(), khachHang.getEmail());
    }
}
