package com.example.demo.dto;

import com.example.demo.entity.NhanVien;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NhanVienRequest {
    private Integer id;

    @NotBlank(message = "Mã Không được để trống")
    @Pattern(regexp = "^NV\\d{3}$", message = "Mã phải có định dạng TBxxx (VD: TB001, TB002,...)")
    private String ma;

    @NotBlank(message = "Tên Không được để trống")
    private String ten;

    @Column(name = "EMAIL", unique = true)
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @Column(name = "PASSW")
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String passw;

    @NotBlank(message = "Giới tính Không được để trống")
    private String gioiTinh;

    @NotBlank(message = "IMG Không được để trống")
    private String img;

    @NotBlank(message = "Địa chỉ Không được để trống")
    private String diaChi;

    @NotNull(message = "Trạng thái Không được để trống")
    private Integer trangThai;

    @NotBlank(message = "Ngày tạo Không được để trống")
    private String ngayTao;

    @NotBlank(message = "Ngày sửa Không được để trống")
    private String ngaySua;

    @NotNull(message = "id quyền Không được để trống")
    private Integer idQuyen;


    public NhanVien toEntity() {
        return new NhanVien(id, ma, ten, email, passw, gioiTinh, img, diaChi, trangThai,ngayTao,ngaySua, null);
    }
}
