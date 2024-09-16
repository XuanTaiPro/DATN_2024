package com.example.demo.controller;

import com.example.demo.dto.NhanVienRequest;
import com.example.demo.dto.NhanVienResponse;
import com.example.demo.entity.NhanVien;
import com.example.demo.repository.NhanVienRepository;
import com.example.demo.repository.QuyenRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(name = "nhanvien")
public class NhanVienController {
    @Autowired
    private NhanVienRepository nvRepo;

    @Autowired
    private QuyenRepository qRepo;

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<NhanVienResponse> list = new ArrayList<>();
        nvRepo.findAll().forEach(c -> list.add(c.toResponse()));
        return ResponseEntity.ok(list);
    }

    @GetMapping("page")
    public ResponseEntity<?> page(@RequestParam(defaultValue = "0") Integer page) {
        Pageable p = PageRequest.of(page, 10);
        List<NhanVienResponse> list = new ArrayList<>();
        nvRepo.findAll(p).forEach(c -> list.add(c.toResponse()));
        return ResponseEntity.ok(list);
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<?> detail(@PathVariable Integer id) {
        if (nvRepo.findById(id).isPresent()) {
            return ResponseEntity.ok().body(nvRepo.findById(id).stream().map(NhanVien::toResponse));
        }else {
            return ResponseEntity.badRequest().body("Không tìm thấy id để hiển thị");
        }
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody NhanVienRequest nhanVienRequest,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder mess = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> mess.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(mess.toString());
        }
        if (nvRepo.existsByMa(nhanVienRequest.getMa())) {
            return ResponseEntity.badRequest().body("mã đã tồn tại");
        }
        NhanVien nhanVien = nhanVienRequest.toEntity();
        nhanVien.setQuyen(qRepo.getById(nhanVienRequest.getIdQuyen()));
        nvRepo.save(nhanVien);
        return ResponseEntity.ok("thêm thành công");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@Valid @RequestBody NhanVienRequest nhanVienRequest,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder mess = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> mess.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(mess.toString());
        }
        if (nvRepo.existsByMa(nhanVienRequest.getMa())) {
            return ResponseEntity.badRequest().body("mã đã tồn tại");
        }
        if (nvRepo.findById(id).isPresent()) {
            if (nvRepo.findById(id).get().getQuyen().getId() != 1) { //quyền admin id = 1
                return ResponseEntity.status(403).body("Bạn không có quyền sửa thông tin này");
            }
            NhanVien nhanVien = nhanVienRequest.toEntity();
            nhanVien.setId(id);
            nhanVien.setQuyen(qRepo.getById(nhanVienRequest.getIdQuyen()));
            nvRepo.save(nhanVien);
            return ResponseEntity.ok("Update thành công ");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy id cần update");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (nvRepo.findById(id).isPresent()) {
            if (nvRepo.findById(id).get().getQuyen().getId() != 1) { //quyền admin id = 1
                return ResponseEntity.status(403).body("Bạn không có quyền sửa thông tin này");
            }
            nvRepo.deleteById(id);
            return ResponseEntity.ok("Xóa thành công");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy id cần xóa");
        }
    }
}
