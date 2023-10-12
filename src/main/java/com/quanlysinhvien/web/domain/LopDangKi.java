package com.quanlysinhvien.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A LopDangKi.
 */
@Entity
@Table(name = "lop_dang_ki")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LopDangKi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "diem_qua_trinh")
    private Float diemQuaTrinh;

    @Column(name = "diem_thi")
    private Float diemThi;

    @Column(name = "diem_ket_thuc_hoc_phan")
    private Float diemKetThucHocPhan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "nganhHoc", "nienKhoa" }, allowSetters = true)
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "hocPhan", "giaoVien" }, allowSetters = true)
    private LopHoc lopHoc;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LopDangKi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getDiemQuaTrinh() {
        return this.diemQuaTrinh;
    }

    public LopDangKi diemQuaTrinh(Float diemQuaTrinh) {
        this.setDiemQuaTrinh(diemQuaTrinh);
        return this;
    }

    public void setDiemQuaTrinh(Float diemQuaTrinh) {
        this.diemQuaTrinh = diemQuaTrinh;
    }

    public Float getDiemThi() {
        return this.diemThi;
    }

    public LopDangKi diemThi(Float diemThi) {
        this.setDiemThi(diemThi);
        return this;
    }

    public void setDiemThi(Float diemThi) {
        this.diemThi = diemThi;
    }

    public Float getDiemKetThucHocPhan() {
        return this.diemKetThucHocPhan;
    }

    public LopDangKi diemKetThucHocPhan(Float diemKetThucHocPhan) {
        this.setDiemKetThucHocPhan(diemKetThucHocPhan);
        return this;
    }

    public void setDiemKetThucHocPhan(Float diemKetThucHocPhan) {
        this.diemKetThucHocPhan = diemKetThucHocPhan;
    }

    public SinhVien getSinhVien() {
        return this.sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public LopDangKi sinhVien(SinhVien sinhVien) {
        this.setSinhVien(sinhVien);
        return this;
    }

    public LopHoc getLopHoc() {
        return this.lopHoc;
    }

    public void setLopHoc(LopHoc lopHoc) {
        this.lopHoc = lopHoc;
    }

    public LopDangKi lopHoc(LopHoc lopHoc) {
        this.setLopHoc(lopHoc);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LopDangKi)) {
            return false;
        }
        return id != null && id.equals(((LopDangKi) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LopDangKi{" +
            "id=" + getId() +
            ", diemQuaTrinh=" + getDiemQuaTrinh() +
            ", diemThi=" + getDiemThi() +
            ", diemKetThucHocPhan=" + getDiemKetThucHocPhan() +
            "}";
    }
}
