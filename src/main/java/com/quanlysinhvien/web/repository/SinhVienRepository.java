package com.quanlysinhvien.web.repository;

import com.quanlysinhvien.web.domain.SinhVien;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SinhVien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, Long> {}
