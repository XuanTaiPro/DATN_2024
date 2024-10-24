window.voucherCtrl = function ($scope, $http) {
    const url = "http://localhost:8080/voucher";

    $scope.listVoucher = [];

    $http.get(url).then(function (response) {
        $scope.listVoucher = response.data;
        console.log("Lấy dữ liệu thành công");
    }).catch((error) => {
        console.error('Lỗi:', error);
    });

    $scope.listLVC = [];
    $http.get("http://localhost:8080/loaivoucher")
        .then(function(response) {
            $scope.listLVC = response.data;
            console.log("Lấy danh sách LVC thành công", $scope.listLVC);
        })
        .catch(function(error) {
            console.error("Lỗi khi lấy danh sách LVC:", error);
        });


    $scope.viewDetail = function (voucher) {
        $scope.selectedVoucher = angular.copy(voucher);
    }
    $scope.openUpdateModal = function(voucher) {
        $scope.selectedVoucher = angular.copy(voucher);
        $scope.selectedLoaiVoucher = $scope.selectedVoucher.tenQuyen
        $scope.idLoaiVC = null
        console.log( $scope.selectedLoaiVoucher)
        fetch('http://localhost:8080/loaivoucher/getId?ten=' +$scope.selectedLoaiVoucher).then(function (response){
            $scope.idLoaiVC = response
        }).catch(function (er){
            console.error()
        })
    };


    $scope.addVoucher = function (e) {
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

        console.log("Dữ liệu mới:", newVoucher);
        $http.post('http://localhost:8080/voucher/add', newVoucher)
            .then(function (response) {
                $('#productModal').modal('hide');
                $scope.listVoucher.push(response.data);
                alert('Thêm thành công!!')
                e.preventDefault()
                resetForm();
            }).catch((error) => {
            $scope.errorMessage = "Thêm thất bại";
        });
        resetForm();
    };

    $scope.updateNhanVien = function () {
        if (!$scope.idLoaiVC) {
            alert("Vui lòng chọn quyền cho nhân viên.");
            return;
        }
        $scope.selectedVoucher.idLoaiVC = $scope.idLoaiVC;
        $http.put('http://localhost:8080/voucher/update/' + $scope.selectedVoucher.id, $scope.selectedVoucher)
            .then(function (response) {
                $('#UpdateForm').modal('hide');
                console.log("Cập nhật thành công", response.data);
                // Xóa nhân viên khỏi danh sách
                const index = $scope.listVoucher.findIndex(vc => vc.id === id);
                if (index !== -1) {
                    $scope.listVoucher.splice(index, 1);
                }
                alert('Sửa thành công!!');
                resetForm()
            })
            .catch(function () {
            });
    };



    $scope.deleteVoucher = function (id) {
        console.log("Xóa");
        if (confirm('Bạn có chắc chắn muốn xóa ?')) {
            $http.delete('http://localhost:8080/voucher/delete/' + id)
                .then(function (response) {
                    // Kiểm tra phản hồi server
                    console.log(response.data);
                    const index = $scope.listVoucher.findIndex(vc => vc.id === id);
                    if (index !== -1) {
                        $scope.listVoucher.splice(index, 1);
                    }
                    alert(response.data.message || 'Xóa thành công!!');  // Sử dụng thông điệp từ server
                })
                .catch(function (error) {
                    console.error("Lỗi khi xóa :", error);
                    alert("Xóa thất bại. Vui lòng thử lại sau.");
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

