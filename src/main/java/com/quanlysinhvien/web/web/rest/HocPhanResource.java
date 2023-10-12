package com.quanlysinhvien.web.web.rest;

import com.quanlysinhvien.web.domain.HocPhan;
import com.quanlysinhvien.web.repository.HocPhanRepository;
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
 * REST controller for managing {@link com.quanlysinhvien.web.domain.HocPhan}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HocPhanResource {

    private final Logger log = LoggerFactory.getLogger(HocPhanResource.class);

    private static final String ENTITY_NAME = "hocPhan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HocPhanRepository hocPhanRepository;

    public HocPhanResource(HocPhanRepository hocPhanRepository) {
        this.hocPhanRepository = hocPhanRepository;
    }

    /**
     * {@code POST  /hoc-phans} : Create a new hocPhan.
     *
     * @param hocPhan the hocPhan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hocPhan, or with status {@code 400 (Bad Request)} if the hocPhan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hoc-phans")
    public ResponseEntity<HocPhan> createHocPhan(@Valid @RequestBody HocPhan hocPhan) throws URISyntaxException {
        log.debug("REST request to save HocPhan : {}", hocPhan);
        if (hocPhan.getId() != null) {
            throw new BadRequestAlertException("A new hocPhan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HocPhan result = hocPhanRepository.save(hocPhan);
        return ResponseEntity
            .created(new URI("/api/hoc-phans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hoc-phans/:id} : Updates an existing hocPhan.
     *
     * @param id the id of the hocPhan to save.
     * @param hocPhan the hocPhan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hocPhan,
     * or with status {@code 400 (Bad Request)} if the hocPhan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hocPhan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hoc-phans/{id}")
    public ResponseEntity<HocPhan> updateHocPhan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HocPhan hocPhan
    ) throws URISyntaxException {
        log.debug("REST request to update HocPhan : {}, {}", id, hocPhan);
        if (hocPhan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hocPhan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hocPhanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HocPhan result = hocPhanRepository.save(hocPhan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hocPhan.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hoc-phans/:id} : Partial updates given fields of an existing hocPhan, field will ignore if it is null
     *
     * @param id the id of the hocPhan to save.
     * @param hocPhan the hocPhan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hocPhan,
     * or with status {@code 400 (Bad Request)} if the hocPhan is not valid,
     * or with status {@code 404 (Not Found)} if the hocPhan is not found,
     * or with status {@code 500 (Internal Server Error)} if the hocPhan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hoc-phans/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HocPhan> partialUpdateHocPhan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HocPhan hocPhan
    ) throws URISyntaxException {
        log.debug("REST request to partial update HocPhan partially : {}, {}", id, hocPhan);
        if (hocPhan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hocPhan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hocPhanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HocPhan> result = hocPhanRepository
            .findById(hocPhan.getId())
            .map(existingHocPhan -> {
                if (hocPhan.getTen() != null) {
                    existingHocPhan.setTen(hocPhan.getTen());
                }
                if (hocPhan.getMoTa() != null) {
                    existingHocPhan.setMoTa(hocPhan.getMoTa());
                }

                return existingHocPhan;
            })
            .map(hocPhanRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hocPhan.getId().toString())
        );
    }

    /**
     * {@code GET  /hoc-phans} : get all the hocPhans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hocPhans in body.
     */
    @GetMapping("/hoc-phans")
    public ResponseEntity<List<HocPhan>> getAllHocPhans(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of HocPhans");
        Page<HocPhan> page = hocPhanRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hoc-phans/:id} : get the "id" hocPhan.
     *
     * @param id the id of the hocPhan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hocPhan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hoc-phans/{id}")
    public ResponseEntity<HocPhan> getHocPhan(@PathVariable Long id) {
        log.debug("REST request to get HocPhan : {}", id);
        Optional<HocPhan> hocPhan = hocPhanRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hocPhan);
    }

    /**
     * {@code DELETE  /hoc-phans/:id} : delete the "id" hocPhan.
     *
     * @param id the id of the hocPhan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hoc-phans/{id}")
    public ResponseEntity<Void> deleteHocPhan(@PathVariable Long id) {
        log.debug("REST request to delete HocPhan : {}", id);
        hocPhanRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
