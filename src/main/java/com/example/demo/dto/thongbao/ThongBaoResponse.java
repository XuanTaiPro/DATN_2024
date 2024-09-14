package com.example.demo.dto.thongbao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThongBaoResponse {

    private Integer id;

    private String ma;

    private String noiDung;

    private String ngayGui;

    private String ngayDoc;

    private Integer trangThai;

    private String tenKH;

    private String emailKH;
}
