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
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "MA")
    private String ma;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "GIAMGIA")
    private String giamGia;

    @Column(name = "NGAYTAO")
    private String ngayTao;

    @Column(name = "HSD")
    private String hsd;

    @Column(name = "SOLUONG")
    private Integer soLuong;

    @Column(name = "TRANGTHAI")
    private Integer trangThai;

    @Column(name = "LOAIVOUCHER")
    private String loaiVoucher;

    @ManyToOne
    @JoinColumn(name = "IDKH")
    private KhachHang khachHang;

    public VoucherResponse toResponse() {
        return new VoucherResponse(id, ma, ten, giamGia, ngayTao, hsd, soLuong, trangThai, loaiVoucher, khachHang.getTen(), khachHang.getEmail());
    }
}
