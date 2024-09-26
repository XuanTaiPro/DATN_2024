package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CHITIETSANPHAM")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietSanPham {

    @Id
    @Column(name = "ID")
    private String id;

    @NotBlank(message = "Mã không được để trống")
    @Size(max = 255, message = "Mã không được vượt quá 255 ký tự")
//    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Mã chỉ được chứa chữ cái và số!")
    @Column(name = "MA")
    private String ma;

    @NotBlank(message = "Giá không được để trống")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$", message = "Giá phải là số dương, có thể có tối đa hai chữ số thập phân")
    @Column(name = "GIA")
    private String gia;

    @NotBlank(message = "Số ngày sử dụng không được để trống")
    @Size(max = 255, message = "Số ngày sử dụng không được vượt quá 255 ký tự")
    @Column(name = "SONGAYSUDUNG")
    private String soNgaySuDung;

//    @NotBlank(message = "Thành phần không được để trống")
//    @Size(max = 255, message = "Thành phần không được vượt quá 255 ký tự")
//    @Column(name = "THANHPHAN")
//    private String thanhPhan;
//
//    @NotBlank(message = "Công dụng không được để trống")
//    @Size(max = 255, message = "Công dụng không được vượt quá 255 ký tự")
//    @Column(name = "CONGDUNG")
//    private String congDung;

    @NotBlank(message = "Hướng dẫn sử dụng không được để trống")
    @Size(max = 255, message = "Hướng dẫn sử dụng không được vượt quá 255 ký tự")
    @Column(name = "HDSD")
    private String HDSD;

//    @Min(value = 0, message = "Tuổi tối thiểu không hợp lệ")
//    @Column(name = "TUOIMIN")
//    private int tuoiMin;
//
//    @Min(value = 0, message = "Tuổi tối đa không hợp lệ")
//    @Column(name = "TUOIMAX")
//    private int tuoiMax;

    @NotNull(message = "Hạn sử dụng không được để trống")
    @FutureOrPresent(message = "Hạn sử dụng phải là ngày hiện tại hoặc trong tương lai")
    @Column(name = "HSD")
    private LocalDate HSD;

    @NotNull(message = "Ngày nhập không được để trống")
    @PastOrPresent(message = "Ngày nhập phải là ngày hiện tại hoặc trong quá khứ")
    @Column(name = "NGAYNHAP")
    private LocalDateTime ngayNhap;

    @NotNull(message = "Ngày nhập không được để trống")
    @PastOrPresent(message = "Ngày nhập phải là ngày hiện tại hoặc trong quá khứ")
    @Column(name = "NGAYSANXUAT")
    private LocalDateTime ngaySanXuat;

    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    @Column(name = "SOLUONG")
    private int soLuong;

    @NotNull(message = "Trạng thái không được để trống")
    @Min(value = 0, message = "Trạng thái không hợp lệ")
    @Max(value = 4, message = "Trạng thái không hợp lệ")
    @Column(name = "TRANGTHAI")
    private int trangThai;

    @Column(name = "NGAYTAO")
    private LocalDateTime ngayTao;

    @Column(name = "NGAYSUA")
    private LocalDateTime ngaySua;

    @Column(name = "IDSP")
    private String idSP;

}
