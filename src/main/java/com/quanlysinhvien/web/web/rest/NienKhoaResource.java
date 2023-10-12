package com.quanlysinhvien.web.web.rest;

import com.quanlysinhvien.web.domain.NienKhoa;
import com.quanlysinhvien.web.repository.NienKhoaRepository;
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
 * REST controller for managing {@link com.quanlysinhvien.web.domain.NienKhoa}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NienKhoaResource {

    private final Logger log = LoggerFactory.getLogger(NienKhoaResource.class);

    private static final String ENTITY_NAME = "nienKhoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NienKhoaRepository nienKhoaRepository;

    public NienKhoaResource(NienKhoaRepository nienKhoaRepository) {
        this.nienKhoaRepository = nienKhoaRepository;
    }

    /**
     * {@code POST  /nien-khoas} : Create a new nienKhoa.
     *
     * @param nienKhoa the nienKhoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nienKhoa, or with status {@code 400 (Bad Request)} if the nienKhoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nien-khoas")
    public ResponseEntity<NienKhoa> createNienKhoa(@Valid @RequestBody NienKhoa nienKhoa) throws URISyntaxException {
        log.debug("REST request to save NienKhoa : {}", nienKhoa);
        if (nienKhoa.getId() != null) {
            throw new BadRequestAlertException("A new nienKhoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NienKhoa result = nienKhoaRepository.save(nienKhoa);
        return ResponseEntity
            .created(new URI("/api/nien-khoas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nien-khoas/:id} : Updates an existing nienKhoa.
     *
     * @param id the id of the nienKhoa to save.
     * @param nienKhoa the nienKhoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nienKhoa,
     * or with status {@code 400 (Bad Request)} if the nienKhoa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nienKhoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nien-khoas/{id}")
    public ResponseEntity<NienKhoa> updateNienKhoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NienKhoa nienKhoa
    ) throws URISyntaxException {
        log.debug("REST request to update NienKhoa : {}, {}", id, nienKhoa);
        if (nienKhoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nienKhoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nienKhoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NienKhoa result = nienKhoaRepository.save(nienKhoa);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nienKhoa.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nien-khoas/:id} : Partial updates given fields of an existing nienKhoa, field will ignore if it is null
     *
     * @param id the id of the nienKhoa to save.
     * @param nienKhoa the nienKhoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nienKhoa,
     * or with status {@code 400 (Bad Request)} if the nienKhoa is not valid,
     * or with status {@code 404 (Not Found)} if the nienKhoa is not found,
     * or with status {@code 500 (Internal Server Error)} if the nienKhoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nien-khoas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NienKhoa> partialUpdateNienKhoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NienKhoa nienKhoa
    ) throws URISyntaxException {
        log.debug("REST request to partial update NienKhoa partially : {}, {}", id, nienKhoa);
        if (nienKhoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nienKhoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nienKhoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NienKhoa> result = nienKhoaRepository
            .findById(nienKhoa.getId())
            .map(existingNienKhoa -> {
                if (nienKhoa.getTen() != null) {
                    existingNienKhoa.setTen(nienKhoa.getTen());
                }
                if (nienKhoa.getNamBatDau() != null) {
                    existingNienKhoa.setNamBatDau(nienKhoa.getNamBatDau());
                }

                return existingNienKhoa;
            })
            .map(nienKhoaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nienKhoa.getId().toString())
        );
    }

    /**
     * {@code GET  /nien-khoas} : get all the nienKhoas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nienKhoas in body.
     */
    @GetMapping("/nien-khoas")
    public ResponseEntity<List<NienKhoa>> getAllNienKhoas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NienKhoas");
        Page<NienKhoa> page = nienKhoaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nien-khoas/:id} : get the "id" nienKhoa.
     *
     * @param id the id of the nienKhoa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nienKhoa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nien-khoas/{id}")
    public ResponseEntity<NienKhoa> getNienKhoa(@PathVariable Long id) {
        log.debug("REST request to get NienKhoa : {}", id);
        Optional<NienKhoa> nienKhoa = nienKhoaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nienKhoa);
    }

    /**
     * {@code DELETE  /nien-khoas/:id} : delete the "id" nienKhoa.
     *
     * @param id the id of the nienKhoa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nien-khoas/{id}")
    public ResponseEntity<Void> deleteNienKhoa(@PathVariable Long id) {
        log.debug("REST request to delete NienKhoa : {}", id);
        nienKhoaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
