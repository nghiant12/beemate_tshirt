/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views;

import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.KieuDang;
import model.Mau;
import model.SanPham;
import model.SanPhamCT;
import model.Size;
import model.ThuongHieu;
import repo.KieuDangRepo;
import repo.MauSacRepo;
import repo.SanPhamCTRepo;
import repo.SizeRepo;
import repo.ThuongHieuRepo;

/**
 *
 * @author maymimim
 */
public class SanPhamCT_View extends javax.swing.JDialog {

    /**
     * Creates new form SanPhamCT_
     */
    private int id_sp;
    private int page = 1;
    private int limit = 5;

    private SanPhamCTRepo spctRepo;
    private MauSacRepo msRepo;
    private SizeRepo sizeRepo;
    private KieuDangRepo kdRepo;
    private ThuongHieuRepo thRepo;

    private String selectedMauSac;
    private String selectedSize;
    private String selectedThuongHieu;
    private String selectedKieuDang;

    private ArrayList<SanPhamCT> dsTK;
    private ArrayList<SanPhamCT> dsTK1;
    private ArrayList<SanPhamCT> dsTK2;
    private ArrayList<SanPhamCT> dsTK3;
    private ArrayList<SanPhamCT> dsTK4;

    private JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

    public SanPhamCT_View(JFrame parent, int id_sp) {
        super(parent, true);
        initComponents();
        this.id_sp = id_sp;
        this.setLocationRelativeTo(null);
        this.spctRepo = new SanPhamCTRepo();
        this.msRepo = new MauSacRepo();
        this.sizeRepo = new SizeRepo();
        this.kdRepo = new KieuDangRepo();
        this.thRepo = new ThuongHieuRepo();

        this.dsTK = new ArrayList<>();
        this.dsTK1 = new ArrayList<>();
        this.dsTK2 = new ArrayList<>();
        this.dsTK3 = new ArrayList<>();
        this.dsTK4 = new ArrayList<>();

        this.lblPage.setText(this.page + "");

        this.loadCboMauSac();
        this.loadCboSize();
        this.loadCboKieuDang();
        this.loadCboThuongHieu();

        this.loadCboLocMauSac();
        this.loadCboLocSize();
        this.loadCboLocThuongHieu();
        this.loadCboLocKieuDang();

        this.maAuto();
        this.loadTable();
    }

    private void loadCboMauSac() {
        cboMauSac.removeAllItems();
        ArrayList<model.Mau> ds = this.msRepo.findAllMauSac();
        for (model.Mau m : ds) {
            cboMauSac.addItem(m.getTenMau());
        }
    }

    private void loadCboSize() {
        cboSize.removeAllItems();
        ArrayList<Size> ds = this.sizeRepo.findAllSize();
        for (Size size : ds) {
            cboSize.addItem(size.getTenSize());
        }
    }

    private void loadCboKieuDang() {
        cboThuongHieu.removeAllItems();
        ArrayList<KieuDang> ds = this.kdRepo.findAll();
        for (KieuDang k : ds) {
            cboKieuDang.addItem(k.getTenKD());
        }
    }

    private void loadCboThuongHieu() {
        cboThuongHieu.removeAllItems();
        ArrayList<ThuongHieu> ds = this.thRepo.findAllThuongHieu();
        for (ThuongHieu th : ds) {
            cboThuongHieu.addItem(th.getTenTH());
        }
    }

    private void loadCboLocMauSac() {
        cboLocMauSac.removeAllItems();
        cboLocMauSac.addItem("Tất cả");
        ArrayList<Mau> ds = msRepo.findAllMauSac();
        for (Mau m : ds) {
            cboLocMauSac.addItem(m.getTenMau());
        }
    }

    private void loadCboLocSize() {
        cboLocSize.removeAllItems();
        cboLocSize.addItem("Tất cả");
        ArrayList<Size> ds = sizeRepo.findAllSize();
        for (Size size : ds) {
            cboLocSize.addItem(size.getTenSize());
        }
    }

