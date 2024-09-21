package com.example.demo.dto.khachhang;

import com.example.demo.entity.KhachHang;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KhachHangRequest {
    private String id;

    @Column(name = "MA", unique = true)
    @NotBlank(message = "Mã khách hàng không được để trống")
    @Size(max = 10, message = "Mã khách hàng không được dài quá 10 ký tự")
    @Pattern(regexp = "^KH\\d{3}$", message = "Mã phải có định dạng TBxxx (VD: KH001, KH002,...)")
    private String ma;

    @Column(name = "Ten")
    @NotBlank(message = "Tên không được để trống")
    private String ten;

    @Column(name = "EMAIL", unique = true)
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @Column(name = "PASSW")
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String passw;

    @Column(name = "GIOITINH")
    @NotBlank(message = "Giới tính không được để trống")
    private String gioiTinh;

    @Column(name = "SDT")
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải bao gồm 10 chữ số")
    private String sdt;

    @Column(name = "DIACHI")
    @NotBlank(message = "Địa chỉ không được để trống")
    private String diaChi;

    @Column(name = "TRANGTHAI")
    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangThai;

    @Column(name = "NGAYTAO")
    @NotBlank(message = "Ngày tạo không được để trống")
    private String ngayTao;

    @Column(name = "NGAYSUA")
    @NotBlank(message = "Ngày sửa không được để trống")
    private String ngaySua;

    public KhachHang toEntity() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        return new KhachHang(id, ma, ten, email, passw, gioiTinh, sdt, diaChi, trangThai, ngayTao, ngaySua);
    }
}
