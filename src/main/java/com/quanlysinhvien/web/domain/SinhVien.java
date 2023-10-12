package com.quanlysinhvien.web.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A SinhVien.
 */
@Entity
@Table(name = "sinh_vien")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SinhVien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "ten", length = 255, nullable = false)
    private String ten;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @NotNull
    @Size(min = 10, max = 11)
    @Column(name = "dien_thoai", length = 11, nullable = false)
    private String dienThoai;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private NganhHoc nganhHoc;

    @ManyToOne(fetch = FetchType.LAZY)
    private NienKhoa nienKhoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SinhVien id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return this.ten;
    }

    public SinhVien ten(String ten) {
        this.setTen(ten);
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return this.email;
    }

    public SinhVien email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDienThoai() {
        return this.dienThoai;
    }

    public SinhVien dienThoai(String dienThoai) {
        this.setDienThoai(dienThoai);
        return this;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SinhVien user(User user) {
        this.setUser(user);
        return this;
    }

    public NganhHoc getNganhHoc() {
        return this.nganhHoc;
    }

    public void setNganhHoc(NganhHoc nganhHoc) {
        this.nganhHoc = nganhHoc;
    }

    public SinhVien nganhHoc(NganhHoc nganhHoc) {
        this.setNganhHoc(nganhHoc);
        return this;
    }

    public NienKhoa getNienKhoa() {
        return this.nienKhoa;
    }

    public void setNienKhoa(NienKhoa nienKhoa) {
        this.nienKhoa = nienKhoa;
    }

    public SinhVien nienKhoa(NienKhoa nienKhoa) {
        this.setNienKhoa(nienKhoa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SinhVien)) {
            return false;
        }
        return id != null && id.equals(((SinhVien) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SinhVien{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", email='" + getEmail() + "'" +
            ", dienThoai='" + getDienThoai() + "'" +
            "}";
    }
}
