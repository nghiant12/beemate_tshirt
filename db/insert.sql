USE DB_DA1
GO
INSERT INTO NhanVien
VALUES
    ('NV001', N'Nguyễn Thu Nghĩa', N'Hà Nội', '0834045324', 'maymimim.1202@gmail.com', 1, 'nghiantt12', '123', 1, 0),
    ('NV002', N'Lê Văn Nam', N'Hà Nội', '0123456789', 'namlv310304@gmail.com', 0, 'namlv', '123', 0, 0),
    ('NV003', N'Vũ Thế Anh', N'Hà Nội', '0123456789', 'anhvt6804@gmail.com', 0, 'anhvt', '123', 0, 0);
SELECT *
FROM NhanVien;

INSERT INTO KhachHang
VALUES
    ('KH001', N'Bùi Cao Minh', '0123456789', N'Hà Nội', 0),
    ('KH002', N'Ngô Văn Khoa', '0823456789', N'Hà Nội', 0),
    ('KH003', N'Nguyễn Đức Khải', '0923456789', N'Hà Nội', 0),
    ('KH004', N'Ngô Phương Mạnh', '0823456789', N'Hà Nội', 0);
SELECT *
FROM KhachHang;

INSERT INTO ThuongHieu
VALUES
    ('TH001', N'Uniqlo', 0),
    ('TH002', N'Adidas', 0),
    ('TH003', N'Nike', 0),
    ('TH004', N'MLB', 0),
    ('TH005', N'Nelly', 0);
SELECT *
FROM ThuongHieu;

INSERT INTO MauSac
VALUES
    ('MS001', N'Đen', 0),
    ('MS002', N'Trắng', 0),
    ('MS003', N'Kem', 0),
    ('MS004', N'Xám', 0),
    ('MS005', N'Hồng nhạt', 0),
    ('MS006', N'Tím nho', 0),
    ('MS007', N'Tím nhạt', 0),
    ('MS008', N'Vàng', 0),
    ('MS009', N'Đỏ', 0),
    ('MS010', N'Xanh mint', 0);
SELECT *
FROM MauSac;

INSERT INTO [Size]
VALUES
    ('SZ001', N'S', 0),
    ('SZ002', N'M', 0),
    ('SZ003', N'L', 0),
    ('SZ004', N'XL', 0),
    ('SZ005', N'2XL', 0);
SELECT *
FROM [Size];

INSERT INTO [KieuDang]
VALUES
    ('KD001', N'Tay lỡ', N'Không', 0),
    ('KD002', N'Tay dài', N'Không', 0),
    ('KD003', N'Oversize', N'Không', 0),
    ('KD004', N'Unisex', N'Không', 0),
    ('KD005', N'Polo', N'Không', 0);
SELECT *
FROM KieuDang;

INSERT INTO SanPham
VALUES
    ('SP001', N'Adidas Tango Logo Tee', N'USA', 0),
    ('SP002', N'Adidas Dame Avatar Pocket Tee', N'USA', 0),
    ('SP003', N'Adidas HUSTLE SAIGON Tee', N'USA', 0),
    ('SP004', N'Nike Sportswear JDI T-Shirt', N'USA', 0),
    ('SP005', N'Jordan Photo Crew [CN3588 010]', N'USA', 0),
    ('SP006', N'Nike Stussy Collab Tee Black', N'USA', 0),
    ('SP007', N'AIRism COTTON T-Shirt', N'Japan', 0),
    ('SP008', N'UT Disney Collection Mini', N'Japan', 0),
    ('SP009', N'Winnie the Pooh Collection T-Shirt', N'Japan', 0),
    ('SP010', N'UT HOKUSAI  T-Shirt', N'Japan', 0);
SELECT *
FROM SanPham;

INSERT INTO SanPhamChiTiet
VALUES
    ('SC001', 1, 1, 2, 4, 1, 20, 550000, 0),
    ('SC002', 1, 2, 3, 4, 1, 15, 550000, 0),
    ('SC003', 5, 3, 2, 3, 1, 10, 725000, 0),
    ('SC004', 6, 1, 2, 1, 2, 15, 475000, 0),
    ('SC005', 8, 10, 1, 1, 2, 10, 370000, 0),
    ('SC006', 8, 7, 3, 1, 2, 10, 370000, 0),
    ('SC007', 9, 1, 2, 3, 3, 15, 654000, 0),
    ('SC008', 9, 6, 1, 2, 3, 15, 654000, 0),
    ('SC009', 10, 1, 2, 4, 3, 10, 950000, 0),
    ('SC010', 10, 3, 3, 4, 4, 10, 950000, 0),
    ('SC011', 7, 1, 2, 3, 4, 15, 325000, 0),
    ('SC012', 7, 4, 4, 3, 4, 15, 325000, 0),
    ('SC013', 2, 5, 1, 4, 2, 5, 450000, 0),
    ('SC014', 3, 8, 3, 3, 2, 10, 350000, 0),
    ('SC015', 4, 9, 3, 1, 1, 5, 550000, 0);
SELECT *
FROM SanPhamChiTiet;

INSERT INTO KhuyenMai
VALUES
    ('KM001', N'30/04 - 01/05', 0, 25, '2023-04-28', '2023-05-02', '2023-04-27', 1000000, 50, 0),
    ('KM002', N'Black Friday', 0, 50, '2023-11-24', '2023-11-24', '2023-11-23', 1500000, 50, 0),
    ('KM003', N'Merry X-mas 23', 0, 20, '2023-12-01', '2023-12-31', '2023-11-29', 1500000, 30, 0),
    ('KM004', N'Tết Dương lịch 2023', 0, 15, '2024-01-01', '2024-01-05', '2023-12-16', 1000000, 25, 0),
    ('KM005', N'Tết Nguyên đán 2024', 0, 25, '2024-01-15', '2024-02-07', '2024-01-13', 2500000, 50, 0);
SELECT *
FROM KhuyenMai;

--INSERT INTO HoaDon
--VALUES
--    (1, 3, NULL, 'HD01', GETDATE(), 0, 0, NULL, NULL, 0, 0)
SELECT *
FROM HoaDon;

--INSERT INTO HoaDonChiTiet 
--VALUES
--    (NEWID(), 1, 2, 2, 550000, NULL, NULL),
--    (NEWID(), 1, 3, 1, 725000, NULL, NULL),
--    (NEWID(), 1, 4, 1, 475000, NULL, NULL),
--    (NEWID(), 1, 5, 1, 370000, NULL, NULL)
SELECT *
FROM HoaDonChiTiet;
