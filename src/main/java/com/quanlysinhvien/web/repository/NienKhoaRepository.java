package com.quanlysinhvien.web.repository;

import com.quanlysinhvien.web.domain.NienKhoa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NienKhoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NienKhoaRepository extends JpaRepository<NienKhoa, Long> {}