    private void loadCboLocThuongHieu() {
        cboLocThuongHieu.removeAllItems();
        cboLocThuongHieu.addItem("Tất cả");
        ArrayList<ThuongHieu> ds = thRepo.findAllThuongHieu();
        for (ThuongHieu th : ds) {
            cboLocThuongHieu.addItem(th.getTenTH());
        }
    }

    private void loadCboLocKieuDang() {
        cboLocKieuDang.removeAllItems();
        cboLocKieuDang.addItem("Tất cả");
        ArrayList<KieuDang> ds = kdRepo.findAllKd();
        for (KieuDang k : ds) {
            cboLocKieuDang.addItem(k.getTenKD());
        }
    }

    private void loadTableSanPhamCT1() {
        dsTK1.clear();
        dsTK = this.spctRepo.findSPCT_ByIdSp(id_sp);
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamCT.getModel();
        dtm.setRowCount(0);
        for (SanPhamCT s : dsTK) {
            if (s.getM().getTenMau().equals(selectedMauSac)) {
                dsTK1.add(s);
                Object[] row = {
                    s.getMaSPCT(),
                    s.getM().getTenMau(),
                    s.getS().getTenSize(),
                    s.getKd().getTenKD(),
                    s.getTh().getTenTH(),
                    s.getDonGia(),
                    s.getSoLuong(),
                    s.getTrangThai() == 0 ? "Đang bán" : "Dừng bán"
                };
                dtm.addRow(row);
            }
        }
    }

    private void loadTableSanPhamCT2() {
        dsTK2.clear();
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamCT.getModel();
        dtm.setRowCount(0);
        for (SanPhamCT s : dsTK1) {
            if (s.getS().getTenSize().equals(selectedSize)) {
                dsTK2.add(s);
                Object[] row = {
                    s.getMaSPCT(),
                    s.getM().getTenMau(),
                    s.getS().getTenSize(),
                    s.getKd().getTenKD(),
                    s.getTh().getTenTH(),
                    s.getDonGia(),
                    s.getSoLuong(),
                    s.getTrangThai() == 0 ? "Đang bán" : "Dừng bán"
                };
                dtm.addRow(row);
            }
        }
    }

    private void loadTableSanPhamCT3() {
        dsTK3.clear();
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamCT.getModel();
        dtm.setRowCount(0);
        for (SanPhamCT s : dsTK2) {
            if (s.getTh().getTenTH().equals(selectedThuongHieu)) {
                dsTK3.add(s);
                Object[] row = {
                    s.getMaSPCT(),
                    s.getM().getTenMau(),
                    s.getS().getTenSize(),
                    s.getKd().getTenKD(),
                    s.getTh().getTenTH(),
                    s.getDonGia(),
                    s.getSoLuong(),
                    s.getTrangThai() == 0 ? "Đang bán" : "Dừng bán"
                };
                dtm.addRow(row);
            }
        }
    }

    private void loadTableSanPhamCT4() {
        dsTK4.clear();
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamCT.getModel();
        dtm.setRowCount(0);
        for (SanPhamCT s : dsTK3) {
            if (s.getKd().getTenKD().equals(selectedKieuDang)) {
                dsTK4.add(s);
                Object[] row = {
                    s.getMaSPCT(),
                    s.getM().getTenMau(),
                    s.getS().getTenSize(),
                    s.getKd().getTenKD(),
                    s.getTh().getTenTH(),
                    s.getDonGia(),
                    s.getSoLuong(),
                    s.getTrangThai() == 0 ? "Đang bán" : "Dừng bán"
                };
                dtm.addRow(row);
            }
        }
    }

    private void loadTable() {
        ArrayList<SanPhamCT> ds = spctRepo.paging(page, limit, id_sp);
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamCT.getModel();
        dtm.setRowCount(0);
        for (model.SanPhamCT spct : ds) {
            Object[] row = {
                spct.getMaSPCT(),
                spct.getM().getTenMau(),
                spct.getS().getTenSize(),
                spct.getKd().getTenKD(),
                spct.getTh().getTenTH(),
                spct.getDonGia(),
                spct.getSoLuong(),
                spct.getTrangThai() == 0 ? "Đang bán" : "Dừng bán"
            };
            dtm.addRow(row);
        }
    }

