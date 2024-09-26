package com.example.demo.entity;


import com.example.demo.dto.voucher.VoucherResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "VOUCHER")
public class Voucher {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MA")
    private String ma;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "GIAGIAM")
    private String giamGia;

    @Column(name = "NGAYTAO")
    private String ngayTao;

    @Column(name = "NGAYSUA")
    private String ngaySua;

    @Column(name = "GIAMMIN")
    private String giamMin;

    @Column(name = "GIAMMAX")
    private String giamMax;

    @Column(name = "DIEUKIEN")
    private String dieuKien;

    @Column(name = "NGAYKETTHUC")
    private String ngayKetThuc;

    @Column(name = "SOLUONG")
    private Integer soLuong;

    @Column(name = "TRANGTHAI")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "IDLOAIVC")
    private LoaiVoucher loaiVoucher;

    public VoucherResponse toResponse() {
        return new VoucherResponse(id, ma, ten, giamGia, ngayTao, ngaySua, giamMin, giamMax, dieuKien,ngayKetThuc,soLuong,trangThai, loaiVoucher.getMa(), loaiVoucher.getTen());
    }
}
