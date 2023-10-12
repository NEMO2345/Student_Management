package com.quanlysinhvien.web.repository;

import com.quanlysinhvien.web.domain.LopHoc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LopHoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LopHocRepository extends JpaRepository<LopHoc, Long> {}
