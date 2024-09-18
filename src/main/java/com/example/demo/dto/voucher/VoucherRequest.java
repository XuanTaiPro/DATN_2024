package com.example.demo.dto.voucher;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Voucher;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoucherRequest {

    private String id;

//    @NotBlank(message = "Mã Không được để trống")
//    @Pattern(regexp = "^VC\\d{3}$", message = "Mã phải có định dạng TBxxx (VD: TB001, TB002,...)")
    private String ma;

    @NotBlank(message = "Tên Không được để trống")
    private String ten;

    @NotBlank(message = "Giảm giáKhông được để trống")
    private String giamGia;

    @NotBlank(message = "Ngày tạo Không được để trống")
    private String ngayTao;

    @NotBlank(message = "hsd Không được để trống")
    private String hsd;

    @NotNull(message = "Số lượng Không được để trống")
    @Min(value = 1,message = "số lượng phải là số lớn hơn 0")
    private Integer soLuong;

    @NotNull(message = "Trạng thái Không được để trống")
    private Integer trangThai;

    @NotBlank(message = "Loại voucher Không được để trống")
    private String loaiVoucher;

    @NotNull(message = "idKH Không được để trống")
    private String idKH;

    public Voucher toEntity() {
        return new Voucher(id, ma, ten, giamGia, ngayTao, hsd, soLuong, trangThai, loaiVoucher, null);
    }
}
