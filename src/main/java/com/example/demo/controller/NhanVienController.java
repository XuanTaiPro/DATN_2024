package com.example.demo.controller;

import com.example.demo.dto.nhanvien.NhanVienRequest;
import com.example.demo.dto.nhanvien.NhanVienResponse;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.Quyen;
import com.example.demo.repository.NhanVienRepository;
import com.example.demo.repository.QuyenRepository;
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

@RestController
@RequestMapping(name = "nhanvien")
public class NhanVienController {
    @Autowired
    private NhanVienRepository nvRepo;

    @Autowired
    private QuyenRepository qRepo;

    @Autowired
    private GenerateCodeAll generateCodeAll;

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
    public ResponseEntity<?> detail(@PathVariable String id) {
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
        if (nhanVienRequest.getMa() == null || nhanVienRequest.getMa().isEmpty()) {//nếu mã chưa đc điền thì tự động thêm mã
            nhanVienRequest.setMa(generateCodeAll.generateMaNhanVien());
        }
//        if (nvRepo.existsByMa(nhanVienRequest.getMa())) {
//            return ResponseEntity.badRequest().body("mã đã tồn tại");
//        }
        NhanVien nhanVien = nhanVienRequest.toEntity();
        nhanVien.setQuyen(qRepo.getById(nhanVienRequest.getIdQuyen()));
        nvRepo.save(nhanVien);
        return ResponseEntity.ok("thêm thành công");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable String id,@Valid @RequestBody NhanVienRequest nhanVienRequest,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder mess = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> mess.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(mess.toString());
        }
        if (nvRepo.existsByMaAndIdNot(nhanVienRequest.getMa(), id)) {
            return ResponseEntity.badRequest().body("Mã đã tồn tại tại nhân viên khác.");
        }
        if (nvRepo.findById(id).isPresent()) {
            NhanVien nhanVien = nvRepo.findById(id).get();
            Quyen quyenAdmin = qRepo.findByTen("Admin");
            if (!nhanVien.getQuyen().getId().equals(quyenAdmin.getId())) {
                return ResponseEntity.badRequest().body("Bạn không có quyền sửa thông tin này");
            }
            NhanVien nhanVienUpdate = nhanVienRequest.toEntity();
            nhanVienUpdate.setId(id);
            nhanVienUpdate.setQuyen(qRepo.getById(nhanVienRequest.getIdQuyen()));
            nvRepo.save(nhanVienUpdate);
            return ResponseEntity.ok("Update thành công ");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy id cần update");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (nvRepo.findById(id).isPresent()) {
            NhanVien nhanVien = nvRepo.findById(id).get();
            Quyen quyenAdmin = qRepo.findByTen("Admin");

            if (!nhanVien.getQuyen().getId().equals(quyenAdmin.getId())) {
                return ResponseEntity.badRequest().body("Bạn không có quyền xóa thông tin này");
            }
            nvRepo.deleteById(id);
            return ResponseEntity.ok("Xóa thành công");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy id cần xóa");
        }
    }
}
