package com.quanlysinhvien.web.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A GiaoVien.
 */
@Entity
@Table(name = "giao_vien")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GiaoVien implements Serializable {

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
    @Column(name = "sdt", length = 11, nullable = false)
    private String sdt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GiaoVien id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return this.ten;
    }

    public GiaoVien ten(String ten) {
        this.setTen(ten);
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return this.email;
    }

    public GiaoVien email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return this.sdt;
    }

    public GiaoVien sdt(String sdt) {
        this.setSdt(sdt);
        return this;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiaoVien user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GiaoVien)) {
            return false;
        }
        return id != null && id.equals(((GiaoVien) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GiaoVien{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", email='" + getEmail() + "'" +
            ", sdt='" + getSdt() + "'" +
            "}";
    }
}
