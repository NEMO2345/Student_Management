package com.quanlysinhvien.web.web.rest;

import com.quanlysinhvien.web.domain.LopDangKi;
import com.quanlysinhvien.web.repository.LopDangKiRepository;
import com.quanlysinhvien.web.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.quanlysinhvien.web.domain.LopDangKi}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LopDangKiResource {

    private final Logger log = LoggerFactory.getLogger(LopDangKiResource.class);

    private static final String ENTITY_NAME = "lopDangKi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LopDangKiRepository lopDangKiRepository;

    public LopDangKiResource(LopDangKiRepository lopDangKiRepository) {
        this.lopDangKiRepository = lopDangKiRepository;
    }

    /**
     * {@code POST  /lop-dang-kis} : Create a new lopDangKi.
     *
     * @param lopDangKi the lopDangKi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lopDangKi, or with status {@code 400 (Bad Request)} if the lopDangKi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lop-dang-kis")
    public ResponseEntity<LopDangKi> createLopDangKi(@RequestBody LopDangKi lopDangKi) throws URISyntaxException {
        log.debug("REST request to save LopDangKi : {}", lopDangKi);
        if (lopDangKi.getId() != null) {
            throw new BadRequestAlertException("A new lopDangKi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LopDangKi result = lopDangKiRepository.save(lopDangKi);
        return ResponseEntity
            .created(new URI("/api/lop-dang-kis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lop-dang-kis/:id} : Updates an existing lopDangKi.
     *
     * @param id the id of the lopDangKi to save.
     * @param lopDangKi the lopDangKi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lopDangKi,
     * or with status {@code 400 (Bad Request)} if the lopDangKi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lopDangKi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lop-dang-kis/{id}")
    public ResponseEntity<LopDangKi> updateLopDangKi(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LopDangKi lopDangKi
    ) throws URISyntaxException {
        log.debug("REST request to update LopDangKi : {}, {}", id, lopDangKi);
        if (lopDangKi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lopDangKi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lopDangKiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LopDangKi result = lopDangKiRepository.save(lopDangKi);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lopDangKi.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lop-dang-kis/:id} : Partial updates given fields of an existing lopDangKi, field will ignore if it is null
     *
     * @param id the id of the lopDangKi to save.
     * @param lopDangKi the lopDangKi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lopDangKi,
     * or with status {@code 400 (Bad Request)} if the lopDangKi is not valid,
     * or with status {@code 404 (Not Found)} if the lopDangKi is not found,
     * or with status {@code 500 (Internal Server Error)} if the lopDangKi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lop-dang-kis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LopDangKi> partialUpdateLopDangKi(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LopDangKi lopDangKi
    ) throws URISyntaxException {
        log.debug("REST request to partial update LopDangKi partially : {}, {}", id, lopDangKi);
        if (lopDangKi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lopDangKi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lopDangKiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LopDangKi> result = lopDangKiRepository
            .findById(lopDangKi.getId())
            .map(existingLopDangKi -> {
                if (lopDangKi.getDiemQuaTrinh() != null) {
                    existingLopDangKi.setDiemQuaTrinh(lopDangKi.getDiemQuaTrinh());
                }
                if (lopDangKi.getDiemThi() != null) {
                    existingLopDangKi.setDiemThi(lopDangKi.getDiemThi());
                }
                if (lopDangKi.getDiemKetThucHocPhan() != null) {
                    existingLopDangKi.setDiemKetThucHocPhan(lopDangKi.getDiemKetThucHocPhan());
                }

                return existingLopDangKi;
            })
            .map(lopDangKiRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lopDangKi.getId().toString())
        );
    }

    /**
     * {@code GET  /lop-dang-kis} : get all the lopDangKis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lopDangKis in body.
     */
    @GetMapping("/lop-dang-kis")
    public ResponseEntity<List<LopDangKi>> getAllLopDangKis(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LopDangKis");
        Page<LopDangKi> page = lopDangKiRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lop-dang-kis/:id} : get the "id" lopDangKi.
     *
     * @param id the id of the lopDangKi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lopDangKi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lop-dang-kis/{id}")
    public ResponseEntity<LopDangKi> getLopDangKi(@PathVariable Long id) {
        log.debug("REST request to get LopDangKi : {}", id);
        Optional<LopDangKi> lopDangKi = lopDangKiRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lopDangKi);
    }

    /**
     * {@code DELETE  /lop-dang-kis/:id} : delete the "id" lopDangKi.
     *
     * @param id the id of the lopDangKi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lop-dang-kis/{id}")
    public ResponseEntity<Void> deleteLopDangKi(@PathVariable Long id) {
        log.debug("REST request to delete LopDangKi : {}", id);
        lopDangKiRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
