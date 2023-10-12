package com.quanlysinhvien.web.web.rest;

import com.quanlysinhvien.web.domain.LopHoc;
import com.quanlysinhvien.web.repository.LopHocRepository;
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
 * REST controller for managing {@link com.quanlysinhvien.web.domain.LopHoc}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LopHocResource {

    private final Logger log = LoggerFactory.getLogger(LopHocResource.class);

    private static final String ENTITY_NAME = "lopHoc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LopHocRepository lopHocRepository;

    public LopHocResource(LopHocRepository lopHocRepository) {
        this.lopHocRepository = lopHocRepository;
    }

    /**
     * {@code POST  /lop-hocs} : Create a new lopHoc.
     *
     * @param lopHoc the lopHoc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lopHoc, or with status {@code 400 (Bad Request)} if the lopHoc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lop-hocs")
    public ResponseEntity<LopHoc> createLopHoc(@Valid @RequestBody LopHoc lopHoc) throws URISyntaxException {
        log.debug("REST request to save LopHoc : {}", lopHoc);
        if (lopHoc.getId() != null) {
            throw new BadRequestAlertException("A new lopHoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LopHoc result = lopHocRepository.save(lopHoc);
        return ResponseEntity
            .created(new URI("/api/lop-hocs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lop-hocs/:id} : Updates an existing lopHoc.
     *
     * @param id the id of the lopHoc to save.
     * @param lopHoc the lopHoc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lopHoc,
     * or with status {@code 400 (Bad Request)} if the lopHoc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lopHoc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lop-hocs/{id}")
    public ResponseEntity<LopHoc> updateLopHoc(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LopHoc lopHoc
    ) throws URISyntaxException {
        log.debug("REST request to update LopHoc : {}, {}", id, lopHoc);
        if (lopHoc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lopHoc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lopHocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LopHoc result = lopHocRepository.save(lopHoc);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lopHoc.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lop-hocs/:id} : Partial updates given fields of an existing lopHoc, field will ignore if it is null
     *
     * @param id the id of the lopHoc to save.
     * @param lopHoc the lopHoc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lopHoc,
     * or with status {@code 400 (Bad Request)} if the lopHoc is not valid,
     * or with status {@code 404 (Not Found)} if the lopHoc is not found,
     * or with status {@code 500 (Internal Server Error)} if the lopHoc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lop-hocs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LopHoc> partialUpdateLopHoc(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LopHoc lopHoc
    ) throws URISyntaxException {
        log.debug("REST request to partial update LopHoc partially : {}, {}", id, lopHoc);
        if (lopHoc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lopHoc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lopHocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LopHoc> result = lopHocRepository
            .findById(lopHoc.getId())
            .map(existingLopHoc -> {
                if (lopHoc.getKiHoc() != null) {
                    existingLopHoc.setKiHoc(lopHoc.getKiHoc());
                }
                if (lopHoc.getNgayHocTrongTuan() != null) {
                    existingLopHoc.setNgayHocTrongTuan(lopHoc.getNgayHocTrongTuan());
                }
                if (lopHoc.getThoiGianBatDau() != null) {
                    existingLopHoc.setThoiGianBatDau(lopHoc.getThoiGianBatDau());
                }
                if (lopHoc.getThoiGianKetThuc() != null) {
                    existingLopHoc.setThoiGianKetThuc(lopHoc.getThoiGianKetThuc());
                }
                if (lopHoc.getPhongHoc() != null) {
                    existingLopHoc.setPhongHoc(lopHoc.getPhongHoc());
                }
                if (lopHoc.getNgayThi() != null) {
                    existingLopHoc.setNgayThi(lopHoc.getNgayThi());
                }
                if (lopHoc.getGioThi() != null) {
                    existingLopHoc.setGioThi(lopHoc.getGioThi());
                }
                if (lopHoc.getPhongThi() != null) {
                    existingLopHoc.setPhongThi(lopHoc.getPhongThi());
                }

                return existingLopHoc;
            })
            .map(lopHocRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lopHoc.getId().toString())
        );
    }

    /**
     * {@code GET  /lop-hocs} : get all the lopHocs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lopHocs in body.
     */
    @GetMapping("/lop-hocs")
    public ResponseEntity<List<LopHoc>> getAllLopHocs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LopHocs");
        Page<LopHoc> page = lopHocRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lop-hocs/:id} : get the "id" lopHoc.
     *
     * @param id the id of the lopHoc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lopHoc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lop-hocs/{id}")
    public ResponseEntity<LopHoc> getLopHoc(@PathVariable Long id) {
        log.debug("REST request to get LopHoc : {}", id);
        Optional<LopHoc> lopHoc = lopHocRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lopHoc);
    }

    /**
     * {@code DELETE  /lop-hocs/:id} : delete the "id" lopHoc.
     *
     * @param id the id of the lopHoc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lop-hocs/{id}")
    public ResponseEntity<Void> deleteLopHoc(@PathVariable Long id) {
        log.debug("REST request to delete LopHoc : {}", id);
        lopHocRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
