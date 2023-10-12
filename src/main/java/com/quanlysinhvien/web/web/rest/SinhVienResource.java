package com.quanlysinhvien.web.web.rest;

import com.quanlysinhvien.web.domain.SinhVien;
import com.quanlysinhvien.web.repository.SinhVienRepository;
import com.quanlysinhvien.web.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.quanlysinhvien.web.domain.SinhVien}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SinhVienResource {

    private final Logger log = LoggerFactory.getLogger(SinhVienResource.class);

    private static final String ENTITY_NAME = "sinhVien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SinhVienRepository sinhVienRepository;

    public SinhVienResource(SinhVienRepository sinhVienRepository) {
        this.sinhVienRepository = sinhVienRepository;
    }

    /**
     * {@code POST  /sinh-viens} : Create a new sinhVien.
     *
     * @param sinhVien the sinhVien to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sinhVien, or with status {@code 400 (Bad Request)} if the sinhVien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sinh-viens")
    public ResponseEntity<SinhVien> createSinhVien(@Valid @RequestBody SinhVien sinhVien) throws URISyntaxException {
        log.debug("REST request to save SinhVien : {}", sinhVien);
        if (sinhVien.getId() != null) {
            throw new BadRequestAlertException("A new sinhVien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SinhVien result = sinhVienRepository.save(sinhVien);
        return ResponseEntity
            .created(new URI("/api/sinh-viens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sinh-viens/:id} : Updates an existing sinhVien.
     *
     * @param id the id of the sinhVien to save.
     * @param sinhVien the sinhVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sinhVien,
     * or with status {@code 400 (Bad Request)} if the sinhVien is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sinhVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sinh-viens/{id}")
    public ResponseEntity<SinhVien> updateSinhVien(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SinhVien sinhVien
    ) throws URISyntaxException {
        log.debug("REST request to update SinhVien : {}, {}", id, sinhVien);
        if (sinhVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sinhVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sinhVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SinhVien result = sinhVienRepository.save(sinhVien);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sinhVien.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sinh-viens/:id} : Partial updates given fields of an existing sinhVien, field will ignore if it is null
     *
     * @param id the id of the sinhVien to save.
     * @param sinhVien the sinhVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sinhVien,
     * or with status {@code 400 (Bad Request)} if the sinhVien is not valid,
     * or with status {@code 404 (Not Found)} if the sinhVien is not found,
     * or with status {@code 500 (Internal Server Error)} if the sinhVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sinh-viens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SinhVien> partialUpdateSinhVien(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SinhVien sinhVien
    ) throws URISyntaxException {
        log.debug("REST request to partial update SinhVien partially : {}, {}", id, sinhVien);
        if (sinhVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sinhVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sinhVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SinhVien> result = sinhVienRepository
            .findById(sinhVien.getId())
            .map(existingSinhVien -> {
                if (sinhVien.getTen() != null) {
                    existingSinhVien.setTen(sinhVien.getTen());
                }
                if (sinhVien.getEmail() != null) {
                    existingSinhVien.setEmail(sinhVien.getEmail());
                }
                if (sinhVien.getDienThoai() != null) {
                    existingSinhVien.setDienThoai(sinhVien.getDienThoai());
                }

                return existingSinhVien;
            })
            .map(sinhVienRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sinhVien.getId().toString())
        );
    }

    /**
     * {@code GET  /sinh-viens} : get all the sinhViens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sinhViens in body.
     */
    @GetMapping("/sinh-viens")
    public ResponseEntity<List<SinhVien>> getAllSinhViens(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SinhViens");
        Page<SinhVien> page = sinhVienRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sinh-viens/:id} : get the "id" sinhVien.
     *
     * @param id the id of the sinhVien to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sinhVien, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sinh-viens/{id}")
    public ResponseEntity<SinhVien> getSinhVien(@PathVariable Long id) {
        log.debug("REST request to get SinhVien : {}", id);
        Optional<SinhVien> sinhVien = sinhVienRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sinhVien);
    }

    /**
     * {@code DELETE  /sinh-viens/:id} : delete the "id" sinhVien.
     *
     * @param id the id of the sinhVien to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sinh-viens/{id}")
    public ResponseEntity<Void> deleteSinhVien(@PathVariable Long id) {
        log.debug("REST request to delete SinhVien : {}", id);
        sinhVienRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
