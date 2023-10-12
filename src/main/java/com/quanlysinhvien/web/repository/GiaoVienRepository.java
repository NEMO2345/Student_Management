package com.quanlysinhvien.web.repository;

import com.quanlysinhvien.web.domain.GiaoVien;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GiaoVien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GiaoVienRepository extends JpaRepository<GiaoVien, Long> {}
