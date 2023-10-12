package com.quanlysinhvien.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.quanlysinhvien.web.IntegrationTest;
import com.quanlysinhvien.web.domain.GiaoVien;
import com.quanlysinhvien.web.repository.GiaoVienRepository;
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
 * Integration tests for the {@link GiaoVienResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GiaoVienResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SDT = "AAAAAAAAAA";
    private static final String UPDATED_SDT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/giao-viens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GiaoVienRepository giaoVienRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGiaoVienMockMvc;

    private GiaoVien giaoVien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GiaoVien createEntity(EntityManager em) {
        GiaoVien giaoVien = new GiaoVien().ten(DEFAULT_TEN).email(DEFAULT_EMAIL).sdt(DEFAULT_SDT);
        return giaoVien;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GiaoVien createUpdatedEntity(EntityManager em) {
        GiaoVien giaoVien = new GiaoVien().ten(UPDATED_TEN).email(UPDATED_EMAIL).sdt(UPDATED_SDT);
        return giaoVien;
    }

    @BeforeEach
    public void initTest() {
        giaoVien = createEntity(em);
    }

    @Test
    @Transactional
    void createGiaoVien() throws Exception {
        int databaseSizeBeforeCreate = giaoVienRepository.findAll().size();
        // Create the GiaoVien
        restGiaoVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giaoVien)))
            .andExpect(status().isCreated());

        // Validate the GiaoVien in the database
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeCreate + 1);
        GiaoVien testGiaoVien = giaoVienList.get(giaoVienList.size() - 1);
        assertThat(testGiaoVien.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testGiaoVien.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGiaoVien.getSdt()).isEqualTo(DEFAULT_SDT);
    }

    @Test
    @Transactional
    void createGiaoVienWithExistingId() throws Exception {
        // Create the GiaoVien with an existing ID
        giaoVien.setId(1L);

        int databaseSizeBeforeCreate = giaoVienRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiaoVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giaoVien)))
            .andExpect(status().isBadRequest());

        // Validate the GiaoVien in the database
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = giaoVienRepository.findAll().size();
        // set the field null
        giaoVien.setTen(null);

        // Create the GiaoVien, which fails.

        restGiaoVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giaoVien)))
            .andExpect(status().isBadRequest());

        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = giaoVienRepository.findAll().size();
        // set the field null
        giaoVien.setEmail(null);

        // Create the GiaoVien, which fails.

        restGiaoVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giaoVien)))
            .andExpect(status().isBadRequest());

        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSdtIsRequired() throws Exception {
        int databaseSizeBeforeTest = giaoVienRepository.findAll().size();
        // set the field null
        giaoVien.setSdt(null);

        // Create the GiaoVien, which fails.

        restGiaoVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giaoVien)))
            .andExpect(status().isBadRequest());

        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGiaoViens() throws Exception {
        // Initialize the database
        giaoVienRepository.saveAndFlush(giaoVien);

        // Get all the giaoVienList
        restGiaoVienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giaoVien.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].sdt").value(hasItem(DEFAULT_SDT)));
    }

    @Test
    @Transactional
    void getGiaoVien() throws Exception {
        // Initialize the database
        giaoVienRepository.saveAndFlush(giaoVien);

        // Get the giaoVien
        restGiaoVienMockMvc
            .perform(get(ENTITY_API_URL_ID, giaoVien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(giaoVien.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.sdt").value(DEFAULT_SDT));
    }

    @Test
    @Transactional
    void getNonExistingGiaoVien() throws Exception {
        // Get the giaoVien
        restGiaoVienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGiaoVien() throws Exception {
        // Initialize the database
        giaoVienRepository.saveAndFlush(giaoVien);

        int databaseSizeBeforeUpdate = giaoVienRepository.findAll().size();

        // Update the giaoVien
        GiaoVien updatedGiaoVien = giaoVienRepository.findById(giaoVien.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGiaoVien are not directly saved in db
        em.detach(updatedGiaoVien);
        updatedGiaoVien.ten(UPDATED_TEN).email(UPDATED_EMAIL).sdt(UPDATED_SDT);

        restGiaoVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGiaoVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGiaoVien))
            )
            .andExpect(status().isOk());

        // Validate the GiaoVien in the database
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeUpdate);
        GiaoVien testGiaoVien = giaoVienList.get(giaoVienList.size() - 1);
        assertThat(testGiaoVien.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testGiaoVien.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGiaoVien.getSdt()).isEqualTo(UPDATED_SDT);
    }

    @Test
    @Transactional
    void putNonExistingGiaoVien() throws Exception {
        int databaseSizeBeforeUpdate = giaoVienRepository.findAll().size();
        giaoVien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiaoVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, giaoVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(giaoVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiaoVien in the database
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGiaoVien() throws Exception {
        int databaseSizeBeforeUpdate = giaoVienRepository.findAll().size();
        giaoVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiaoVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(giaoVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiaoVien in the database
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGiaoVien() throws Exception {
        int databaseSizeBeforeUpdate = giaoVienRepository.findAll().size();
        giaoVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiaoVienMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giaoVien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GiaoVien in the database
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGiaoVienWithPatch() throws Exception {
        // Initialize the database
        giaoVienRepository.saveAndFlush(giaoVien);

        int databaseSizeBeforeUpdate = giaoVienRepository.findAll().size();

        // Update the giaoVien using partial update
        GiaoVien partialUpdatedGiaoVien = new GiaoVien();
        partialUpdatedGiaoVien.setId(giaoVien.getId());

        partialUpdatedGiaoVien.ten(UPDATED_TEN);

        restGiaoVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGiaoVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGiaoVien))
            )
            .andExpect(status().isOk());

        // Validate the GiaoVien in the database
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeUpdate);
        GiaoVien testGiaoVien = giaoVienList.get(giaoVienList.size() - 1);
        assertThat(testGiaoVien.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testGiaoVien.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGiaoVien.getSdt()).isEqualTo(DEFAULT_SDT);
    }

    @Test
    @Transactional
    void fullUpdateGiaoVienWithPatch() throws Exception {
        // Initialize the database
        giaoVienRepository.saveAndFlush(giaoVien);

        int databaseSizeBeforeUpdate = giaoVienRepository.findAll().size();

        // Update the giaoVien using partial update
        GiaoVien partialUpdatedGiaoVien = new GiaoVien();
        partialUpdatedGiaoVien.setId(giaoVien.getId());

        partialUpdatedGiaoVien.ten(UPDATED_TEN).email(UPDATED_EMAIL).sdt(UPDATED_SDT);

        restGiaoVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGiaoVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGiaoVien))
            )
            .andExpect(status().isOk());

        // Validate the GiaoVien in the database
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeUpdate);
        GiaoVien testGiaoVien = giaoVienList.get(giaoVienList.size() - 1);
        assertThat(testGiaoVien.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testGiaoVien.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGiaoVien.getSdt()).isEqualTo(UPDATED_SDT);
    }

    @Test
    @Transactional
    void patchNonExistingGiaoVien() throws Exception {
        int databaseSizeBeforeUpdate = giaoVienRepository.findAll().size();
        giaoVien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiaoVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, giaoVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(giaoVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiaoVien in the database
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGiaoVien() throws Exception {
        int databaseSizeBeforeUpdate = giaoVienRepository.findAll().size();
        giaoVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiaoVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(giaoVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the GiaoVien in the database
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGiaoVien() throws Exception {
        int databaseSizeBeforeUpdate = giaoVienRepository.findAll().size();
        giaoVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiaoVienMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(giaoVien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GiaoVien in the database
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGiaoVien() throws Exception {
        // Initialize the database
        giaoVienRepository.saveAndFlush(giaoVien);

        int databaseSizeBeforeDelete = giaoVienRepository.findAll().size();

        // Delete the giaoVien
        restGiaoVienMockMvc
            .perform(delete(ENTITY_API_URL_ID, giaoVien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        assertThat(giaoVienList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
