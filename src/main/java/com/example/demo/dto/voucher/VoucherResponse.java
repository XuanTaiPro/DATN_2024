package com.example.demo.dto.voucher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoucherResponse {
    private String id;

    private String ma;

    private String ten;

    private String giamGia;

    private String ngayTao;

    private String hsd;

    private Integer soLuong;

    private Integer trangThai;

    private String loaiVoucher;

    private String tenKH;

    private String emailKH;

}
