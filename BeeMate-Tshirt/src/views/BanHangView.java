/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.HoaDon;
import model.HoaDonCT;
import model.KhachHang;
import model.KhuyenMai;
import model.Mau;
import model.NhanVien;
import model.Size;
import model.ThuongHieu;
import model.SanPhamCT;
import repo.DangNhapRepo;
import repo.GioHangRepo;
import repo.HoaDonRepo;
import repo.KhachHangRepo;
import repo.KhuyenMaiRepo;
import repo.MauSacRepo;
import repo.NhanVienRepo;
import repo.SanPhamCTRepo;
import repo.SizeRepo;
import repo.ThuongHieuRepo;

/**
 *
 * @author admin
 */
public class BanHangView extends javax.swing.JFrame {

    private GioHangRepo ghRepo;
    private HoaDonRepo hdRepo;
    private KhachHangRepo khRepo;
    private SanPhamCTRepo spctRepo;
    private KhuyenMaiRepo kmRepo;
    private NhanVienRepo nvRepo;
    private MauSacRepo mauSacRepo;
    private SizeRepo sizeRepo;
    private ThuongHieuRepo thuongHieuRepo;

    DecimalFormat currencyFormat = new DecimalFormat("#,##0");

    private String selectedMauSac;
    private String selectedSize;
    private String selectedThuongHieu;
    private ArrayList<SanPhamCT> dsTK = new ArrayList<>();
    private ArrayList<SanPhamCT> dsTK1 = new ArrayList<>();
    private ArrayList<SanPhamCT> dsTK2 = new ArrayList<>();
    private ArrayList<SanPhamCT> dsTK3 = new ArrayList<>();

    private int page = 1;
    private int limit = 5;

    /**
     * Creates new form MainJFrame
     */
    public BanHangView() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.ghRepo = new GioHangRepo();
        this.hdRepo = new HoaDonRepo();
        this.khRepo = new KhachHangRepo();
        this.spctRepo = new SanPhamCTRepo();
        this.kmRepo = new KhuyenMaiRepo();
        this.nvRepo = new NhanVienRepo();
        mauSacRepo = new MauSacRepo();
        sizeRepo = new SizeRepo();
        thuongHieuRepo = new ThuongHieuRepo();
        this.setLocationRelativeTo(null);
        this.loadTenNV();
        Date ngayMuaHang = new Date();
        // Định dạng ngày theo ý muốn, ví dụ "dd/MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String ngayMuaHangFormatted = sdf.format(ngayMuaHang);
        // Hiển thị ngày mua hàng trên label
        lblNgayMuaHang.setText(ngayMuaHangFormatted);

        this.lblPage.setText(this.page + "");

