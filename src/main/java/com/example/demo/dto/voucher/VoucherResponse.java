package com.example.demo.dto.voucher;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    private String ngaySua;

    private String giamMin;

    private String giamMax;

    private String dieuKien;

    private String ngayKetThuc;

    private Integer soLuong;

    private Integer trangThai;

    private String maLoaiVC;

    private String tenLoaiVC;

}