    //hiển thị dữ liệu lên form
    private void showData(int row) {
        txtMaSPCT.setText(tblSanPhamCT.getValueAt(row, 0).toString());
        cboMauSac.setSelectedItem(tblSanPhamCT.getValueAt(row, 1).toString());
        cboSize.setSelectedItem(tblSanPhamCT.getValueAt(row, 2).toString());
        cboKieuDang.setSelectedItem(tblSanPhamCT.getValueAt(row, 3).toString());
        cboThuongHieu.setSelectedItem(tblSanPhamCT.getValueAt(row, 4).toString());
        txtDonGia.setText(tblSanPhamCT.getValueAt(row, 5).toString());
        txtSoLuong.setText(tblSanPhamCT.getValueAt(row, 6).toString());
        if (tblSanPhamCT.getValueAt(row, 7).toString().equals("Đang bán")) {
            rdoActive.setSelected(true);
        } else {
            rdoActive.setSelected(false);
            rdoUnactive.setSelected(true);
        }
    }

    //làm mới form
    private void clearForm() {
        txtMaSPCT.setText(null);
        txtDonGia.setText(null);
        txtSoLuong.setText(null);
        cboMauSac.setSelectedIndex(0);
        cboSize.setSelectedIndex(0);
        cboThuongHieu.setSelectedIndex(0);
        cboKieuDang.setSelectedIndex(0);

    }

    private void maAuto() {
        ArrayList<SanPhamCT> ds = spctRepo.findAllSPCT();
        if (ds.size() == 0) {
            txtMaSPCT.setText("SC001");
        } else {
            int viTriCuoi = ds.size() - 1;
            SanPhamCT s = ds.get(viTriCuoi);
            String ma = s.getMaSPCT();
            int so = Integer.valueOf(ma.substring(2));
            so++;
            String id = "SC0" + so;
            txtMaSPCT.setText(id);
        }

    }

    //lấy dữ liệu từ form
    private SanPhamCT getFormData() {
        SanPhamCT s = new SanPhamCT();
        String ma = txtMaSPCT.getText().trim();
        float donGia = Float.parseFloat(txtDonGia.getText().trim());
        int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
        int trangThai;
        if (this.rdoActive.isSelected()) {
            trangThai = 0;
        } else {
            trangThai = 1;
        }
        s.setMaSPCT(ma);
        s.setDonGia(donGia);
        s.setSoLuong(soLuong);
        s.setTrangThai(trangThai);
        s.setSp(new SanPham(id_sp));

        for (Mau m : msRepo.findAllMauSac()) {
            if (cboMauSac.getSelectedItem().equals(m.getTenMau())) {
                s.setM(new Mau(m.getId()));
            }
        }
        for (Size size : sizeRepo.findAllSize()) {
            if (cboSize.getSelectedItem().equals(size.getTenSize())) {
                s.setS(new Size(size.getId()));
            }
        }
        for (KieuDang k : kdRepo.findAll()) {
            if (cboKieuDang.getSelectedItem().equals(k.getTenKD())) {
                s.setKd(new KieuDang(k.getId()));
            }
        }
        for (ThuongHieu t : thRepo.findAllThuongHieu()) {
            if (cboThuongHieu.getSelectedItem().equals(t.getTenTH())) {
                s.setTh(new ThuongHieu(t.getId()));
            }
        }
        return s;
    }

