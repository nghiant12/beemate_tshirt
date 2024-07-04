/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo;

import model.NhanVien;

/**
 *
 * @author Admin
 */
public class DangNhapRepo {

    private static NhanVien currentUser;

    public static void setCurrentUser(NhanVien user) {
        currentUser = user;
    }

    public static NhanVien getCurrentUser() {
        return currentUser;
    }

    public static String getMaNhanVien() {
        return currentUser != null ? currentUser.getTenTaiKhoan() : null;
    }

    public static int getVaiTro() {
        return currentUser != null ? currentUser.getVaiTro() : 1;
    }

}
