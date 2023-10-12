package com.quanlysinhvien.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.quanlysinhvien.web.IntegrationTest;
import com.quanlysinhvien.web.domain.NienKhoa;
import com.quanlysinhvien.web.repository.NienKhoaRepository;
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
 * Integration tests for the {@link NienKhoaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NienKhoaResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final Integer DEFAULT_NAM_BAT_DAU = 1;
    private static final Integer UPDATED_NAM_BAT_DAU = 2;

    private static final String ENTITY_API_URL = "/api/nien-khoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NienKhoaRepository nienKhoaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNienKhoaMockMvc;

    private NienKhoa nienKhoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NienKhoa createEntity(EntityManager em) {
        NienKhoa nienKhoa = new NienKhoa().ten(DEFAULT_TEN).namBatDau(DEFAULT_NAM_BAT_DAU);
        return nienKhoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NienKhoa createUpdatedEntity(EntityManager em) {
        NienKhoa nienKhoa = new NienKhoa().ten(UPDATED_TEN).namBatDau(UPDATED_NAM_BAT_DAU);
        return nienKhoa;
    }

    @BeforeEach
    public void initTest() {
        nienKhoa = createEntity(em);
    }

    @Test
    @Transactional
    void createNienKhoa() throws Exception {
        int databaseSizeBeforeCreate = nienKhoaRepository.findAll().size();
        // Create the NienKhoa
        restNienKhoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nienKhoa)))
            .andExpect(status().isCreated());

        // Validate the NienKhoa in the database
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeCreate + 1);
        NienKhoa testNienKhoa = nienKhoaList.get(nienKhoaList.size() - 1);
        assertThat(testNienKhoa.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testNienKhoa.getNamBatDau()).isEqualTo(DEFAULT_NAM_BAT_DAU);
    }

    @Test
    @Transactional
    void createNienKhoaWithExistingId() throws Exception {
        // Create the NienKhoa with an existing ID
        nienKhoa.setId(1L);

        int databaseSizeBeforeCreate = nienKhoaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNienKhoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nienKhoa)))
            .andExpect(status().isBadRequest());

        // Validate the NienKhoa in the database
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = nienKhoaRepository.findAll().size();
        // set the field null
        nienKhoa.setTen(null);

        // Create the NienKhoa, which fails.

        restNienKhoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nienKhoa)))
            .andExpect(status().isBadRequest());

        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNienKhoas() throws Exception {
        // Initialize the database
        nienKhoaRepository.saveAndFlush(nienKhoa);

        // Get all the nienKhoaList
        restNienKhoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nienKhoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].namBatDau").value(hasItem(DEFAULT_NAM_BAT_DAU)));
    }

    @Test
    @Transactional
    void getNienKhoa() throws Exception {
        // Initialize the database
        nienKhoaRepository.saveAndFlush(nienKhoa);

        // Get the nienKhoa
        restNienKhoaMockMvc
            .perform(get(ENTITY_API_URL_ID, nienKhoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nienKhoa.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN))
            .andExpect(jsonPath("$.namBatDau").value(DEFAULT_NAM_BAT_DAU));
    }

    @Test
    @Transactional
    void getNonExistingNienKhoa() throws Exception {
        // Get the nienKhoa
        restNienKhoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNienKhoa() throws Exception {
        // Initialize the database
        nienKhoaRepository.saveAndFlush(nienKhoa);

        int databaseSizeBeforeUpdate = nienKhoaRepository.findAll().size();

        // Update the nienKhoa
        NienKhoa updatedNienKhoa = nienKhoaRepository.findById(nienKhoa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNienKhoa are not directly saved in db
        em.detach(updatedNienKhoa);
        updatedNienKhoa.ten(UPDATED_TEN).namBatDau(UPDATED_NAM_BAT_DAU);

        restNienKhoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNienKhoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNienKhoa))
            )
            .andExpect(status().isOk());

        // Validate the NienKhoa in the database
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeUpdate);
        NienKhoa testNienKhoa = nienKhoaList.get(nienKhoaList.size() - 1);
        assertThat(testNienKhoa.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testNienKhoa.getNamBatDau()).isEqualTo(UPDATED_NAM_BAT_DAU);
    }

    @Test
    @Transactional
    void putNonExistingNienKhoa() throws Exception {
        int databaseSizeBeforeUpdate = nienKhoaRepository.findAll().size();
        nienKhoa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNienKhoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nienKhoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nienKhoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the NienKhoa in the database
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNienKhoa() throws Exception {
        int databaseSizeBeforeUpdate = nienKhoaRepository.findAll().size();
        nienKhoa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNienKhoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nienKhoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the NienKhoa in the database
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNienKhoa() throws Exception {
        int databaseSizeBeforeUpdate = nienKhoaRepository.findAll().size();
        nienKhoa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNienKhoaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nienKhoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NienKhoa in the database
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNienKhoaWithPatch() throws Exception {
        // Initialize the database
        nienKhoaRepository.saveAndFlush(nienKhoa);

        int databaseSizeBeforeUpdate = nienKhoaRepository.findAll().size();

        // Update the nienKhoa using partial update
        NienKhoa partialUpdatedNienKhoa = new NienKhoa();
        partialUpdatedNienKhoa.setId(nienKhoa.getId());

        restNienKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNienKhoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNienKhoa))
            )
            .andExpect(status().isOk());

        // Validate the NienKhoa in the database
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeUpdate);
        NienKhoa testNienKhoa = nienKhoaList.get(nienKhoaList.size() - 1);
        assertThat(testNienKhoa.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testNienKhoa.getNamBatDau()).isEqualTo(DEFAULT_NAM_BAT_DAU);
    }

    @Test
    @Transactional
    void fullUpdateNienKhoaWithPatch() throws Exception {
        // Initialize the database
        nienKhoaRepository.saveAndFlush(nienKhoa);

        int databaseSizeBeforeUpdate = nienKhoaRepository.findAll().size();

        // Update the nienKhoa using partial update
        NienKhoa partialUpdatedNienKhoa = new NienKhoa();
        partialUpdatedNienKhoa.setId(nienKhoa.getId());

        partialUpdatedNienKhoa.ten(UPDATED_TEN).namBatDau(UPDATED_NAM_BAT_DAU);

        restNienKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNienKhoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNienKhoa))
            )
            .andExpect(status().isOk());

        // Validate the NienKhoa in the database
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeUpdate);
        NienKhoa testNienKhoa = nienKhoaList.get(nienKhoaList.size() - 1);
        assertThat(testNienKhoa.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testNienKhoa.getNamBatDau()).isEqualTo(UPDATED_NAM_BAT_DAU);
    }

    @Test
    @Transactional
    void patchNonExistingNienKhoa() throws Exception {
        int databaseSizeBeforeUpdate = nienKhoaRepository.findAll().size();
        nienKhoa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNienKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nienKhoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nienKhoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the NienKhoa in the database
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNienKhoa() throws Exception {
        int databaseSizeBeforeUpdate = nienKhoaRepository.findAll().size();
        nienKhoa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNienKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nienKhoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the NienKhoa in the database
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNienKhoa() throws Exception {
        int databaseSizeBeforeUpdate = nienKhoaRepository.findAll().size();
        nienKhoa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNienKhoaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nienKhoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NienKhoa in the database
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNienKhoa() throws Exception {
        // Initialize the database
        nienKhoaRepository.saveAndFlush(nienKhoa);

        int databaseSizeBeforeDelete = nienKhoaRepository.findAll().size();

        // Delete the nienKhoa
        restNienKhoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, nienKhoa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NienKhoa> nienKhoaList = nienKhoaRepository.findAll();
        assertThat(nienKhoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
