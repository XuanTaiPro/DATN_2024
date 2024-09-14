package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NhanVienResponse {
    private Integer id;
    private String ma;
    private String ten;
    private String email;
    private String passw;
    private String gioiTinh;
    private String img;
    private String diaChi;
    private Integer trangThai;
    private String ngayTao;
    private String ngaySua;
    private String tenQuyen;
    private Integer trangThaiQuyen;
}
