CREATE DATABASE DB_DA1
GO
USE DB_DA1
GO
CREATE TABLE NhanVien
(
    Id INT IDENTITY(1,1) PRIMARY KEY,
    MaNV VARCHAR(100),
    HoTen NVARCHAR(100),
    DiaChi NVARCHAR(150),
    Sdt VARCHAR(10),
    Email VARCHAR(100),
    GioiTinh INT,
    TenTaiKhoan VARCHAR(255),
    MatKhau VARCHAR(100),
    VaiTro INT, --1: quản lí, 0: nhân viên
    TrangThai INT
);

CREATE TABLE KhachHang
(
    Id INT IDENTITY(1,1) PRIMARY KEY,
    MaKH VARCHAR(100) NOT NULL,
    HoTen NVARCHAR(100) NOT NULL,
    Sdt VARCHAR(15) NOT NULL,
    DiaChi NVARCHAR(150),
    TrangThai INT NOT NULL
);

CREATE TABLE ThuongHieu
(
    Id INT IDENTITY(1,1) PRIMARY KEY,
    MaTH VARCHAR(100),
    TenTH NVARCHAR(100),
    TrangThai INT
);

CREATE TABLE MauSac
(
    Id INT IDENTITY(1,1) PRIMARY KEY,
    MaMau VARCHAR(100),
    TenMau NVARCHAR(100),
    TrangThai INT
);

CREATE TABLE Size
(
    Id INT IDENTITY(1,1) PRIMARY KEY,
    MaSize VARCHAR(100),
    TenSize NVARCHAR(100),
    TrangThai INT
);
CREATE TABLE KieuDang
(
    Id INT IDENTITY(1,1) PRIMARY KEY,
    MaKD VARCHAR(100),
    TenKieuDang NVARCHAR(100),
    MoTa NVARCHAR(250),
    TrangThai INT
);

CREATE TABLE SanPham
(
    Id INT IDENTITY(1,1) PRIMARY KEY,
    MaSP VARCHAR(100),
    TenSP NVARCHAR(100),
    XuatXu NVARCHAR(100),
    TrangThai INT
);

CREATE TABLE SanPhamChiTiet
(
    Id INT IDENTITY(1,1) PRIMARY KEY,
    MaSPCT VARCHAR(100),
    Id_SP INT,
    Id_Mau INT,
    Id_Size INT,
    Id_KD INT,
    Id_TH INT,
    SoLuong INT,
    DonGia money,
    TrangThai INT
);

CREATE TABLE KhuyenMai
(
    Id INT IDENTITY(1,1) PRIMARY KEY,
    MaKM VARCHAR(100),
    TenChienDich NVARCHAR(100),
    SoHoaDonDuocApDung INT,
    [MucGiamGia(%)] INT,
    NgayBatDau DATE,
    NgayKetThuc DATE,
    NgayTao DATE,
    HoaDonToiThieu money,
    SoLuong INT,
    TrangThai INT
);

CREATE TABLE HoaDon
(
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Id_NV INT,
    Id_KH INT,
    Id_KM INT,
    MaHD VARCHAR(100),
    NgayTao DATE,
    TongTien money,
    SoTienGiamGia money,
    HinhThucThanhToan NVARCHAR(150),
    GhiChu NVARCHAR(255),
    ThanhTien money,
    TrangThai INT DEFAULT 0 --Chua thanh toan
);

CREATE TABLE HoaDonChiTiet
(
    Id UNIQUEIDENTIFIER PRIMARY KEY,
    Id_HD INT,
    Id_SPCT INT,
    SoLuong INT,
    GiaHienHanh money,
    IdHDCT_Old UNIQUEIDENTIFIER,
    TrangThai INT
);

ALTER TABLE SanPhamChiTiet ADD FOREIGN KEY(Id_SP) REFERENCES SanPham(Id);
ALTER TABLE SanPhamChiTiet ADD FOREIGN KEY(Id_Mau) REFERENCES MauSac(Id);
ALTER TABLE SanPhamChiTiet ADD FOREIGN KEY(Id_Size) REFERENCES [Size](Id);
ALTER TABLE SanPhamChiTiet ADD FOREIGN KEY(Id_KD) REFERENCES KieuDang(Id);
ALTER TABLE SanPhamChiTiet ADD FOREIGN KEY(Id_TH) REFERENCES ThuongHieu(Id);
ALTER TABLE HoaDon ADD FOREIGN KEY(Id_NV) REFERENCES NhanVien(Id);
ALTER TABLE HoaDon ADD FOREIGN KEY(Id_KH) REFERENCES KhachHang(Id);
ALTER TABLE HoaDon ADD FOREIGN KEY(Id_KM) REFERENCES KhuyenMai(Id);
ALTER TABLE HoaDonChiTiet ADD FOREIGN KEY(Id_HD) REFERENCES HoaDon(Id);
ALTER TABLE HoaDonChiTiet ADD FOREIGN KEY(Id_SPCT) REFERENCES SanPhamChiTiet(Id);