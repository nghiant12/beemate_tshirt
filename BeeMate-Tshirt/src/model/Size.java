package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author maymimim
 */
public class Size {

    private int id;
    private String maSize;
    private String tenSize;
    private int trangThai;

    public Size() {
    }

    public Size(int id) {
        this.id = id;
    }

    public Size(int id, String maSize, String tenSize, int trangThai) {
        this.id = id;
        this.maSize = maSize;
        this.tenSize = tenSize;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSize() {
        return maSize;
    }

    public void setMaSize(String maSize) {
        this.maSize = maSize;
    }

    public String getTenSize() {
        return tenSize;
    }

    public void setTenSize(String tenSize) {
        this.tenSize = tenSize;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "Size{" + "id=" + id + ", maSize=" + maSize + ", tenSize=" + tenSize + ", trangThai=" + trangThai + '}';
    }

}
