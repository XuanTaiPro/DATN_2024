package com.example.demo.controller;

import com.example.demo.entity.KhachHang;
import com.example.demo.repository.KhachHangRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/khachhang")
public class KhachHangController {

    @Autowired
    private KhachHangRepository khRepo;

    @GetMapping
    public List<KhachHang> findAll() {
        return khRepo.findAll();
    }

    @GetMapping("/page")
    public ResponseEntity<?> page(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<KhachHang> list = new ArrayList<>();
        khRepo.findAll(pageable).forEach(list::add);
        return ResponseEntity.ok(list);
    }


    public String createMaAuto() {//tạo mã tự động bằng query bên repository
        String lastCode = khRepo.findLastCustomerCode();
        if (lastCode != null && lastCode.matches("^KH\\d{3}$")) {
            int lastNumber = Integer.parseInt(lastCode.substring(2));
            return String.format("KH%03d", lastNumber + 1);
        }
        return "KH001";
    }
    public String checkTrungMaAuto() {//check mã vừa tạo có trùng với mã trong list k
        String newCode;
        do {
            newCode = createMaAuto();
        } while (khRepo.existsByMa(newCode));
        return newCode;
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhachHang> getKhachHangById(@PathVariable Integer id) {
        Optional<KhachHang> khachHang = khRepo.findById(id);
        if (khachHang.isPresent()) {
            return ResponseEntity.ok(khachHang.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody KhachHang khachHang, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder mess = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> mess.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(mess.toString());
        }
        if (khachHang.getMa() == null || khachHang.getMa().isEmpty()) {
            khachHang.setMa(checkTrungMaAuto());
        }
        return ResponseEntity.ok(khRepo.save(khachHang));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody KhachHang khachHangDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder mess = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> mess.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(mess.toString());
        }

        Optional<KhachHang> optionalKhachHang = khRepo.findById(id);
        if (optionalKhachHang.isPresent()) {
            KhachHang khachHang = optionalKhachHang.get();
            khachHang.setMa(khachHangDetails.getMa());
            khachHang.setTen(khachHangDetails.getTen());
            khachHang.setEmail(khachHangDetails.getEmail());
            khachHang.setPassw(khachHangDetails.getPassw());
            khachHang.setGioiTinh(khachHangDetails.getGioiTinh());
            khachHang.setSdt(khachHangDetails.getSdt());
            khachHang.setDiaChi(khachHangDetails.getDiaChi());
            khachHang.setTrangThai(khachHangDetails.getTrangThai());
            khachHang.setNgayTao(khachHangDetails.getNgayTao());
            khachHang.setNgaySua(khachHangDetails.getNgaySua());
            return ResponseEntity.ok(khRepo.save(khachHang));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhachHang(@PathVariable Integer id) {
        Optional<KhachHang> khachHang = khRepo.findById(id);
        if (khachHang.isPresent()) {
            khRepo.delete(khachHang.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