        this.loadHoaDonCho();
        this.loadComboBoxMauSac();
        this.loadComboBoxSize();
        this.loadComboBoxThuongHieu();
        this.loadTableSPCT();

    }

    private void loadTenNV() {
        NhanVien nv = DangNhapRepo.getCurrentUser();
        NhanVien nvl = nvRepo.selectByMa(nv.getTenTaiKhoan());
        if (nv != null) {
            lblTenNV.setText(nvl.getHoTen());
            // Thực hiện các thao tác khác với thông tin `nv`
        } else {
            System.out.println("Không có người dùng nào đăng nhập");
        }
    }

    private void loadHoaDonCho() {
        ArrayList<HoaDon> ds = this.hdRepo.getHoaDonCho();
        DefaultTableModel dtm = (DefaultTableModel) this.tblHoaDon.getModel();
        dtm.setRowCount(0);
        for (HoaDon h : ds) {
            Object[] row = {
                h.getId(),
                h.getMaHD(),
                h.getNv().getMaNV(),
                h.getKh().getHoTen(),
                h.getTrangThai() == 0 ? "Chưa thanh toán" : "Đã thanh toán",
                h.getNgayTao()
            };
            dtm.addRow(row);
        }
    }

    private void loadGioHang() {
        int viTri = this.tblHoaDon.getSelectedRow();
        if (viTri == -1) {
            return;
        }
        String idStr = this.tblHoaDon.getValueAt(viTri, 0).toString();
        int id = Integer.parseInt(idStr);
        ArrayList<HoaDonCT> ds = this.ghRepo.getGioHang(id);
        DefaultTableModel dtm = (DefaultTableModel) this.tblGioHang.getModel();
        dtm.setRowCount(0);
        for (HoaDonCT hdct : ds) {
            Object[] row = {
                hdct.getId(),
                hdct.getSpct().getId(),
                hdct.getSpct().getSp().getTenSP(),
                hdct.getSpct().getM().getTenMau(),
                hdct.getSpct().getS().getTenSize(),
                hdct.getSpct().getKd().getTenKD(),
                hdct.getSpct().getTh().getTenTH(),
                hdct.getSoLuong(),
                hdct.getSpct().getDonGia(),
                hdct.getSpct().getDonGia() * hdct.getSoLuong()
            };
            dtm.addRow(row);
        }
    }

    private void getTongTien() {
        int row = tblHoaDon.getSelectedRow();
        int id_hd = Integer.parseInt(tblHoaDon.getValueAt(row, 0).toString());
        int tongTien = this.ghRepo.getTongTien(id_hd);
        lblTongTien.setText(String.valueOf(tongTien));
    }

    private void loadTableSPCT() {
        ArrayList<SanPhamCT> ds = spctRepo.findAllProductDetailPaging(page, limit);
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamCT.getModel();
        dtm.setRowCount(0);
        for (SanPhamCT spct : ds) {
            Object[] row = {
                spct.getId(),
                spct.getMaSPCT(),
                spct.getSp().getTenSP(),
                spct.getM().getTenMau(),
                spct.getS().getTenSize(),
                spct.getKd().getTenKD(),
                spct.getTh().getTenTH(),
                spct.getSoLuong(),
                spct.getDonGia()
            };
            dtm.addRow(row);
        }
    }

    private int getTotalPages() {
        int totalItems = this.spctRepo.getTotalItems();
        return (int) Math.ceil((double) totalItems / limit);
    }

    private void loadComboBoxMauSac() {
        cboMau.removeAllItems();
        cboMau.addItem("Tất cả");
        ArrayList<Mau> ds = mauSacRepo.findAllMauSac();
        for (Mau m : ds) {
            cboMau.addItem(m.getTenMau());
        }
    }

    private void loadComboBoxThuongHieu() {
        cboThuongHieu.removeAllItems();
        cboThuongHieu.addItem("Tất cả");
        ArrayList<ThuongHieu> ds = thuongHieuRepo.findAllThuongHieu();
        for (ThuongHieu th : ds) {
            cboThuongHieu.addItem(th.getTenTH());
        }
    }

    private void loadComboBoxSize() {
        cboSize.removeAllItems();
        cboSize.addItem("Tất cả");
        ArrayList<Size> ds = sizeRepo.findAllSize();
        for (Size size : ds) {
            cboSize.addItem(size.getTenSize());
        }
    }

    private void taoHoaDon() {
        try {
            NhanVien nv = DangNhapRepo.getCurrentUser();
            if (nv == null) {
                JOptionPane.showMessageDialog(this, "Lỗi: Không thể xác định nhân viên đang đăng nhập.");
                return;
            }

            NhanVien nvl = nvRepo.selectByMa(nv.getTenTaiKhoan());
            if (nvl == null) {
                JOptionPane.showMessageDialog(this, "Lỗi: Không thể tìm thấy thông tin nhân viên.");
                return;
            }

            int nvId = nvl.getId();
            String sdtKH = txtSDT.getText();

            if (sdtKH == null || sdtKH.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại khách hàng.");
                return;
            }

            int khId = this.khRepo.findId_KHBySdt(sdtKH);

            int conf = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn tạo hóa đơn không?");
            if (conf == JOptionPane.YES_OPTION) {
                if (hdRepo.taoHoaDonCho(nvId, khId) > 0) {
                    JOptionPane.showMessageDialog(this, "Tạo hóa đơn chờ thành công!");
                    this.loadHoaDonCho();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi hệ thống khi tạo hóa đơn!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadTableSanPhamCT1() {
        dsTK1.clear();
        dsTK = this.spctRepo.findAllSPCT();
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamCT.getModel();
        dtm.setRowCount(0);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        for (SanPhamCT a : dsTK) {
            if (a.getM().getTenMau().equals(selectedMauSac)) {
                dsTK1.add(a);
                Object[] row = {
                    a.getId(),
                    a.getMaSPCT(),
                    a.getSp().getTenSP(),
                    a.getM().getTenMau(),
                    a.getS().getTenSize(),
                    a.getKd().getTenKD(),
                    a.getTh().getTenTH(),
                    a.getSoLuong(),
                    decimalFormat.format(a.getDonGia())
                };
                dtm.addRow(row);
            }
        }
    }

    private void loadTableSanPhamCT2() {
        dsTK2.clear();
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamCT.getModel();
        dtm.setRowCount(0);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        for (SanPhamCT a : dsTK1) {
            if (a.getS().getTenSize().equals(selectedSize)) {
                dsTK2.add(a);
                Object[] row = {
                    a.getId(),
                    a.getMaSPCT(),
                    a.getSp().getTenSP(),
                    a.getM().getTenMau(),
                    a.getS().getTenSize(),
                    a.getKd().getTenKD(),
                    a.getTh().getTenTH(),
                    a.getSoLuong(),
                    decimalFormat.format(a.getDonGia())
                };
                dtm.addRow(row);
            }
        }
    }

    private void loadTableSanPhamCT3() {
        dsTK3.clear();
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamCT.getModel();
        dtm.setRowCount(0);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        for (SanPhamCT a : dsTK2) {
            if (a.getTh().getTenTH().equals(selectedThuongHieu)) {
                dsTK3.add(a);
                Object[] row = {
                    a.getId(),
                    a.getMaSPCT(),
                    a.getSp().getTenSP(),
                    a.getM().getTenMau(),
                    a.getS().getTenSize(),
                    a.getKd().getTenKD(),
                    a.getTh().getTenTH(),
                    a.getSoLuong(),
                    decimalFormat.format(a.getDonGia())
                };
                dtm.addRow(row);
            }
        }
    }

    private void timKiem() {
        String timKiem = txtTimKiem.getText().toLowerCase(); // Chuyển đổi thành chữ thường
        if (timKiem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu cần tìm");
            return;
        }
        ArrayList<SanPhamCT> dstk = spctRepo.findAllSPCT();
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamCT.getModel();
        dtm.setRowCount(0);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        int check = 0;
        for (SanPhamCT a : dstk) {
            if (a.getMaSPCT().toLowerCase().contains(timKiem) || a.getSp().getTenSP().toLowerCase().contains(timKiem)) { // Kiểm tra nếu mã sản phẩm chứa chuỗi tìm kiếm
                check = 1;
                Object[] row = {
                    a.getId(),
                    a.getMaSPCT(),
                    a.getSp().getTenSP(),
                    a.getM().getTenMau(),
                    a.getS().getTenSize(),
                    a.getKd().getTenKD(),
                    a.getTh().getTenTH(),
                    a.getSoLuong(),
                    decimalFormat.format(a.getDonGia())
                };
                dtm.addRow(row);
            }
        }
        if (check == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
            loadTableSPCT();
        }
    }

    private boolean checkMaKMHanSD(String ngayKetThucKM) {
        try {
            String dateFormat = "yyyy-MM-dd";
            // Định dạng ngày tháng
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            // Chuyển đổi chuỗi ngày tháng sang đối tượng LocalDate
            LocalDate ngayKT = LocalDate.parse(ngayKetThucKM, formatter);
            // Lấy ngày hiện tại
            LocalDate ngayHT = LocalDate.now();
            // So sánh ngày hết hạn với ngày hiện tại
            return ngayHT.isAfter(ngayKT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Định dạng ngày tháng không hợp lệ. Vui lòng kiểm tra lại.");
        }
    }

    private boolean checkAdmin() {
        NhanVien nv = DangNhapRepo.getCurrentUser();
        if (nv == null) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này");
            return false;
        }
        return nv.getVaiTro() == 1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        btnChinhSuaGH = new javax.swing.JButton();
        btnXoaSPGH = new javax.swing.JButton();
        btnLamMoiGH = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPhamCT = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboMau = new javax.swing.JComboBox<>();
        cboSize = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cboThuongHieu = new javax.swing.JComboBox<>();
        btnTimKiem = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnThemSPGH = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblNgayMuaHang = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblTienGiam = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cboHTTT = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jSeparator2 = new javax.swing.JSeparator();
        btnThanhToan = new javax.swing.JButton();
        lblTenKH = new javax.swing.JLabel();
        txtMaKM = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(235, 249, 204));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        jLabel1.setToolTipText("");

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_SP.png"))); // NOI18N
        jButton1.setText("Sản phẩm");
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/consultant.png"))); // NOI18N
        jButton2.setText("Nhân viên");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/technical-support.png"))); // NOI18N
        jButton3.setText("Khách hàng");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/discount.png"))); // NOI18N
        jButton4.setText("Khuyến mãi");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/trolley-cart.png"))); // NOI18N
        jButton5.setText("Bán hàng");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profit.png"))); // NOI18N
        jButton6.setText("Thống kê");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_HoaDon.png"))); // NOI18N
        jButton7.setText("Hóa đơn");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconThoat.png"))); // NOI18N
        jButton8.setText("Đăng xuất");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("GIỎ HÀNG"));

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id_HDCT", "Id_SPCT", "TenSP", "Màu", "Size", "Kiểu dáng", "Thương hiệu", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane1.setViewportView(tblGioHang);
        if (tblGioHang.getColumnModel().getColumnCount() > 0) {
            tblGioHang.getColumnModel().getColumn(5).setResizable(false);
        }

        btnChinhSuaGH.setText("Chỉnh sửa");
        btnChinhSuaGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChinhSuaGHActionPerformed(evt);
            }
        });

        btnXoaSPGH.setText("Xóa");
        btnXoaSPGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPGHActionPerformed(evt);
            }
        });

        btnLamMoiGH.setText("Làm mới");
        btnLamMoiGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiGHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnChinhSuaGH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(btnXoaSPGH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnLamMoiGH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChinhSuaGH)
                    .addComponent(btnLamMoiGH)
                    .addComponent(btnXoaSPGH))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("HÓA ĐƠN CHỜ"));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID_HD", "MaHD", "MaNV", "Tên KH", "Trạng thái", "Ngày tạo"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(5).setResizable(false);
        }

        jButton11.setBackground(new java.awt.Color(204, 204, 255));
        jButton11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton11.setText("Tạo Hóa đơn");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("DANH SÁCH SẢN PHẨM"));

        tblSanPhamCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID_SPCT", "MaSPCT", "TenSP", "Màu", "Size", "Kiểu dáng", "Thương hiệu", "Số lượng", "Đơn giá"
            }
        ));
        jScrollPane3.setViewportView(tblSanPhamCT);

        jLabel2.setText("Mã/Tên SP");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jLabel3.setText("Màu sắc");

        cboMau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMau.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMauItemStateChanged(evt);
            }
        });
        cboMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauActionPerformed(evt);
            }
        });

        cboSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSize.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSizeItemStateChanged(evt);
            }
        });

        jLabel4.setText("Size");

        jLabel6.setText("Thương hiệu");

        cboThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboThuongHieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboThuongHieuItemStateChanged(evt);
            }
        });

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimKiemMouseClicked(evt);
            }
        });
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThemSPGH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnThemSPGH.setText("Thêm Sản phẩm");
        btnThemSPGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPGHActionPerformed(evt);
            }
        });

        btnPre.setText("<<");
        btnPre.setEnabled(false);
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        lblPage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPage.setText("0");

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(173, 173, 173)
                                .addComponent(btnThemSPGH, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboMau, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboSize, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(319, 319, 319))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblPage, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTimKiem)
                        .addComponent(btnLamMoi))
                    .addComponent(btnThemSPGH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPre)
                    .addComponent(btnNext)
                    .addComponent(lblPage))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Thanh toán"));

        jLabel7.setText("SDT");

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        jLabel8.setText("Khách hàng");

        jLabel9.setText("Nhân viên");

        lblTenNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenNV.setText("jLabel10");

        jLabel11.setText("Ngày mua hàng");

        lblNgayMuaHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNgayMuaHang.setText("dd/mm/yyyy");

        jLabel15.setText("Tiền giảm");

        lblTienGiam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTienGiam.setText("0");

        jLabel17.setText("Mã KM");

        jLabel20.setText("Thành tiền");

        lblThanhTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblThanhTien.setText("0");

        jLabel22.setText("HT Thanh toán");

        cboHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản" }));

        jLabel26.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        btnThanhToan.setBackground(new java.awt.Color(204, 255, 255));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        lblTenKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenKH.setText("Khách vãng lai");

        txtMaKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKMActionPerformed(evt);
            }
        });

        jLabel18.setText("Tổng tiền");

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTongTien.setText("0");

        jLabel5.setText("VND");

        jLabel10.setText("VND");

        jLabel12.setText("VND");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addGap(21, 21, 21)
                                            .addComponent(txtMaKM, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addGap(33, 33, 33)
                                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                    .addComponent(lblThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                    .addComponent(lblTienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGap(1, 1, 1))
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(lblTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jSeparator2)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(37, 37, 37)
                                                .addComponent(lblNgayMuaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 5, Short.MAX_VALUE))))
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblTenNV))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblTenKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblNgayMuaHang))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lblTongTien)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtMaKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblTienGiam)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(lblThanhTien)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (checkAdmin()) {
            new NhanVienView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (checkAdmin()) {
            new KhachHangView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (checkAdmin()) {
            new KhuyenMaiView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
//        new BanHangView().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Xác nhận đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (check == JOptionPane.YES_OPTION) {
            dispose();
            new DangNhapView().setVisible(true);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        try {
            int row = tblHoaDon.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn muốn thanh toán");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thanh toán không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            int id_hd = Integer.parseInt(tblHoaDon.getValueAt(row, 0).toString());
            String s = txtMaKM.getText().trim();
            Integer id_km = s.length() == 0 ? null : this.kmRepo.getId_KmByMaKhuyenMai(s);

            float tongTien = Float.parseFloat(lblTongTien.getText());
            String tienGiam = lblTienGiam.getText();
            tienGiam = tienGiam.replace(",", "");
            float soTienGiamGia = Float.parseFloat(tienGiam);
            String hinhThucThanhToan = cboHTTT.getSelectedItem().toString();
            String ghiChu = txtGhiChu.getText();

            String tt = lblThanhTien.getText();
            tt = tt.replace(",", "");
            float thanhTien = Float.parseFloat(tt);

            if (id_km != null) {
                // Kiểm tra mã khuyến mãi và cập nhật số lượng mã km và số hóa đơn được áp dụng
                boolean isValid = this.kmRepo.checkMaKhuyenMai(s);
                if (isValid) {
                    this.kmRepo.updateMaKhuyenMai(s);
                } else {
                    JOptionPane.showMessageDialog(this, "Mã khuyến mãi không hợp lệ hoặc đã hết số lượng");
                    return;
                }
            }

            this.hdRepo.thanhToanHoaDon(id_hd, id_km, tongTien, soTienGiamGia, hinhThucThanhToan, ghiChu, thanhTien);
            JOptionPane.showMessageDialog(this, "Thanh toán thành công");
            this.loadHoaDonCho();

            DefaultTableModel dtm = (DefaultTableModel) this.tblGioHang.getModel();
            dtm.setRowCount(0);
            lblTenKH.setText("Khách vãng lai");
            txtSDT.setText("");
            txtMaKM.setText("");
            lblTienGiam.setText("0");
            lblTongTien.setText("0");
            lblThanhTien.setText("0");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thanh toán");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (checkAdmin()) {
            new SanPhamView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
        String SoDT = txtSDT.getText();
        if (SoDT.trim().length() == 10) {
            List<KhachHang> listKH = this.khRepo.getAllKhachHangTheoSDT(SoDT);
            if (!listKH.isEmpty()) {
                KhachHang kh = listKH.get(0);
                lblTenKH.setText(kh.getHoTen());
            } else {
                lblTenKH.setText("Khách vãng lai");
            }
        } else if (SoDT.trim().length() < 10 || SoDT.trim().length() > 10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải chứa đủ 10 ký tự", "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
        } else if (!SoDT.trim().matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải phải là số", "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
        }
    }//GEN-LAST:event_txtSDTActionPerformed

    private void btnXoaSPGHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPGHActionPerformed
        // TODO add your handling code here:
        try {
            int row = tblGioHang.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sản phẩm trong giỏ hàng.");
                return;
            }
            int id_spct = Integer.parseInt(tblGioHang.getValueAt(row, 1).toString());
            int soLuongSPCT = this.ghRepo.getSoLuongById_SPCT(id_spct);
            int soLuongSPGH = Integer.parseInt(tblGioHang.getValueAt(row, 7).toString());

            int newSoLuong = soLuongSPGH + soLuongSPCT;

            int xacNhanXoa = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng không?");
            if (xacNhanXoa != JOptionPane.YES_OPTION) {
                return;
            }
            this.ghRepo.updateSoLuongSPCT(newSoLuong, id_spct);
            this.ghRepo.deleteHdctById_Spct(id_spct);
            JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công");
            this.loadGioHang();
            this.loadTableSPCT();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa sản phẩm");
        }
    }//GEN-LAST:event_btnXoaSPGHActionPerformed

    private void btnThemSPGHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPGHActionPerformed
        // TODO add your handling code here:
        int rowHD = tblHoaDon.getSelectedRow();
        if (rowHD == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn");
            return;
        }
        int rowSPCT = tblSanPhamCT.getSelectedRow();
        if (rowSPCT == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm");
            return;
        }
        String input = JOptionPane.showInputDialog(this, "Nhập số lượng: ");
        if (input != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thêm sản phẩm này vào giỏ hàng không?");
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            try {
                int id_hd = Integer.parseInt(tblHoaDon.getValueAt(rowHD, 0).toString());
                int id_spct = Integer.parseInt(tblSanPhamCT.getValueAt(rowSPCT, 0).toString());
                float giaHienHanh = Float.parseFloat(tblSanPhamCT.getValueAt(rowSPCT, 8).toString());

                int newSoLuong = Integer.parseInt(input);
                int soLuongSPCT = this.ghRepo.getSoLuongById_SPCT(id_spct);

                if (newSoLuong > 0 && newSoLuong < soLuongSPCT) {
                    this.ghRepo.themSPGH(id_hd, id_spct, newSoLuong, giaHienHanh);
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào giỏ hàng thành công");
                    this.loadGioHang();
                    this.loadTableSPCT();
                } else {
                    JOptionPane.showMessageDialog(this, "Số lượng nhập vào không hợp lệ");
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Chỉnh sửa số lượng thất bại");
            }
        }

    }//GEN-LAST:event_btnThemSPGHActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        int row = tblHoaDon.getSelectedRow();
        lblTenKH.setText(tblHoaDon.getValueAt(row, 3).toString());
        txtSDT.setText(this.khRepo.findSdtKHByTen(tblHoaDon.getValueAt(row, 3).toString()));
        lblNgayMuaHang.setText(tblHoaDon.getValueAt(row, 5).toString());
        float tongTien = this.ghRepo.getTongTien(Integer.parseInt(tblHoaDon.getValueAt(row, 0).toString()));
        lblThanhTien.setText(String.valueOf(tongTien));
        this.loadGioHang();
        this.getTongTien();
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        this.taoHoaDon();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void cboMauItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMauItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            selectedMauSac = (String) cboMau.getSelectedItem();
            if (selectedMauSac != "Tất cả") {
                cboSize.setEnabled(true);
                loadTableSanPhamCT1();
            } else {
                loadTableSPCT();
                cboSize.setEnabled(false);
                return;
            }
        }
    }//GEN-LAST:event_cboMauItemStateChanged

    private void cboSizeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSizeItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            selectedSize = (String) cboSize.getSelectedItem();
            if (selectedSize != "Tất cả") {
                cboMau.setEnabled(false);
                cboThuongHieu.setEnabled(true);
                loadTableSanPhamCT2();
            } else {
                loadTableSanPhamCT1();
                cboMau.setEnabled(true);
                cboThuongHieu.setEnabled(false);
                return;
            }
        }
    }//GEN-LAST:event_cboSizeItemStateChanged

    private void cboThuongHieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboThuongHieuItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            selectedThuongHieu = (String) cboThuongHieu.getSelectedItem();
            if (selectedThuongHieu != "Tất cả") {
                cboSize.setEnabled(false);
                loadTableSanPhamCT3();
            } else {
                loadTableSanPhamCT2();
                if (cboMau.isEnabled() == false) {
                    cboSize.setEnabled(true);
                }
                return;
            }
        }
    }//GEN-LAST:event_cboThuongHieuItemStateChanged

    private void btnTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseClicked
        // TODO add your handling code here:
        this.timKiem();
    }//GEN-LAST:event_btnTimKiemMouseClicked

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        txtTimKiem.setText("");
        cboMau.setSelectedIndex(0);
        cboSize.setSelectedIndex(0);
        cboThuongHieu.setSelectedIndex(0);
        dsTK.clear();
        dsTK1.clear();
        dsTK2.clear();
        dsTK3.clear();
        loadTableSPCT();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnChinhSuaGHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChinhSuaGHActionPerformed
        // TODO add your handling code here:
        try {
            int row = tblGioHang.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng dữ liệu cần sửa");
                return;
            }
            int soLuongSPGH = Integer.parseInt(tblGioHang.getValueAt(row, 7).toString());
            String id_hdct = tblGioHang.getValueAt(row, 0).toString();
            int id_spct = Integer.parseInt(tblGioHang.getValueAt(row, 1).toString());
            int soLuongSPCT = this.ghRepo.getSoLuongById_SPCT(id_spct);
            String input = JOptionPane.showInputDialog(this, "Nhập số lượng: ", soLuongSPGH);
            if (input != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn chỉnh sửa sản phẩm này không?");
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
                try {
                    int newSoLuong = Integer.parseInt(input);
                    int newSoLuongSPCT = soLuongSPCT - newSoLuong + soLuongSPGH;
                    if (newSoLuong > 0 && newSoLuong <= soLuongSPGH + soLuongSPCT) {
                        this.ghRepo.updateSoLuongHDCT(newSoLuong, id_hdct);
                        this.ghRepo.updateSoLuongSPCT(newSoLuongSPCT, id_spct);
                        JOptionPane.showMessageDialog(this, "Chỉnh sửa số lượng thành công");
                        this.loadGioHang();
                        this.loadTableSPCT();
                    } else {
                        JOptionPane.showMessageDialog(this, "Số lượng nhập vào không hợp lệ");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Chỉnh sửa số lượng thất bại");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗsửai ");
        }
    }//GEN-LAST:event_btnChinhSuaGHActionPerformed

    private void btnLamMoiGHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiGHActionPerformed
        // TODO add your handling code here:
        try {
            int row = tblHoaDon.getSelectedRow();
            int id_hd = Integer.parseInt(tblHoaDon.getValueAt(row, 0).toString());
            ArrayList<HoaDon> ds = this.hdRepo.getHoaDonCho();
            for (HoaDon h : ds) {
                if (h.getId() == id_hd) {
                    int xacNhanXoa = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn làm mới hóa đơn này không?");
                    if (xacNhanXoa != JOptionPane.YES_OPTION) {
                        return;
                    }
                    this.ghRepo.clearCart(h.getId());
                    JOptionPane.showMessageDialog(this, "Làm mới giỏ hàng thành công!");
                    this.loadGioHang();
                    this.loadTableSPCT();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần làm mới");
        }
    }//GEN-LAST:event_btnLamMoiGHActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtMaKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKMActionPerformed
        // TODO add your handling code here:
//        try {
//            String MaKhuyenMai = txtMaKM.getText();
////            System.out.println("MaKM: " + MaKhuyenMai);
//            double tongTien = currencyFormat.parse(lblTongTien.getText()).doubleValue();
//            double giamGia = 0;
//            List<KhuyenMai> listKM = this.kmRepo.getAllKhuyenMaiByMa(MaKhuyenMai);
//            if (listKM != null && !listKM.isEmpty()) {
//                KhuyenMai km = listKM.get(0);
////                System.out.println("MucGiamGia: " + km.getMucGiamGia());
//                if (tongTien >= km.getHoaDonToiThieu()) {
//                    giamGia = (km.getMucGiamGia() * tongTien) / 100.0;
//                } else {
//                    JOptionPane.showMessageDialog(this, "Bạn không thể áp dụng được mã này vì tổng tiền nhỏ hơn số tiền tối thiểu của khuyến mãi");
//                    txtMaKM.setText("");
//                }
//                lblTienGiam.setText(currencyFormat.format(giamGia));
//                lblThanhTien.setText(currencyFormat.format(tongTien - giamGia));
//            } else {
//                JOptionPane.showMessageDialog(this, "Không tìm thấy mã khuyến mãi phù hợp");
//                txtMaKM.setText("");
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            String MaKhuyenMai = txtMaKM.getText();
            System.out.println("MaKM: " + MaKhuyenMai);
            double tongTien = currencyFormat.parse(lblTongTien.getText()).doubleValue();
            double giamGia = 0;
            List<KhuyenMai> listKM = this.kmRepo.getAllKhuyenMaiByMa(MaKhuyenMai);
            if (listKM != null && !listKM.isEmpty()) {
                KhuyenMai km = listKM.get(0);
                String ngayKTString = km.getNgayKT().toString();
                System.out.println(ngayKTString);
                boolean check = checkMaKMHanSD(ngayKTString);
                if (check == false) {
                    if (tongTien >= km.getHoaDonToiThieu()) {
                        giamGia = (km.getMucGiamGia() * tongTien) / 100.0;
                    } else {
                        JOptionPane.showMessageDialog(this, "Bạn không thể áp dụng được mã này vì tổng tiền nhỏ hơn số tiền tối thiểu của khuyến mãi");
                        txtMaKM.setText("");
                    }
                    lblTienGiam.setText(currencyFormat.format(giamGia));
                    lblThanhTien.setText(currencyFormat.format(tongTien - giamGia));
                } else {
                    JOptionPane.showMessageDialog(this, "Mã khuyễn mãi đã hết hạn!");
                    txtMaKM.setText("");
                }
                System.out.println("MucGiamGia: " + km.getMucGiamGia());

            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy mã khuyến mãi phù hợp");
                txtMaKM.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtMaKMActionPerformed

    private void cboMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMauActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if (checkAdmin()) {
            new HoaDonView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if (checkAdmin()) {
            new ThongKeView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        int totalPages = getTotalPages(); // Phương thức để lấy tổng số trang
        if (this.page < totalPages) {
            this.page++;
            this.lblPage.setText(String.valueOf(this.page));
            this.loadTableSPCT();
        }
        // Cập nhật trạng thái nút Back
        this.btnPre.setEnabled(this.page > 1);
        // Cập nhật trạng thái nút Next
        this.btnNext.setEnabled(this.page < totalPages);
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        // TODO add your handling code here:
        if (this.page > 1) {
            this.page--;
            this.lblPage.setText(String.valueOf(this.page));
            this.loadTableSPCT();
        }

        // Cập nhật trạng thái nút Back
        this.btnPre.setEnabled(this.page > 1);
        // Cập nhật trạng thái nút Next
        int totalPages = getTotalPages();
        this.btnNext.setEnabled(this.page < totalPages);
    }//GEN-LAST:event_btnPreActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BanHangView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BanHangView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BanHangView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BanHangView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BanHangView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    // Variables declaration - do not modify
    // Variables declaration - do not modify
    // Variables declaration - do not modify

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChinhSuaGH;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLamMoiGH;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemSPGH;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaSPGH;
    private javax.swing.JComboBox<String> cboHTTT;
    private javax.swing.JComboBox<String> cboMau;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JComboBox<String> cboThuongHieu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblNgayMuaHang;
    private javax.swing.JLabel lblPage;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JLabel lblTienGiam;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPhamCT;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaKM;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
