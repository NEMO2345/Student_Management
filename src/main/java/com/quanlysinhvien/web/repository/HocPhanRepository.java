package com.quanlysinhvien.web.repository;

import com.quanlysinhvien.web.domain.HocPhan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HocPhan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HocPhanRepository extends JpaRepository<HocPhan, Long> {}
