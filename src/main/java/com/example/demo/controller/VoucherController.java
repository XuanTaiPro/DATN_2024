package com.example.demo.controller;

import com.example.demo.dto.thongbao.ThongBaoRequest;
import com.example.demo.dto.thongbao.ThongBaoResponse;
import com.example.demo.dto.voucher.VoucherRequest;
import com.example.demo.dto.voucher.VoucherResponse;
import com.example.demo.entity.ThongBao;
import com.example.demo.entity.Voucher;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.LoaiVoucherRepository;
import com.example.demo.repository.VoucherRepository;
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
import java.util.UUID;

@RestController
@RequestMapping("voucher")
public class VoucherController {
    @Autowired
    private VoucherRepository vcRepo;

    @Autowired
    private LoaiVoucherRepository lvcRepo;

    @Autowired
    private GenerateCodeAll generateCodeAll;

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<VoucherResponse> list = new ArrayList<>();
        vcRepo.findAll().forEach(c -> list.add(c.toResponse()));
        return ResponseEntity.ok(list);
    }

    @GetMapping("page")
    public ResponseEntity<?> page(@RequestParam(defaultValue = "0") Integer page) {
        Pageable p = PageRequest.of(page, 10);
        List<VoucherResponse> list = new ArrayList<>();
        vcRepo.findAll(p).forEach(c -> list.add(c.toResponse()));
        return ResponseEntity.ok(list);
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<?> detail(@PathVariable String id) {
        return ResponseEntity.ok().body(vcRepo.findById(id).stream().map(Voucher::toResponse));
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody VoucherRequest voucherRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder mess = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> mess.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(mess.toString());
        }
        if (voucherRequest.getId() == null || voucherRequest.getId().isEmpty()) {
            voucherRequest.setId(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        if (voucherRequest.getMa() == null || voucherRequest.getMa().isEmpty()) {//nếu mã chưa đc điền thì tự động thêm mã
            voucherRequest.setMa(generateCodeAll.generateMaVoucher());
        }
//        if (vcRepo.existsByMa(voucherRequest.getMa())) {
//            return ResponseEntity.badRequest().body("mã đã tồn tại");
//        }
        Voucher voucher = voucherRequest.toEntity();
        voucher.setLoaiVoucher(lvcRepo.getById(voucherRequest.getIdLoaiVC()));
        vcRepo.save(voucher);
        return ResponseEntity.ok("thêm thành công");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody VoucherRequest voucherRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder mess = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> mess.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(mess.toString());
        }
//        if (vcRepo.existsByMa(voucherRequest.getMa())) {
//            return ResponseEntity.badRequest().body("mã đã tồn tại");
//        }
        if (vcRepo.findById(id).isPresent()) {
            Voucher voucher = voucherRequest.toEntity();
            voucher.setId(id);
            voucher.setLoaiVoucher(lvcRepo.getById(voucherRequest.getIdLoaiVC()));
            vcRepo.save(voucher);
            return ResponseEntity.ok("Update thành công ");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy id cần update");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (vcRepo.findById(id).isPresent()) {
            vcRepo.deleteById(id);
            return ResponseEntity.ok("Xóa thành công");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy id cần xóa");
        }
    }

    @GetMapping("search")
    public ResponseEntity<?> searchVoucher(@RequestParam String ten) {
        List<VoucherResponse> list = new ArrayList<>();
        vcRepo.findByTenContainingIgnoreCase(ten).forEach(voucher -> list.add(voucher.toResponse()));

        if (list.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy voucher với tên: " + ten);
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("filter")
    public ResponseEntity<?> filterVoucher(
            @RequestParam(required = false) String giamMin,
            @RequestParam(required = false) String giamMax,
            @RequestParam(required = false) String ngayKetThuc) {

        List<VoucherResponse> list = new ArrayList<>();
        vcRepo.filterVouchers(giamMin, giamMax, ngayKetThuc).forEach(voucher -> list.add(voucher.toResponse()));

        if (list.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy voucher nào phù hợp.");
        }

        return ResponseEntity.ok(list);
    }
}
