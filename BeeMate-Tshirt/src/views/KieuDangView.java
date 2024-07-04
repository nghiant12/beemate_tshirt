/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.KieuDang;
import repo.KieuDangRepo;

/**
 *
 * @author USER
 */
public class KieuDangView extends javax.swing.JDialog {

    /**
     * Creates new form NewJDialog
     */
    private KieuDangRepo kRepo;

    public KieuDangView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setTitle("Kiểu dáng");
        this.setLocationRelativeTo(null);

        this.kRepo = new KieuDangRepo();

        this.maAuto();

        this.loadTable();
    }

    private void loadTable() {
        ArrayList<KieuDang> ds = this.kRepo.findAllKd();
        DefaultTableModel dtm = (DefaultTableModel) this.tblKieuDang.getModel();
        dtm.setRowCount(0);
        for (KieuDang k : ds) {
            Object[] row = {
                k.getMaKD(),
                k.getTenKD(),
                k.getMoTa(),
                k.getTrangThai() == 0 ? "Đang hoạt động" : "Ngừng hoạt động"
            };
            dtm.addRow(row);
        }
    }

    private void clearForm() {
        maAuto();
        txtTenKD.setText("");
        txtMoTa.setText("");
        rdoActive.setSelected(true);
        tblKieuDang.clearSelection();
    }

    private void showData(int row) {
        txtMaKD.setText(tblKieuDang.getValueAt(row, 0).toString());
        txtTenKD.setText(tblKieuDang.getValueAt(row, 1).toString());
        txtMoTa.setText(tblKieuDang.getValueAt(row, 2).toString());
        if (tblKieuDang.getValueAt(row, 3).toString().equals("Đang hoạt động")) {
            rdoActive.setSelected(true);
        } else {
            rdoUnactive.setSelected(true);
        }
    }

    private void maAuto() {
        ArrayList<KieuDang> ds = this.kRepo.findAllKd();
        if (ds.isEmpty()) {
            txtMaKD.setText("KD001");
        } else {
            int viTriCuoi = ds.size() - 1;

            KieuDang k = ds.get(viTriCuoi);
            String ma = k.getMaKD();
            int so = Integer.parseInt(ma.substring(2));
            so++;
            String maTHM = "KD" + String.format("%03d", so);
            txtMaKD.setText(maTHM);
        }
    }

    //lấy dữ liệu từ form
    private KieuDang getFormData() {
        String ma = txtMaKD.getText().trim();
        String ten = txtTenKD.getText().trim();
        String mt = txtMoTa.getText().trim();
        int trangThai;
        if (rdoActive.isSelected()) {
            trangThai = 0;
        } else {
            trangThai = 1;
        }
        KieuDang k = new KieuDang(-1, ma, ten, mt, trangThai);
        return k;
    }

    //check form
    private boolean check() {
        if (txtMaKD.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã KD không được để trống");
            txtMaKD.requestFocus();
            return false;
        }
        if (txtTenKD.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên KD không được để trống");
            txtTenKD.requestFocus();
            return false;
        }
        if (txtMoTa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mô tả KD không được để trống");
            txtMoTa.requestFocus();
            return false;
        }
        return true;
    }

    //thêm sp
    private void insert() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thêm KD này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        if (check()) {
            if (this.kRepo.checkTrungMa(txtMaKD.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Mã KD đã tồn tại");
                return;
            }
            KieuDang k = getFormData();
            this.kRepo.create(k);
            this.clearForm();
            this.loadTable();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công");
        }
    }

    //cập nhật sp
    private void edit() {
        int row = tblKieuDang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng dữ liệu cần sửa");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa KD này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        if (check()) {
            KieuDang s = getFormData();
            this.kRepo.update(s, tblKieuDang.getValueAt(row, 0).toString());
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

        buttonGroupTrangThai = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaKD = new javax.swing.JTextField();
        txtTenKD = new javax.swing.JTextField();
        btnAutoMaTH = new javax.swing.JButton();
        rdoActive = new javax.swing.JRadioButton();
        rdoUnactive = new javax.swing.JRadioButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClearForm = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtMoTa = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKieuDang = new javax.swing.JTable();
        cbbTrangThai = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Form Nhập Thông tin Kiểu dáng"));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mã KD");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Trạng Thái");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tên KD");

        txtMaKD.setEditable(false);

        btnAutoMaTH.setText("Auto");
        btnAutoMaTH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAutoMaTHMouseClicked(evt);
            }
        });

        buttonGroupTrangThai.add(rdoActive);
        rdoActive.setSelected(true);
        rdoActive.setText("Đang hoạt động");

        buttonGroupTrangThai.add(rdoUnactive);
        rdoUnactive.setText("Ngừng hoạt động");

        btnAdd.setText("Thêm");
        btnAdd.setPreferredSize(new java.awt.Dimension(72, 35));
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });

        btnUpdate.setText("Cập Nhật");
        btnUpdate.setPreferredSize(new java.awt.Dimension(72, 35));
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });

        btnClearForm.setText("Clear Form");
        btnClearForm.setPreferredSize(new java.awt.Dimension(72, 35));
        btnClearForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearFormMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Mô tả");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(rdoActive, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdoUnactive, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtMaKD, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAutoMaTH, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTenKD, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearForm, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(117, 117, 117))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaKD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAutoMaTH))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTenKD))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearForm, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoActive, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoUnactive, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(21, 21, 21))))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAutoMaTH, txtMaKD, txtMoTa, txtTenKD});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3});

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách Kiểu dáng"));

        tblKieuDang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên kiểu dáng", "Mô tả", "Trạng Thái"
            }
        ));
        tblKieuDang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKieuDangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKieuDang);

        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đang hoạt động", "Ngừng hoạt động" }));
        cbbTrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbTrangThaiItemStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Trạng thái");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 661, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAutoMaTHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAutoMaTHMouseClicked
        // TODO add your handling code here:
        maAuto();
    }//GEN-LAST:event_btnAutoMaTHMouseClicked

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        // TODO add your handling code here:
        this.insert();
    }//GEN-LAST:event_btnAddMouseClicked

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
        // TODO add your handling code here:
        this.edit();
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnClearFormMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearFormMouseClicked
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnClearFormMouseClicked

    private void tblKieuDangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKieuDangMouseClicked
        // TODO add your handling code here:
        int row = tblKieuDang.getSelectedRow();
        this.showData(row);
    }//GEN-LAST:event_tblKieuDangMouseClicked

    private void cbbTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbTrangThaiItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String trangThai = (String) cbbTrangThai.getSelectedItem();
            if (trangThai.equals("Tất cả")) {
                ArrayList<KieuDang> ds = this.kRepo.findAllKd();
                DefaultTableModel dtm = (DefaultTableModel) this.tblKieuDang.getModel();
                dtm.setRowCount(0);
                for (KieuDang k : ds) {
                    Object[] row = {
                        k.getMaKD(),
                        k.getTenKD(),
                        k.getMoTa(),
                        k.getTrangThai() == 0 ? "Đang hoạt động" : "Ngừng hoạt động"
                    };
                    dtm.addRow(row);
                }
            } else if (trangThai.equals("Đang hoạt động")) {
                ArrayList<KieuDang> ds = this.kRepo.findAll();
                DefaultTableModel dtm = (DefaultTableModel) this.tblKieuDang.getModel();
                dtm.setRowCount(0);
                for (KieuDang k : ds) {
                    Object[] row = {
                        k.getMaKD(),
                        k.getTenKD(),
                        k.getMoTa(),
                        k.getTrangThai() == 0 ? "Đang hoạt động" : "Ngừng hoạt động"
                    };
                    dtm.addRow(row);
                }
            } else {
                ArrayList<KieuDang> ds = this.kRepo.findAll_Unactive();
                DefaultTableModel dtm = (DefaultTableModel) this.tblKieuDang.getModel();
                dtm.setRowCount(0);
                for (KieuDang k : ds) {
                    Object[] row = {
                        k.getMaKD(),
                        k.getTenKD(),
                        k.getMoTa(),
                        k.getTrangThai() == 0 ? "Đang hoạt động" : "Ngừng hoạt động"
                    };
                    dtm.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_cbbTrangThaiItemStateChanged

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
            java.util.logging.Logger.getLogger(KieuDangView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KieuDangView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KieuDangView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KieuDangView.class
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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KieuDangView dialog = new KieuDangView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAutoMaTH;
    private javax.swing.JButton btnClearForm;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroupTrangThai;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoActive;
    private javax.swing.JRadioButton rdoUnactive;
    private javax.swing.JTable tblKieuDang;
    private javax.swing.JTextField txtMaKD;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtTenKD;
    // End of variables declaration//GEN-END:variables
}