    //check form
    private boolean checkForm() {
        if (txtDonGia.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Đơn giá không được để trống");
            txtDonGia.requestFocus();
            return false;
        } else {
            try {
                float donGia = Float.parseFloat(txtDonGia.getText().trim());
                if (donGia < 0) {
                    JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn hoặc bằng 0");
                    txtDonGia.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải là số");
                txtDonGia.requestFocus();
                return false;
            }
        }

        if (txtSoLuong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số lượng không được để trống");
            txtSoLuong.requestFocus();
            return false;
        } else {
            try {
                int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
                if (soLuong < 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn hoặc bằng 0");
                    txtSoLuong.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số");
                txtSoLuong.requestFocus();
                return false;
            }
        }
        return true;
    }

    private int getTotalPages() {
        int totalItems = this.spctRepo.getTotalItems();
        return (int) Math.ceil((double) totalItems / limit);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnSanPham = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtMaSPCT = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClearForm = new javax.swing.JButton();
        cboThuongHieu = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        rdoActive = new javax.swing.JRadioButton();
        rdoUnactive = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        cboSize = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cboKieuDang = new javax.swing.JComboBox<>();
        btnThuongHieu = new javax.swing.JButton();
        btnMauSac = new javax.swing.JButton();
        btnSize = new javax.swing.JButton();
        btnKieuDang = new javax.swing.JButton();
        btnMa = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSanPhamCT = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        txtMaSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        cboLocMauSac = new javax.swing.JComboBox<>();
        cboLocSize = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cboLocThuongHieu = new javax.swing.JComboBox<>();
        btnClearSearch = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        cboLocKieuDang = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnSanPham.setBackground(new java.awt.Color(248, 242, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Tùy chỉnh sản phẩm"));

        jLabel9.setText("Mã SPCT");

        txtMaSPCT.setEditable(false);

        jLabel11.setText("Thương hiệu");

        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Chỉnh sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClearForm.setText("Làm mới");
        btnClearForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFormActionPerformed(evt);
            }
        });

        jLabel14.setText("Đơn giá");

        jLabel15.setText("Số lượng");

        jLabel2.setText("Trạng thái");

        buttonGroup1.add(rdoActive);
        rdoActive.setSelected(true);
        rdoActive.setText("Đang bán");

        buttonGroup1.add(rdoUnactive);
        rdoUnactive.setText("Dừng bán");

        jLabel12.setText("Size");

        jLabel10.setText("Màu");

        jLabel13.setText("Kiểu dáng");

        btnThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuongHieuActionPerformed(evt);
            }
        });

        btnMauSac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMauSacActionPerformed(evt);
            }
        });

        btnSize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSizeActionPerformed(evt);
            }
        });

        btnKieuDang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKieuDangActionPerformed(evt);
            }
        });

        btnMa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update.png"))); // NOI18N
        btnMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSize))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addGap(38, 38, 38)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnClearForm, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(102, 102, 102)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(43, 43, 43)
                            .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMauSac))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rdoActive, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(rdoUnactive, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(cboKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnKieuDang))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(84, 84, 84)
                                    .addComponent(txtMaSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnMa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(108, 108, 108)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnThuongHieu))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAdd, btnClearForm, btnUpdate});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnKieuDang, btnMauSac, btnSize, btnThuongHieu});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(txtMaSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11)
                                .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnMa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(1, 1, 1))
                        .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnSize, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cboKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(rdoActive)
                        .addComponent(rdoUnactive)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnClearForm))
                .addGap(20, 20, 20))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAdd, btnClearForm, btnUpdate});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cboKieuDang, cboMauSac, cboSize, cboThuongHieu, txtDonGia, txtMaSPCT, txtSoLuong});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnKieuDang, btnMauSac, btnSize, btnThuongHieu});

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        tblSanPhamCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SPCT", "Màu", "Size", "Kiểu dáng", "Thương hiệu", "Đơn giá", "Số lượng", "Trạng thái"
            }
        ));
        tblSanPhamCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamCTMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblSanPhamCT);

        jLabel20.setText("Mã Sản phẩm chi tiết");

        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel16.setText("Màu");

        cboLocMauSac.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLocMauSacItemStateChanged(evt);
            }
        });

        cboLocSize.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLocSizeItemStateChanged(evt);
            }
        });

        jLabel17.setText("Size");

        jLabel19.setText("Thương hiệu");

        cboLocThuongHieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLocThuongHieuItemStateChanged(evt);
            }
        });

        btnClearSearch.setText("Làm mới");
        btnClearSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSearchActionPerformed(evt);
            }
        });

        btnBack.setText("<<");
        btnBack.setEnabled(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
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

        jLabel21.setText("Kiểu dáng");

        cboLocKieuDang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLocKieuDangItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboLocMauSac, 0, 85, Short.MAX_VALUE))
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtMaSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboLocSize, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboLocThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboLocKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblPage, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 67, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch)
                        .addComponent(btnClearSearch)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(cboLocKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(cboLocSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)
                        .addComponent(cboLocThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(cboLocMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(btnNext)
                    .addComponent(lblPage))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ SẢN PHẨM CHI TIẾT");

        javax.swing.GroupLayout pnSanPhamLayout = new javax.swing.GroupLayout(pnSanPham);
        pnSanPham.setLayout(pnSanPhamLayout);
        pnSanPhamLayout.setHorizontalGroup(
            pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(pnSanPhamLayout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnSanPhamLayout.setVerticalGroup(
            pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        //thêm spct
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thêm SPCT này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        if (checkForm()) {
            try {
                if (this.spctRepo.checkTrungMa(txtMaSPCT.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "Mã SPCT đã tồn tại");
                    return;
                }
                SanPhamCT s = getFormData();
                if (this.spctRepo.checkTrungSPCT(
                        s.getM().getId(),
                        s.getS().getId(),
                        s.getKd().getId(),
                        s.getTh().getId()
                )) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm chi tiết đã tồn tại");
                    return;
                }
                this.spctRepo.create(s);
                this.clearForm();
                this.loadTable();
                JOptionPane.showMessageDialog(this, "Thêm mới thành công");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm chi tiết");
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        //cập nhật spct
        int row = tblSanPhamCT.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng dữ liệu cần sửa");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa SPCT này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        if (checkForm()) {
            SanPhamCT s = getFormData();
            this.spctRepo.update(s, tblSanPhamCT.getValueAt(row, 0).toString());
            this.clearForm();
            this.loadTable();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnClearFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFormActionPerformed
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnClearFormActionPerformed

    private void tblSanPhamCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamCTMouseClicked
        // TODO add your handling code here:
        int row = tblSanPhamCT.getSelectedRow();
        this.showData(row);
    }//GEN-LAST:event_tblSanPhamCTMouseClicked

    private void btnThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuongHieuActionPerformed
        // TODO add your handling code here:
        new ThuongHieuView(frame, true).setVisible(true);
    }//GEN-LAST:event_btnThuongHieuActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String timKiem = txtMaSearch.getText().toLowerCase(); // Chuyển đổi thành chữ thường
        if (timKiem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu cần tìm");
            return;
        }
        ArrayList<SanPhamCT> ds = spctRepo.findSPCT_ByIdSp(id_sp);
        DefaultTableModel dtm = (DefaultTableModel) tblSanPhamCT.getModel();
        dtm.setRowCount(0);
        int check = 0;
        for (SanPhamCT spct : ds) {
            if (spct.getMaSPCT().toLowerCase().contains(timKiem)) {
                check = 1;
                Object[] row = {
                    spct.getMaSPCT(),
                    spct.getM().getTenMau(),
                    spct.getS().getTenSize(),
                    spct.getKd().getTenKD(),
                    spct.getTh().getTenTH(),
                    spct.getDonGia(),
                    spct.getSoLuong(),
                    spct.getTrangThai() == 0 ? "Đang bán" : "Dừng bán"
                };
                dtm.addRow(row);
            }
        }
        if (check == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
            this.loadTable();
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnClearSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSearchActionPerformed
        // TODO add your handling code here:
        txtMaSearch.setText("");
        this.loadTable();
    }//GEN-LAST:event_btnClearSearchActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        if (this.page > 1) {
            this.page--;
            this.lblPage.setText(String.valueOf(this.page));
            this.loadTable();
        }

        // Cập nhật trạng thái nút Back
        this.btnBack.setEnabled(this.page > 1);
        // Cập nhật trạng thái nút Next
        int totalPages = getTotalPages();
        this.btnNext.setEnabled(this.page < totalPages);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        int totalPages = getTotalPages(); // Phương thức để lấy tổng số trang
        if (this.page < totalPages) {
            this.page++;
            this.lblPage.setText(String.valueOf(this.page));
            this.loadTable();
        }
        // Cập nhật trạng thái nút Back
        this.btnBack.setEnabled(this.page > 1);
        // Cập nhật trạng thái nút Next
        this.btnNext.setEnabled(this.page < totalPages);
    }//GEN-LAST:event_btnNextActionPerformed

    private void cboLocMauSacItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLocMauSacItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            selectedMauSac = (String) cboLocMauSac.getSelectedItem();
            if (!"Tất cả".equals(selectedMauSac)) {
                cboLocSize.setEnabled(true);
                this.loadTableSanPhamCT1();
            } else {
                this.loadTable();
                cboLocSize.setEnabled(false);
                return;
            }
        }
    }//GEN-LAST:event_cboLocMauSacItemStateChanged

    private void cboLocSizeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLocSizeItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            selectedSize = (String) cboLocSize.getSelectedItem();
            if (!"Tất cả".equals(selectedSize)) {
                cboLocMauSac.setEnabled(false);
                cboLocThuongHieu.setEnabled(true);
                this.loadTableSanPhamCT2();
            } else {
                this.loadTableSanPhamCT1();
                cboLocMauSac.setEnabled(true);
                cboLocThuongHieu.setEnabled(false);
                return;
            }
        }
    }//GEN-LAST:event_cboLocSizeItemStateChanged

    private void cboLocThuongHieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLocThuongHieuItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            selectedThuongHieu = (String) cboLocThuongHieu.getSelectedItem();
            if (!"Tất cả".equals(selectedThuongHieu)) {
                cboLocSize.setEnabled(false);
                this.loadTableSanPhamCT3();
            } else {
                this.loadTableSanPhamCT2();
                if (cboLocMauSac.isEnabled() == false) {
                    cboLocSize.setEnabled(true);
                }
                return;
            }
        }
    }//GEN-LAST:event_cboLocThuongHieuItemStateChanged

    private void btnMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMauSacActionPerformed
        // TODO add your handling code here:
        new MauSacView(frame, true).setVisible(true);
    }//GEN-LAST:event_btnMauSacActionPerformed

    private void btnMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaActionPerformed
        // TODO add your handling code here:
        this.maAuto();
    }//GEN-LAST:event_btnMaActionPerformed

    private void btnSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSizeActionPerformed
        // TODO add your handling code here:
        new KichThuocView(frame, true).setVisible(true);
    }//GEN-LAST:event_btnSizeActionPerformed

    private void cboLocKieuDangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLocKieuDangItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            selectedKieuDang = (String) cboLocKieuDang.getSelectedItem();
            if (selectedKieuDang != "Tất cả") {
                cboLocThuongHieu.setEnabled(false);
                this.loadTableSanPhamCT4();
            } else {
                this.loadTableSanPhamCT3();
                if (cboLocSize.isEnabled() == false) {
                    cboLocThuongHieu.setEnabled(true);
                    cboLocSize.setEnabled(true);
                }
                return;
            }
        }
    }//GEN-LAST:event_cboLocKieuDangItemStateChanged

    private void btnKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKieuDangActionPerformed
        // TODO add your handling code here:
        new KieuDangView(frame, true).setVisible(true);
    }//GEN-LAST:event_btnKieuDangActionPerformed

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
            java.util.logging.Logger.getLogger(SanPhamCT.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamCT.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamCT.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamCT.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int defaultIdSp = 1;
                SanPhamCT_View dialog = new SanPhamCT_View(new javax.swing.JFrame(), defaultIdSp);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClearForm;
    private javax.swing.JButton btnClearSearch;
    private javax.swing.JButton btnKieuDang;
    private javax.swing.JButton btnMa;
    private javax.swing.JButton btnMauSac;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSize;
    private javax.swing.JButton btnThuongHieu;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboKieuDang;
    private javax.swing.JComboBox<String> cboLocKieuDang;
    private javax.swing.JComboBox<String> cboLocMauSac;
    private javax.swing.JComboBox<String> cboLocSize;
    private javax.swing.JComboBox<String> cboLocThuongHieu;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JComboBox<String> cboThuongHieu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblPage;
    private javax.swing.JPanel pnSanPham;
    private javax.swing.JRadioButton rdoActive;
    private javax.swing.JRadioButton rdoUnactive;
    private javax.swing.JTable tblSanPhamCT;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaSPCT;
    private javax.swing.JTextField txtMaSearch;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
