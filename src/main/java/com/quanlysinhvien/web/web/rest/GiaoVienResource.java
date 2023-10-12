package com.quanlysinhvien.web.web.rest;

import com.quanlysinhvien.web.domain.GiaoVien;
import com.quanlysinhvien.web.repository.GiaoVienRepository;
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
 * REST controller for managing {@link com.quanlysinhvien.web.domain.GiaoVien}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GiaoVienResource {

    private final Logger log = LoggerFactory.getLogger(GiaoVienResource.class);

    private static final String ENTITY_NAME = "giaoVien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GiaoVienRepository giaoVienRepository;

    public GiaoVienResource(GiaoVienRepository giaoVienRepository) {
        this.giaoVienRepository = giaoVienRepository;
    }

    /**
     * {@code POST  /giao-viens} : Create a new giaoVien.
     *
     * @param giaoVien the giaoVien to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new giaoVien, or with status {@code 400 (Bad Request)} if the giaoVien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/giao-viens")
    public ResponseEntity<GiaoVien> createGiaoVien(@Valid @RequestBody GiaoVien giaoVien) throws URISyntaxException {
        log.debug("REST request to save GiaoVien : {}", giaoVien);
        if (giaoVien.getId() != null) {
            throw new BadRequestAlertException("A new giaoVien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GiaoVien result = giaoVienRepository.save(giaoVien);
        return ResponseEntity
            .created(new URI("/api/giao-viens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /giao-viens/:id} : Updates an existing giaoVien.
     *
     * @param id the id of the giaoVien to save.
     * @param giaoVien the giaoVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated giaoVien,
     * or with status {@code 400 (Bad Request)} if the giaoVien is not valid,
     * or with status {@code 500 (Internal Server Error)} if the giaoVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/giao-viens/{id}")
    public ResponseEntity<GiaoVien> updateGiaoVien(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GiaoVien giaoVien
    ) throws URISyntaxException {
        log.debug("REST request to update GiaoVien : {}, {}", id, giaoVien);
        if (giaoVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, giaoVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!giaoVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GiaoVien result = giaoVienRepository.save(giaoVien);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, giaoVien.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /giao-viens/:id} : Partial updates given fields of an existing giaoVien, field will ignore if it is null
     *
     * @param id the id of the giaoVien to save.
     * @param giaoVien the giaoVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated giaoVien,
     * or with status {@code 400 (Bad Request)} if the giaoVien is not valid,
     * or with status {@code 404 (Not Found)} if the giaoVien is not found,
     * or with status {@code 500 (Internal Server Error)} if the giaoVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/giao-viens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GiaoVien> partialUpdateGiaoVien(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GiaoVien giaoVien
    ) throws URISyntaxException {
        log.debug("REST request to partial update GiaoVien partially : {}, {}", id, giaoVien);
        if (giaoVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, giaoVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!giaoVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GiaoVien> result = giaoVienRepository
            .findById(giaoVien.getId())
            .map(existingGiaoVien -> {
                if (giaoVien.getTen() != null) {
                    existingGiaoVien.setTen(giaoVien.getTen());
                }
                if (giaoVien.getEmail() != null) {
                    existingGiaoVien.setEmail(giaoVien.getEmail());
                }
                if (giaoVien.getSdt() != null) {
                    existingGiaoVien.setSdt(giaoVien.getSdt());
                }

                return existingGiaoVien;
            })
            .map(giaoVienRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, giaoVien.getId().toString())
        );
    }

    /**
     * {@code GET  /giao-viens} : get all the giaoViens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of giaoViens in body.
     */
    @GetMapping("/giao-viens")
    public ResponseEntity<List<GiaoVien>> getAllGiaoViens(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of GiaoViens");
        Page<GiaoVien> page = giaoVienRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /giao-viens/:id} : get the "id" giaoVien.
     *
     * @param id the id of the giaoVien to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the giaoVien, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/giao-viens/{id}")
    public ResponseEntity<GiaoVien> getGiaoVien(@PathVariable Long id) {
        log.debug("REST request to get GiaoVien : {}", id);
        Optional<GiaoVien> giaoVien = giaoVienRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(giaoVien);
    }

    /**
     * {@code DELETE  /giao-viens/:id} : delete the "id" giaoVien.
     *
     * @param id the id of the giaoVien to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/giao-viens/{id}")
    public ResponseEntity<Void> deleteGiaoVien(@PathVariable Long id) {
        log.debug("REST request to delete GiaoVien : {}", id);
        giaoVienRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
