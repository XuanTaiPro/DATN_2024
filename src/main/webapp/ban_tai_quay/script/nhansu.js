window.nhansuCtrl = function ($scope, $http) {
    const url = "http://localhost:8080/nhanvien";

    $scope.listNhanVien = [];

    $http.get(url).then(function (response) {
        $scope.listNhanVien = response.data;
        console.log("Lấy dữ liệu thành công");
    }).catch((error) => {
        console.error('Lỗi:', error);
    });

    // Mở modal
    $scope.openModal = function () {
        var modalElement = new bootstrap.Modal(document.getElementById('userForm'), {
            keyboard: false
        });
        modalElement.show();
    };
    $scope.openDetailModal = function (nhanVien) {
        $scope.selectedNhanVien = nhanVien;
        $scope.img = nhanVien.img;
        var modalElement = new bootstrap.Modal(document.getElementById('readData'), {
            keyboard: false
        });
        modalElement.show(); // Hiển thị modal
    };
    $scope.openUpdateModal = function (nhanVien) {
        $scope.selectedNhanVien = angular.copy(nhanVien); // Lưu thông tin nhân viên vào biến
        var modalElement = new bootstrap.Modal(document.getElementById('updateForm'), {
            keyboard: false
        });
        modalElement.show(); // Hiển thị modal
    };

    // Cập nhật ảnh khi người dùng chọn file mới
    $scope.updateImage = function (files) {
        var file = files[0];
        if (file && file.size < 10000000) {  // Kiểm tra kích thước file < 1MB
            var fileName = file.name;  // Lấy tên file
            var filePath = "img/" + fileName;  // Đường dẫn bạn lưu ảnh trong thư mục img

            // Cập nhật đường dẫn ảnh trong Angular
            $scope.$apply(function () {
                $scope.img = filePath;  // Chỉ lưu đường dẫn vào $scope.img
                $scope.selectedNhanVien.img = filePath;  // Đồng thời cập nhật đường dẫn vào selectedNhanVien
            });
            console.log("Đường dẫn ảnh: " + filePath);
        } else {
            alert("This file is too large!");
        }
    };



    $scope.addNhanVien = function () {
        console.log("Thêm nhân viên được gọi!");
        const newNhanVien = {
            ten: $scope.ten,
            email: $scope.email,
            passw: $scope.passw,
            gioiTinh: $scope.gioiTinh,
            diaChi: $scope.diaChi,
            trangThai: $scope.trangThai,
            ngayTao: $scope.ngayTao,
            ngaySua: $scope.ngaySua,
            idQuyen: $scope.idQuyen,
            img: $scope.img
        };

        console.log("Dữ liệu nhân viên mới:", newNhanVien);
        $http.post(url + '/add', newNhanVien)
            .then(function (response) {
            $scope.listNhanVien.push(response.data);
            alert('Thêm thành công!!')
            resetForm();
        }).catch((error) => {
            $scope.errorMessage = "Thêm thất bại";
        });
        // Reset form
        resetForm();
        var modalElement = new bootstrap.Modal(document.getElementById('userForm'));
        modalElement.hide();

    };
    $scope.updateNhanVien = function () {
        console.log("Cập nhật nhân viên:", $scope.selectedNhanVien);  // Kiểm tra dữ liệu trước khi gửi
        $http.put(url + '/update/' + $scope.selectedNhanVien.id, $scope.selectedNhanVien)
            .then(function (response) {
                console.log("Cập nhật thành công", response.data);
                // Cập nhật danh sách nhân viên sau khi update thành công
                const index = $scope.listNhanVien.findIndex(nv => nv.id === response.data.id);
                if (index !== -1) {
                    $scope.listNhanVien[index] = response.data;
                }
                alert('Cập nhật thành công!!');
                resetUpdateForm();
            })
            .catch(function (error) {
                console.error("Lỗi khi cập nhật nhân viên:", error);
                alert("Cập nhật thất bại. Vui lòng thử lại sau.");
            });
        var modalElement = new bootstrap.Modal(document.getElementById('updateForm'));
        modalElement.hide();
    };



// Xóa nhân viên
    $scope.deleteNhanVien = function (id) {
        console.log("Xóa");
        if (confirm('Bạn có chắc chắn muốn xóa nhân viên này?')) {
            $http.delete(url + '/delete/' + id)  // Sử dụng URL đúng cho yêu cầu xóa
                .then(function (response) {
                    // Tìm chỉ số của nhân viên đã xóa
                    const index = $scope.listNhanVien.findIndex(nv => nv.id === id);
                    if (index !== -1) {
                        $scope.listNhanVien.splice(index, 1);  // Xóa nhân viên khỏi danh sách
                    }
                    alert('Xóa thành công!!');
                })
                .catch(function (error) {
                    console.error("Lỗi khi xóa nhân viên:", error);
                    alert("Xóa thất bại. Vui lòng thử lại sau.");  // Hiển thị thông báo lỗi
                });
        }
    };


    // Reset form
    // Reset form
    function resetForm() {
        $scope.name = "";
        $scope.email = "";
        $scope.passw = "";
        $scope.gioiTinh = "";
        $scope.diaChi = "";
        $scope.trangThai = "";
        $scope.ngayTao = "";
        $scope.ngaySua = "";
        $scope.idQuyen = "";
        $scope.img = "./ban_tai_quay/img/NV1.jpg";  // Đường dẫn ảnh mặc định nếu không có ảnh
    }

};
