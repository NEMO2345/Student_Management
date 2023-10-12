package com.quanlysinhvien.web.repository;

import com.quanlysinhvien.web.domain.LopDangKi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LopDangKi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LopDangKiRepository extends JpaRepository<LopDangKi, Long> {}
