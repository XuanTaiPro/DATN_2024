package com.example.demo.controller;

import com.example.demo.dto.khachhang.KhachHangRequest;
import com.example.demo.dto.khachhang.KhachHangResponse;
import com.example.demo.dto.voucher.VoucherResponse;
import com.example.demo.entity.KhachHang;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.service.GenerateCodeAll;
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
import java.util.UUID;

@RestController
@RequestMapping("khachhang")
public class KhachHangController {

    @Autowired
    private KhachHangRepository khRepo;

    @Autowired
    private GenerateCodeAll generateCodeAll;

    @GetMapping
    public List<KhachHang> findAll() {
        return khRepo.findAll();
    }

    @GetMapping("findAllNotPW")
    public ResponseEntity<?> findAllNotPW() {
        List<KhachHangResponse> list = new ArrayList<>();
        khRepo.findAll().forEach(c -> list.add(c.toResponse()));
        return ResponseEntity.ok(list);
    }

    @GetMapping("page")
    public ResponseEntity<?> page(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<KhachHang> list = new ArrayList<>();
        khRepo.findAll(pageable).forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @GetMapping("pageNotPW")
    public ResponseEntity<?> pageNotPW(@RequestParam(defaultValue = "0") Integer page) {
        Pageable p = PageRequest.of(page, 10);
        List<KhachHangResponse> list = new ArrayList<>();
        khRepo.findAll(p).forEach(c -> list.add(c.toResponse()));
        return ResponseEntity.ok(list);
    }


//    public String createMaAuto() {//tạo mã tự động bằng query bên repository
//        String lastCode = khRepo.findLastCustomerCode();
//        if (lastCode != null && lastCode.matches("^KH\\d{3}$")) {
//            int lastNumber = Integer.parseInt(lastCode.substring(2));
//            return String.format("KH%03d", lastNumber + 1);
//        }
//        return "KH001";
//    }
//    public String checkTrungMaAuto() {//check mã vừa tạo có trùng với mã trong list k
//        String newCode;
//        do {
//            newCode = createMaAuto();
//        } while (khRepo.existsByMa(newCode));
//        return newCode;
//    }

    @GetMapping("detail/{id}")
    public ResponseEntity<KhachHang> getKhachHangById(@PathVariable String id) {
        Optional<KhachHang> khachHang = khRepo.findById(id);
        if (khachHang.isPresent()) {
            return ResponseEntity.ok(khachHang.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody KhachHangRequest khachHangRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder mess = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> mess.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(mess.toString());
        }
        if (khachHangRequest.getMa() == null || khachHangRequest.getMa().isEmpty()) {
            khachHangRequest.setMa(generateCodeAll.generateMaKhachHang());
        }
        if (khachHangRequest.getId() == null || khachHangRequest.getId().isEmpty()) {
            khachHangRequest.setId(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        KhachHang khachHang = khachHangRequest.toEntity();
        khRepo.save(khachHang);
        return ResponseEntity.ok("thêm thành công");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody KhachHangRequest khachHangRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder mess = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> mess.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(mess.toString());
        }
        if (khRepo.findById(id).isPresent()) {
            KhachHang khachHang = khachHangRequest.toEntity();
            khachHang.setId(id);
            khRepo.save(khachHang);
            return ResponseEntity.ok("Update thành công ");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy id cần update");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteKhachHang(@PathVariable String id) {
        Optional<KhachHang> khachHang = khRepo.findById(id);
        if (khachHang.isPresent()) {
            khRepo.delete(khachHang.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("search")
    public ResponseEntity<?> searchVoucher(@RequestParam String ten) {
        List<KhachHangResponse> list = new ArrayList<>();
        khRepo.findByTenContainingIgnoreCase(ten).forEach(voucher -> list.add(voucher.toResponse()));

        if (list.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy voucher với tên: " + ten);
        }

        return ResponseEntity.ok(list);
    }
}
