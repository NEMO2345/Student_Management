package com.quanlysinhvien.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.quanlysinhvien.web.IntegrationTest;
import com.quanlysinhvien.web.domain.NganhHoc;
import com.quanlysinhvien.web.repository.NganhHocRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NganhHocResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NganhHocResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nganh-hocs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NganhHocRepository nganhHocRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNganhHocMockMvc;

    private NganhHoc nganhHoc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NganhHoc createEntity(EntityManager em) {
        NganhHoc nganhHoc = new NganhHoc().ten(DEFAULT_TEN);
        return nganhHoc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NganhHoc createUpdatedEntity(EntityManager em) {
        NganhHoc nganhHoc = new NganhHoc().ten(UPDATED_TEN);
        return nganhHoc;
    }

    @BeforeEach
    public void initTest() {
        nganhHoc = createEntity(em);
    }

    @Test
    @Transactional
    void createNganhHoc() throws Exception {
        int databaseSizeBeforeCreate = nganhHocRepository.findAll().size();
        // Create the NganhHoc
        restNganhHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nganhHoc)))
            .andExpect(status().isCreated());

        // Validate the NganhHoc in the database
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeCreate + 1);
        NganhHoc testNganhHoc = nganhHocList.get(nganhHocList.size() - 1);
        assertThat(testNganhHoc.getTen()).isEqualTo(DEFAULT_TEN);
    }

    @Test
    @Transactional
    void createNganhHocWithExistingId() throws Exception {
        // Create the NganhHoc with an existing ID
        nganhHoc.setId(1L);

        int databaseSizeBeforeCreate = nganhHocRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNganhHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nganhHoc)))
            .andExpect(status().isBadRequest());

        // Validate the NganhHoc in the database
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = nganhHocRepository.findAll().size();
        // set the field null
        nganhHoc.setTen(null);

        // Create the NganhHoc, which fails.

        restNganhHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nganhHoc)))
            .andExpect(status().isBadRequest());

        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNganhHocs() throws Exception {
        // Initialize the database
        nganhHocRepository.saveAndFlush(nganhHoc);

        // Get all the nganhHocList
        restNganhHocMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nganhHoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)));
    }

    @Test
    @Transactional
    void getNganhHoc() throws Exception {
        // Initialize the database
        nganhHocRepository.saveAndFlush(nganhHoc);

        // Get the nganhHoc
        restNganhHocMockMvc
            .perform(get(ENTITY_API_URL_ID, nganhHoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nganhHoc.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN));
    }

    @Test
    @Transactional
    void getNonExistingNganhHoc() throws Exception {
        // Get the nganhHoc
        restNganhHocMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNganhHoc() throws Exception {
        // Initialize the database
        nganhHocRepository.saveAndFlush(nganhHoc);

        int databaseSizeBeforeUpdate = nganhHocRepository.findAll().size();

        // Update the nganhHoc
        NganhHoc updatedNganhHoc = nganhHocRepository.findById(nganhHoc.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNganhHoc are not directly saved in db
        em.detach(updatedNganhHoc);
        updatedNganhHoc.ten(UPDATED_TEN);

        restNganhHocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNganhHoc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNganhHoc))
            )
            .andExpect(status().isOk());

        // Validate the NganhHoc in the database
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeUpdate);
        NganhHoc testNganhHoc = nganhHocList.get(nganhHocList.size() - 1);
        assertThat(testNganhHoc.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void putNonExistingNganhHoc() throws Exception {
        int databaseSizeBeforeUpdate = nganhHocRepository.findAll().size();
        nganhHoc.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNganhHocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nganhHoc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nganhHoc))
            )
            .andExpect(status().isBadRequest());

        // Validate the NganhHoc in the database
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNganhHoc() throws Exception {
        int databaseSizeBeforeUpdate = nganhHocRepository.findAll().size();
        nganhHoc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNganhHocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nganhHoc))
            )
            .andExpect(status().isBadRequest());

        // Validate the NganhHoc in the database
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNganhHoc() throws Exception {
        int databaseSizeBeforeUpdate = nganhHocRepository.findAll().size();
        nganhHoc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNganhHocMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nganhHoc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NganhHoc in the database
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNganhHocWithPatch() throws Exception {
        // Initialize the database
        nganhHocRepository.saveAndFlush(nganhHoc);

        int databaseSizeBeforeUpdate = nganhHocRepository.findAll().size();

        // Update the nganhHoc using partial update
        NganhHoc partialUpdatedNganhHoc = new NganhHoc();
        partialUpdatedNganhHoc.setId(nganhHoc.getId());

        restNganhHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNganhHoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNganhHoc))
            )
            .andExpect(status().isOk());

        // Validate the NganhHoc in the database
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeUpdate);
        NganhHoc testNganhHoc = nganhHocList.get(nganhHocList.size() - 1);
        assertThat(testNganhHoc.getTen()).isEqualTo(DEFAULT_TEN);
    }

    @Test
    @Transactional
    void fullUpdateNganhHocWithPatch() throws Exception {
        // Initialize the database
        nganhHocRepository.saveAndFlush(nganhHoc);

        int databaseSizeBeforeUpdate = nganhHocRepository.findAll().size();

        // Update the nganhHoc using partial update
        NganhHoc partialUpdatedNganhHoc = new NganhHoc();
        partialUpdatedNganhHoc.setId(nganhHoc.getId());

        partialUpdatedNganhHoc.ten(UPDATED_TEN);

        restNganhHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNganhHoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNganhHoc))
            )
            .andExpect(status().isOk());

        // Validate the NganhHoc in the database
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeUpdate);
        NganhHoc testNganhHoc = nganhHocList.get(nganhHocList.size() - 1);
        assertThat(testNganhHoc.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    void patchNonExistingNganhHoc() throws Exception {
        int databaseSizeBeforeUpdate = nganhHocRepository.findAll().size();
        nganhHoc.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNganhHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nganhHoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nganhHoc))
            )
            .andExpect(status().isBadRequest());

        // Validate the NganhHoc in the database
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNganhHoc() throws Exception {
        int databaseSizeBeforeUpdate = nganhHocRepository.findAll().size();
        nganhHoc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNganhHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nganhHoc))
            )
            .andExpect(status().isBadRequest());

        // Validate the NganhHoc in the database
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNganhHoc() throws Exception {
        int databaseSizeBeforeUpdate = nganhHocRepository.findAll().size();
        nganhHoc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNganhHocMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nganhHoc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NganhHoc in the database
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNganhHoc() throws Exception {
        // Initialize the database
        nganhHocRepository.saveAndFlush(nganhHoc);

        int databaseSizeBeforeDelete = nganhHocRepository.findAll().size();

        // Delete the nganhHoc
        restNganhHocMockMvc
            .perform(delete(ENTITY_API_URL_ID, nganhHoc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NganhHoc> nganhHocList = nganhHocRepository.findAll();
        assertThat(nganhHocList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
