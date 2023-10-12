package com.quanlysinhvien.web.web.rest;

import com.quanlysinhvien.web.domain.NganhHoc;
import com.quanlysinhvien.web.repository.NganhHocRepository;
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
 * REST controller for managing {@link com.quanlysinhvien.web.domain.NganhHoc}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NganhHocResource {

    private final Logger log = LoggerFactory.getLogger(NganhHocResource.class);

    private static final String ENTITY_NAME = "nganhHoc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NganhHocRepository nganhHocRepository;

    public NganhHocResource(NganhHocRepository nganhHocRepository) {
        this.nganhHocRepository = nganhHocRepository;
    }

    /**
     * {@code POST  /nganh-hocs} : Create a new nganhHoc.
     *
     * @param nganhHoc the nganhHoc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nganhHoc, or with status {@code 400 (Bad Request)} if the nganhHoc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nganh-hocs")
    public ResponseEntity<NganhHoc> createNganhHoc(@Valid @RequestBody NganhHoc nganhHoc) throws URISyntaxException {
        log.debug("REST request to save NganhHoc : {}", nganhHoc);
        if (nganhHoc.getId() != null) {
            throw new BadRequestAlertException("A new nganhHoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NganhHoc result = nganhHocRepository.save(nganhHoc);
        return ResponseEntity
            .created(new URI("/api/nganh-hocs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nganh-hocs/:id} : Updates an existing nganhHoc.
     *
     * @param id the id of the nganhHoc to save.
     * @param nganhHoc the nganhHoc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nganhHoc,
     * or with status {@code 400 (Bad Request)} if the nganhHoc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nganhHoc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nganh-hocs/{id}")
    public ResponseEntity<NganhHoc> updateNganhHoc(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NganhHoc nganhHoc
    ) throws URISyntaxException {
        log.debug("REST request to update NganhHoc : {}, {}", id, nganhHoc);
        if (nganhHoc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nganhHoc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nganhHocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NganhHoc result = nganhHocRepository.save(nganhHoc);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nganhHoc.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nganh-hocs/:id} : Partial updates given fields of an existing nganhHoc, field will ignore if it is null
     *
     * @param id the id of the nganhHoc to save.
     * @param nganhHoc the nganhHoc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nganhHoc,
     * or with status {@code 400 (Bad Request)} if the nganhHoc is not valid,
     * or with status {@code 404 (Not Found)} if the nganhHoc is not found,
     * or with status {@code 500 (Internal Server Error)} if the nganhHoc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nganh-hocs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NganhHoc> partialUpdateNganhHoc(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NganhHoc nganhHoc
    ) throws URISyntaxException {
        log.debug("REST request to partial update NganhHoc partially : {}, {}", id, nganhHoc);
        if (nganhHoc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nganhHoc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nganhHocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NganhHoc> result = nganhHocRepository
            .findById(nganhHoc.getId())
            .map(existingNganhHoc -> {
                if (nganhHoc.getTen() != null) {
                    existingNganhHoc.setTen(nganhHoc.getTen());
                }

                return existingNganhHoc;
            })
            .map(nganhHocRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nganhHoc.getId().toString())
        );
    }

    /**
     * {@code GET  /nganh-hocs} : get all the nganhHocs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nganhHocs in body.
     */
    @GetMapping("/nganh-hocs")
    public ResponseEntity<List<NganhHoc>> getAllNganhHocs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NganhHocs");
        Page<NganhHoc> page = nganhHocRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nganh-hocs/:id} : get the "id" nganhHoc.
     *
     * @param id the id of the nganhHoc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nganhHoc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nganh-hocs/{id}")
    public ResponseEntity<NganhHoc> getNganhHoc(@PathVariable Long id) {
        log.debug("REST request to get NganhHoc : {}", id);
        Optional<NganhHoc> nganhHoc = nganhHocRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nganhHoc);
    }

    /**
     * {@code DELETE  /nganh-hocs/:id} : delete the "id" nganhHoc.
     *
     * @param id the id of the nganhHoc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nganh-hocs/{id}")
    public ResponseEntity<Void> deleteNganhHoc(@PathVariable Long id) {
        log.debug("REST request to delete NganhHoc : {}", id);
        nganhHocRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
