window.khachhangCtrl = function ($scope, $http) {
    const url = "http://localhost:8080/khachhang";

    $scope.listKhachHang = [];

    $http.get(url).then(function (response) {
        $scope.listKhachHang = response.data;
        console.log("Lấy dữ liệu thành công");
    }).catch((error) => {
        console.error('Lỗi:', error);
    });

    // Mở modal
    $scope.openModal = function () {
        var modalElement = new bootstrap.Modal(document.getElementById('khForm'), {
            keyboard: false
        });
        modalElement.show();
    };
    $scope.openDetailModal = function (khachHang) {
        $scope.selectedKhachHang = angular.copy(khachHang);
        var modalElement = new bootstrap.Modal(document.getElementById('readData'), {
            keyboard: false
        });
        modalElement.show(); // Hiển thị modal
    };
    $scope.openUpdateModal = function (khachHang) {
        $scope.selectedKhachHang = angular.copy(khachHang);
        var modalElement = new bootstrap.Modal(document.getElementById('updateForm'), {
            keyboard: false
        });
        modalElement.show();
    };


    $scope.addKhachHang = function () {
        console.log("Thêm Khách hàng được gọi!");
        const newKhachHang = {
            ten: $scope.ten,
            email: $scope.email,
            passw: $scope.passw,
            gioiTinh: $scope.gioiTinh,
            sdt: $scope.sdt,
            diaChi: $scope.diaChi,
            trangThai: $scope.trangThai

        };
        console.log("Dữ liệu khách hàng mới:", newKhachHang);
        $http.post(url + '/add', newKhachHang)
            .then(function (response) {
                $scope.listKhachHang.push(response.data);
                alert('Thêm thành công!!')
                resetForm();
            }).catch((error) => {
            $scope.errorMessage = "Thêm thất bại";
        });
        // Reset form
        resetForm();
        var modalElement = new bootstrap.Modal(document.getElementById('khForm'));
        modalElement.hide();

    };
    $scope.updateKhachHang = function () {
        console.log("Cập nhật khách hàng:", $scope.selectedKhachHang);  // Kiểm tra dữ liệu trước khi gửi
        $http.put(url + '/update/' + $scope.selectedKhachHang.id, $scope.selectedKhachHang)
            .then(function (response) {
                console.log("Cập nhật thành công", response.data);
                // Cập nhật danh sách nhân viên sau khi update thành công
                const index = $scope.listKhachHang.findIndex(kh => kh.id === response.data.id);
                if (index !== -1) {
                    $scope.listKhachHang[index] = response.data;
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
    $scope.deleteKhachHang = function (id) {
        console.log("Xóa");
        if (confirm('Bạn có chắc chắn muốn xóa khách hàng này?')) {
            $http.delete(url + '/delete/' + id)
                .then(function (response) {
                    // Tìm chỉ số của nhân viên đã xóa
                    const index = $scope.listKhachHang.findIndex(kh => kh.id === id);
                    if (index !== -1) {
                        $scope.listKhachHang.splice(index, 1);  // Xóa nhân viên khỏi danh sách
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
        $scope.ten = "";
        $scope.email = "";
        $scope.passw = "";
        $scope.gioiTinh = "";
        $scope.sdt = "";
        $scope.diaChi = "";
        $scope.trangThai = "";
    }

};

