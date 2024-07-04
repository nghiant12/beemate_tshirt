/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

//import java.util.List;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.SanPham;
import repo.SanPhamRepo;

/**
 *
 * @author admin
 */
public class SanPhamView extends javax.swing.JFrame {

    /**
     * Creates new form MainJFrame
     */
    private SanPhamRepo spRepo;
    private int page = 1;
    private int limit = 5;

    public SanPhamView() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.spRepo = new SanPhamRepo();
        this.lblPage.setText(this.page + "");
        this.maAuto();
        this.loadTable();
    }

    private void loadTable() {
        String keyword = this.txtTimKiem.getText().trim();
        int trangThai = this.cboTrangThai.getSelectedIndex() == 0 ? 0 : 1;

        ArrayList<SanPham> ds = this.spRepo.searchAndPaging(keyword, trangThai, this.page, this.limit);
        DefaultTableModel dtm = (DefaultTableModel) this.tblSanPham.getModel();
        dtm.setRowCount(0);
        for (SanPham s : ds) {
            Object[] row = {
                s.getId(),
                s.getMaSP(),
                s.getTenSP(),
                s.getXuatXu(),
                s.getTrangThai() == 0 ? "Đang bán" : "Dừng bán"
            };
            dtm.addRow(row);
        }
    }

    private void maAuto() {
        ArrayList<SanPham> ds = spRepo.findAll();
        if (ds.size() == 0) {
            txtMaSP.setText("SP001");
        } else {
            int viTriCuoi = ds.size() - 1;
            SanPham s = ds.get(viTriCuoi);
            String ma = s.getMaSP();
            int so = Integer.valueOf(ma.substring(2));
            so++;
            String id = "SP0" + so;
            txtMaSP.setText(id);
        }

    }

    private int getTotalPages() {
        int totalItems = this.spRepo.getTotalItems();
        return (int) Math.ceil((double) totalItems / limit);
    }

    //hiển thị dữ liệu lên form
    private void showData(int row) {
        txtMaSP.setText(tblSanPham.getValueAt(row, 1).toString());
        txtTenSP.setText(tblSanPham.getValueAt(row, 2).toString());
        txtXuatXu.setText(tblSanPham.getValueAt(row, 3).toString());
        cboTrangThai.setSelectedItem(tblSanPham.getValueAt(row, 4).toString());
    }

    //làm mới form
    private void clearForm() {
        txtMaSP.setText(null);
        txtTenSP.setText(null);
        txtXuatXu.setText(null);
        cboTrangThai.setSelectedIndex(0);
    }

    //lấy dữ liệu từ form
    private SanPham getFormData() {
        String ma = txtMaSP.getText().trim();
        String ten = txtTenSP.getText().trim();
        String xuatXu = txtXuatXu.getText().trim();
        int trangThai;
        if (this.cboTrangThai.getSelectedIndex() == 0) {
            trangThai = 0;
        } else {
            trangThai = 1;
        }
        SanPham s = new SanPham(-1, ma, ten, xuatXu, trangThai);
        return s;
    }

    //check form
    private boolean check() {
        if (txtMaSP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm không được để trống");
            txtMaSP.requestFocus();
            return false;
        }
        if (txtTenSP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống");
            txtTenSP.requestFocus();
            return false;
        }
        if (txtXuatXu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Xuất xứ sản phẩm không được để trống");
            txtXuatXu.requestFocus();
            return false;
        }
        return true;
    }

    //thêm sp
    private void insert() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thêm sản phẩm này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_NO_OPTION) {
            return;
        }
        if (check()) {
            try {
                if (this.spRepo.checkTrungMaSP(txtMaSP.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại");
                    return;
                }
                SanPham s = getFormData();
                if (this.spRepo.checkTrungSanPham(s.getTenSP(), s.getXuatXu())) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm đã tồn tại");
                    return;
                }
                this.spRepo.create(s);
                this.clearForm();
                this.loadTable();
                JOptionPane.showMessageDialog(this, "Thêm mới thành công");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm");
                e.printStackTrace();
            }
        }
    }

    //cập nhật sp
    private void edit() {
        int row = tblSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng dữ liệu cần sửa");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa sản phẩm này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        if (check()) {
            SanPham s = getFormData();
            this.spRepo.update(s, Integer.parseInt(tblSanPham.getValueAt(row, 0).toString()));
            this.clearForm();
            this.loadTable();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnSanPham = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtXuatXu = new javax.swing.JTextField();
        btnThemSP = new javax.swing.JButton();
        btnChinhSuaSP = new javax.swing.JButton();
        btnClearFormSP = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        btnMa = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnLamMoiTimKiem = new javax.swing.JButton();
        btnXemChiTietSP = new javax.swing.JButton();
        cboLocTrangThai = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnSanPham.setBackground(new java.awt.Color(236, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Tùy chỉnh sản phẩm"));

        jLabel9.setText("Mã Sản phẩm");

        jLabel10.setText("Tên Sản phẩm");

        txtMaSP.setEditable(false);

        jLabel11.setText("Xuất xứ");

        btnThemSP.setText("Thêm sản phẩm");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnChinhSuaSP.setText("Chỉnh sửa sản phẩm");
        btnChinhSuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChinhSuaSPActionPerformed(evt);
            }
        });

        btnClearFormSP.setText("Làm mới");
        btnClearFormSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFormSPActionPerformed(evt);
            }
        });

        jLabel12.setText("Trạng thái");

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang bán", "Dừng bán" }));

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
                .addGap(37, 37, 37)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnChinhSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnClearFormSP, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(61, 61, 61))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(txtXuatXu)
                    .addComponent(btnMa, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSP)
                    .addComponent(btnChinhSuaSP)
                    .addComponent(btnClearFormSP))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtMaSP, txtTenSP, txtXuatXu});

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id_SP", "Mã sản phẩm", "Tên sản phẩm", "Xuất xứ", "Trạng thái"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblSanPham);

        jLabel20.setText("Mã / tên sản phẩm");

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnLamMoiTimKiem.setText("Làm mới");
        btnLamMoiTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiTimKiemActionPerformed(evt);
            }
        });

        btnXemChiTietSP.setBackground(new java.awt.Color(204, 204, 255));
        btnXemChiTietSP.setText("Xem chi tiết");
        btnXemChiTietSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemChiTietSPActionPerformed(evt);
            }
        });

        cboLocTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang bán", "Dừng bán" }));
        cboLocTrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLocTrangThaiItemStateChanged(evt);
            }
        });

        jLabel2.setText("Trạng thái");

        btnBack.setText("<<");
        btnBack.setEnabled(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        lblPage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPage.setText("0");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLamMoiTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(btnXemChiTietSP, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 37, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboLocTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblPage, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jScrollPane5)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem)
                    .addComponent(btnLamMoiTimKiem)
                    .addComponent(btnXemChiTietSP))
                .addGap(19, 19, 19)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboLocTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnBack)
                    .addComponent(btnNext)
                    .addComponent(lblPage))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ SẢN PHẨM");

        javax.swing.GroupLayout pnSanPhamLayout = new javax.swing.GroupLayout(pnSanPham);
        pnSanPham.setLayout(pnSanPhamLayout);
        pnSanPhamLayout.setHorizontalGroup(
            pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnSanPhamLayout.createSequentialGroup()
                        .addGap(263, 263, 263)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnSanPhamLayout.setVerticalGroup(
            pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXemChiTietSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemChiTietSPActionPerformed
        // TODO add your handling code here:
        try {
            int row = tblSanPham.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm muốn xem chi tiết");
                return;
            }
            int id_sp = Integer.parseInt(tblSanPham.getValueAt(row, 0).toString());
            SanPhamCT_View dialog = new SanPhamCT_View(this, id_sp);
            dialog.setVisible(true);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Lỗi xem chi tiết");
        }
    }//GEN-LAST:event_btnXemChiTietSPActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        try {
            int row = tblSanPham.getSelectedRow();
            this.showData(row);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnClearFormSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFormSPActionPerformed
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnClearFormSPActionPerformed

    private void btnChinhSuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChinhSuaSPActionPerformed
        // TODO add your handling code here:
        this.edit();
    }//GEN-LAST:event_btnChinhSuaSPActionPerformed

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
        this.insert();
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnLamMoiTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiTimKiemActionPerformed
        // TODO add your handling code here:
        txtTimKiem.setText("");
        this.loadTable();
    }//GEN-LAST:event_btnLamMoiTimKiemActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        String timKiem = txtTimKiem.getText().toLowerCase(); // Chuyển đổi thành chữ thường
        if (timKiem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu cần tìm");
            return;
        }
        ArrayList<SanPham> ds = spRepo.findAll();
        DefaultTableModel dtm = (DefaultTableModel) tblSanPham.getModel();
        dtm.setRowCount(0);
        int check = 0;
        for (SanPham s : ds) {
            if (s.getMaSP().toLowerCase().contains(timKiem) || s.getTenSP().toLowerCase().contains(timKiem)) {
                check = 1;
                Object[] row = {
                    s.getId(),
                    s.getMaSP(),
                    s.getTenSP(),
                    s.getXuatXu(),
                    s.getTrangThai() == 0 ? "Đang bán" : "Dừng bán"
                };
                dtm.addRow(row);
            }
        }
        if (check == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
            loadTable();
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void cboLocTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLocTrangThaiItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            int selectedSanPham = cboLocTrangThai.getSelectedIndex();
            if (selectedSanPham == 0) {
                cboLocTrangThai.setEnabled(true);
                ArrayList<SanPham> ds = spRepo.findAllSP_DangBan();
                DefaultTableModel dtm = (DefaultTableModel) tblSanPham.getModel();
                dtm.setRowCount(0);
                for (SanPham s : ds) {
                    if (s.getTrangThai() == selectedSanPham) {
                        Object[] row = {
                            s.getId(),
                            s.getMaSP(),
                            s.getTenSP(),
                            s.getXuatXu(),
                            s.getTrangThai() == 0 ? "Đang bán" : "Dừng bán"
                        };
                        dtm.addRow(row);
                    }
                }
            } else if (selectedSanPham == 1) {
                cboLocTrangThai.setEnabled(true);
                ArrayList<SanPham> ds = spRepo.findAllSP_DungBan();
                DefaultTableModel dtm = (DefaultTableModel) tblSanPham.getModel();
                dtm.setRowCount(0);
                for (SanPham s : ds) {
                    if (s.getTrangThai() == selectedSanPham) {
                        Object[] row = {
                            s.getId(),
                            s.getMaSP(),
                            s.getTenSP(),
                            s.getXuatXu(),
                            s.getTrangThai() == 0 ? "Đang bán" : "Dừng bán"
                        };
                        dtm.addRow(row);
                    }
                }
            }
        }
    }//GEN-LAST:event_cboLocTrangThaiItemStateChanged

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

    private void btnMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaActionPerformed
        // TODO add your handling code here:
        this.maAuto();
    }//GEN-LAST:event_btnMaActionPerformed

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
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new SanPhamView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnChinhSuaSP;
    private javax.swing.JButton btnClearFormSP;
    private javax.swing.JButton btnLamMoiTimKiem;
    private javax.swing.JButton btnMa;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXemChiTietSP;
    private javax.swing.JComboBox<String> cboLocTrangThai;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblPage;
    private javax.swing.JPanel pnSanPham;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtXuatXu;
    // End of variables declaration//GEN-END:variables

}
