window.voucherCtrl = function ($scope, $http) {
    const url = "http://localhost:8080/voucher";

    $scope.listVoucher = [];

    $http.get(url).then(function (response) {
        $scope.listVoucher = response.data;
        console.log("Lấy dữ liệu thành công");
    }).catch((error) => {
        console.error('Lỗi:', error);
    });

    // Mở modal
    $scope.openModal = function () {
        var modalElement = new bootstrap.Modal(document.getElementById('vcForm'), {
            keyboard: false
        });
        modalElement.show();
    };
    $scope.openDetailModal = function (voucher) {
        $scope.selectedVoucher = angular.copy(voucher);
        var modalElement = new bootstrap.Modal(document.getElementById('readData'), {
            keyboard: false
        });
        modalElement.show(); // Hiển thị modal
    };
    $scope.openUpdateModal = function (voucher) {
        $scope.selectedVoucher = angular.copy(voucher);
        // Chuyển đổi chuỗi ngày sang đối tượng Date
        if ($scope.selectedVoucher.ngayKetThuc) {
            $scope.selectedVoucher.ngayKetThuc = new Date($scope.selectedVoucher.ngayKetThuc);
        }
        var modalElement = new bootstrap.Modal(document.getElementById('updateForm'), {
            keyboard: false
        });
        modalElement.show();
    };


    $scope.addVoucher = function () {
        console.log("Thêm Khách hàng được gọi!");
        const newVoucher = {
            ten: $scope.ten,
            giamGia: $scope.giamGia,
            giamMin: $scope.giamMin,
            giamMax: $scope.giamMax,
            dieuKien: $scope.dieuKien,
            ngayKetThuc: $scope.ngayKetThuc,
            soLuong: $scope.soLuong,
            idLoaiVC: $scope.idLoaiVC,
            trangThai: $scope.trangThai
        };
        console.log("Dữ liệu voucher mới:", newVoucher);

        $http.post(url + '/add', newVoucher)
            .then(function (response) {
                $scope.listVoucher.push(response.data);
                alert('Thêm thành công!!')
                resetForm();
            }).catch((error) => {
            $scope.errorMessage = "Thêm thất bại";
        });
        // Reset form
        resetForm();
        var modalElement = new bootstrap.Modal(document.getElementById('vcForm'));
        modalElement.hide();

    };
    $scope.updateVoucher = function () {
        console.log("Cập nhật voucher:", $scope.selectedVoucher);  // Kiểm tra dữ liệu trước khi gửi
        $http.put(url + '/update/' + $scope.selectedVoucher.id, $scope.selectedVoucher)
            .then(function (response) {
                console.log("Cập nhật thành công", response.data);
                // Cập nhật danh sách nhân viên sau khi update thành công
                const index = $scope.listVoucher.findIndex(vc => vc.id === response.data.id);
                if (index !== -1) {
                    $scope.listVoucher[index] = response.data;
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
    $scope.deleteVoucher = function (id) {
        console.log("Xóa");
        if (confirm('Bạn có chắc chắn muốn xóa voucher này?')) {
            $http.delete(url + '/delete/' + id)
                .then(function (response) {
                    // Tìm chỉ số của nhân viên đã xóa
                    const index = $scope.listVoucher.findIndex(vc => vc.id === id);
                    if (index !== -1) {
                        $scope.listVoucher.splice(index, 1);  // Xóa nhân viên khỏi danh sách
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
        $scope.giamGia = "";
        $scope.giamMin = "";
        $scope.giamMax = "";
        $scope.dieuKien = "";
        $scope.ngayKetThuc = "";
        $scope.soLuong = "";
        $scope.idLoaiVC = "";
        $scope.trangThai = "";
    }

};

