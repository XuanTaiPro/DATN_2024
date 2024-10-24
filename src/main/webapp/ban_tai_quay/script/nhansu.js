window.nhansuCtrl= function($scope, $http) {


    $scope.selectedNhanVien = {}; // Khởi tạo đối tượng nếu chưa có


    $scope.listNhanVien = [];
    $http.get('http://localhost:8080/nhanvien').then(function (response) {
        $scope.listNhanVien = response.data;
        console.log("Lấy dữ liệu thành công");
    }).catch((error) => {
        console.error('Lỗi:', error);
    });
    $scope.listQuyen = [];
    $http.get( "http://localhost:8080/quyen")
        .then(function(response) {
            $scope.listQuyen = response.data;
            console.log("Lấy danh sách quyền thành công", $scope.listQuyen);
        })
        .catch(function(error) {
            console.error("Lỗi khi lấy danh sách quyền:", error);
        });


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


    $scope.viewDetail = function (nhanVien) {
        $scope.selectedNhanVien = angular.copy(nhanVien);
        $scope.img = nhanVien.img;
        $scope.selectedNhanVien.trangThai = nhanVien.trangThai == 1 ? 'Hoạt động' : 'Ngưng hoạt động';
    }
    $scope.openUpdateModal = function(nhanVien) {
        $scope.selectedNhanVien = angular.copy(nhanVien);
        $scope.selectedQuyen = $scope.selectedNhanVien.tenQuyen
        $scope.idQuyen = null
        console.log( $scope.selectedQuyen)
        fetch('http://localhost:8080/quyen/getId?ten=' +$scope.selectedQuyen).then(function (response){
            return  response.text()
        }).then(function(data) {
            $scope.$apply(function() {
                $scope.idQuyen = data;  // Gán giá trị trả về vào $scope.idQuyen
                console.log($scope.idQuyen);
            });
        }).catch(function (er){
            console.error(er)
        })
        $scope.selectedNhanVien.trangThai = nhanVien.trangThai.toString();
        console.log("Trạng thái hiện tại:", $scope.selectedNhanVien.trangThai);
    };

    $scope.addNhanVien = function () {
        if (!$scope.gioiTinh) {
            $scope.gioiTinh = "Nam";
        }
        console.log("Thêm nhân viên được gọi!");
        const newNhanVien = {
            ten: $scope.ten,
            email: $scope.email,
            passw: $scope.passw,
            gioiTinh: $scope.gioiTinh,
            diaChi: $scope.diaChi,
            trangThai: $scope.trangThai,
            idQuyen: $scope.idQuyen,
            img: $scope.img
        };
        console.log("Dữ liệu nhân viên mới:", newNhanVien);

        $http.post('http://localhost:8080/nhanvien/add', newNhanVien)
            .then(function (response) {
                $scope.listNhanVien.push(response.data);

                // Đóng modal
                $('#productModal').modal('hide');
                setTimeout(function() {
                    location.reload();
                }, 500);
            })
            .catch(function (error) {
                $scope.errorMessage = "Thêm thất bại";
            });

        resetForm();
    };



    $scope.updateNhanVien = function () {
        if (!$scope.idQuyen) {
            alert("Vui lòng chọn quyền cho nhân viên.");
            return;
        }
        // $event.preventDefault()
        $scope.selectedNhanVien.idQuyen = $scope.idQuyen;
        $http.put('http://localhost:8080/nhanvien/update/' + $scope.selectedNhanVien.id, $scope.selectedNhanVien)
            .then(function (response) {
               location.reload()
            })
            .catch(function () {
            });
    };




// Xóa nhân viên
    $scope.deleteNhanVien = function (id) {
        console.log("Xóa");
        if (confirm('Bạn có chắc chắn muốn xóa nhân viên này?')) {
            $http.delete('http://localhost:8080/nhanvien/delete/' + id)
                .then(function (response) {
                    // Kiểm tra phản hồi server
                    console.log(response.data);
                    const index = $scope.listNhanVien.findIndex(nv => nv.id === id);
                    if (index !== -1) {
                        $scope.listNhanVien.splice(index, 1);
                    }
                    alert(response.data.message || 'Xóa thành công!!');  // Sử dụng thông điệp từ server
                })
                .catch(function (error) {
                    console.error("Lỗi khi xóa nhân viên:", error);
                    alert("Xóa thất bại. Vui lòng thử lại sau.");
                });
        }
    };

    function resetForm() {
        $scope.name = "";
        $scope.email = "";
        $scope.passw = "";
        $scope.gioiTinh = "";
        $scope.diaChi = "";
        $scope.trangThai = "";
        $scope.ngayTao = "";
        $scope.img = "";
    }
};