package com.quanlysinhvien.web.repository;

import com.quanlysinhvien.web.domain.NganhHoc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NganhHoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NganhHocRepository extends JpaRepository<NganhHoc, Long> {}
