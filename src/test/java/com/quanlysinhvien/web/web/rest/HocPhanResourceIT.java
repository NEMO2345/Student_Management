package com.quanlysinhvien.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.quanlysinhvien.web.IntegrationTest;
import com.quanlysinhvien.web.domain.HocPhan;
import com.quanlysinhvien.web.repository.HocPhanRepository;
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
 * Integration tests for the {@link HocPhanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HocPhanResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String DEFAULT_MO_TA = "AAAAAAAAAA";
    private static final String UPDATED_MO_TA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hoc-phans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HocPhanRepository hocPhanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHocPhanMockMvc;

    private HocPhan hocPhan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HocPhan createEntity(EntityManager em) {
        HocPhan hocPhan = new HocPhan().ten(DEFAULT_TEN).moTa(DEFAULT_MO_TA);
        return hocPhan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HocPhan createUpdatedEntity(EntityManager em) {
        HocPhan hocPhan = new HocPhan().ten(UPDATED_TEN).moTa(UPDATED_MO_TA);
        return hocPhan;
    }

    @BeforeEach
    public void initTest() {
        hocPhan = createEntity(em);
    }

    @Test
    @Transactional
    void createHocPhan() throws Exception {
        int databaseSizeBeforeCreate = hocPhanRepository.findAll().size();
        // Create the HocPhan
        restHocPhanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hocPhan)))
            .andExpect(status().isCreated());

        // Validate the HocPhan in the database
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeCreate + 1);
        HocPhan testHocPhan = hocPhanList.get(hocPhanList.size() - 1);
        assertThat(testHocPhan.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testHocPhan.getMoTa()).isEqualTo(DEFAULT_MO_TA);
    }

    @Test
    @Transactional
    void createHocPhanWithExistingId() throws Exception {
        // Create the HocPhan with an existing ID
        hocPhan.setId(1L);

        int databaseSizeBeforeCreate = hocPhanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHocPhanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hocPhan)))
            .andExpect(status().isBadRequest());

        // Validate the HocPhan in the database
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = hocPhanRepository.findAll().size();
        // set the field null
        hocPhan.setTen(null);

        // Create the HocPhan, which fails.

        restHocPhanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hocPhan)))
            .andExpect(status().isBadRequest());

        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHocPhans() throws Exception {
        // Initialize the database
        hocPhanRepository.saveAndFlush(hocPhan);

        // Get all the hocPhanList
        restHocPhanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hocPhan.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].moTa").value(hasItem(DEFAULT_MO_TA)));
    }

    @Test
    @Transactional
    void getHocPhan() throws Exception {
        // Initialize the database
        hocPhanRepository.saveAndFlush(hocPhan);

        // Get the hocPhan
        restHocPhanMockMvc
            .perform(get(ENTITY_API_URL_ID, hocPhan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hocPhan.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN))
            .andExpect(jsonPath("$.moTa").value(DEFAULT_MO_TA));
    }

    @Test
    @Transactional
    void getNonExistingHocPhan() throws Exception {
        // Get the hocPhan
        restHocPhanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHocPhan() throws Exception {
        // Initialize the database
        hocPhanRepository.saveAndFlush(hocPhan);

        int databaseSizeBeforeUpdate = hocPhanRepository.findAll().size();

        // Update the hocPhan
        HocPhan updatedHocPhan = hocPhanRepository.findById(hocPhan.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHocPhan are not directly saved in db
        em.detach(updatedHocPhan);
        updatedHocPhan.ten(UPDATED_TEN).moTa(UPDATED_MO_TA);

        restHocPhanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHocPhan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHocPhan))
            )
            .andExpect(status().isOk());

        // Validate the HocPhan in the database
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeUpdate);
        HocPhan testHocPhan = hocPhanList.get(hocPhanList.size() - 1);
        assertThat(testHocPhan.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testHocPhan.getMoTa()).isEqualTo(UPDATED_MO_TA);
    }

    @Test
    @Transactional
    void putNonExistingHocPhan() throws Exception {
        int databaseSizeBeforeUpdate = hocPhanRepository.findAll().size();
        hocPhan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHocPhanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hocPhan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hocPhan))
            )
            .andExpect(status().isBadRequest());

        // Validate the HocPhan in the database
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHocPhan() throws Exception {
        int databaseSizeBeforeUpdate = hocPhanRepository.findAll().size();
        hocPhan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHocPhanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hocPhan))
            )
            .andExpect(status().isBadRequest());

        // Validate the HocPhan in the database
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHocPhan() throws Exception {
        int databaseSizeBeforeUpdate = hocPhanRepository.findAll().size();
        hocPhan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHocPhanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hocPhan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HocPhan in the database
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHocPhanWithPatch() throws Exception {
        // Initialize the database
        hocPhanRepository.saveAndFlush(hocPhan);

        int databaseSizeBeforeUpdate = hocPhanRepository.findAll().size();

        // Update the hocPhan using partial update
        HocPhan partialUpdatedHocPhan = new HocPhan();
        partialUpdatedHocPhan.setId(hocPhan.getId());

        partialUpdatedHocPhan.ten(UPDATED_TEN);

        restHocPhanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHocPhan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHocPhan))
            )
            .andExpect(status().isOk());

        // Validate the HocPhan in the database
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeUpdate);
        HocPhan testHocPhan = hocPhanList.get(hocPhanList.size() - 1);
        assertThat(testHocPhan.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testHocPhan.getMoTa()).isEqualTo(DEFAULT_MO_TA);
    }

    @Test
    @Transactional
    void fullUpdateHocPhanWithPatch() throws Exception {
        // Initialize the database
        hocPhanRepository.saveAndFlush(hocPhan);

        int databaseSizeBeforeUpdate = hocPhanRepository.findAll().size();

        // Update the hocPhan using partial update
        HocPhan partialUpdatedHocPhan = new HocPhan();
        partialUpdatedHocPhan.setId(hocPhan.getId());

        partialUpdatedHocPhan.ten(UPDATED_TEN).moTa(UPDATED_MO_TA);

        restHocPhanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHocPhan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHocPhan))
            )
            .andExpect(status().isOk());

        // Validate the HocPhan in the database
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeUpdate);
        HocPhan testHocPhan = hocPhanList.get(hocPhanList.size() - 1);
        assertThat(testHocPhan.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testHocPhan.getMoTa()).isEqualTo(UPDATED_MO_TA);
    }

    @Test
    @Transactional
    void patchNonExistingHocPhan() throws Exception {
        int databaseSizeBeforeUpdate = hocPhanRepository.findAll().size();
        hocPhan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHocPhanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hocPhan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hocPhan))
            )
            .andExpect(status().isBadRequest());

        // Validate the HocPhan in the database
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHocPhan() throws Exception {
        int databaseSizeBeforeUpdate = hocPhanRepository.findAll().size();
        hocPhan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHocPhanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hocPhan))
            )
            .andExpect(status().isBadRequest());

        // Validate the HocPhan in the database
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHocPhan() throws Exception {
        int databaseSizeBeforeUpdate = hocPhanRepository.findAll().size();
        hocPhan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHocPhanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hocPhan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HocPhan in the database
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHocPhan() throws Exception {
        // Initialize the database
        hocPhanRepository.saveAndFlush(hocPhan);

        int databaseSizeBeforeDelete = hocPhanRepository.findAll().size();

        // Delete the hocPhan
        restHocPhanMockMvc
            .perform(delete(ENTITY_API_URL_ID, hocPhan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        assertThat(hocPhanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
