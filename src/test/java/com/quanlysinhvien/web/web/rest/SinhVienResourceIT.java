package com.quanlysinhvien.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.quanlysinhvien.web.IntegrationTest;
import com.quanlysinhvien.web.domain.SinhVien;
import com.quanlysinhvien.web.repository.SinhVienRepository;
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
 * Integration tests for the {@link SinhVienResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SinhVienResourceIT {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DIEN_THOAI = "AAAAAAAAAA";
    private static final String UPDATED_DIEN_THOAI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sinh-viens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSinhVienMockMvc;

    private SinhVien sinhVien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SinhVien createEntity(EntityManager em) {
        SinhVien sinhVien = new SinhVien().ten(DEFAULT_TEN).email(DEFAULT_EMAIL).dienThoai(DEFAULT_DIEN_THOAI);
        return sinhVien;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SinhVien createUpdatedEntity(EntityManager em) {
        SinhVien sinhVien = new SinhVien().ten(UPDATED_TEN).email(UPDATED_EMAIL).dienThoai(UPDATED_DIEN_THOAI);
        return sinhVien;
    }

    @BeforeEach
    public void initTest() {
        sinhVien = createEntity(em);
    }

    @Test
    @Transactional
    void createSinhVien() throws Exception {
        int databaseSizeBeforeCreate = sinhVienRepository.findAll().size();
        // Create the SinhVien
        restSinhVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sinhVien)))
            .andExpect(status().isCreated());

        // Validate the SinhVien in the database
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeCreate + 1);
        SinhVien testSinhVien = sinhVienList.get(sinhVienList.size() - 1);
        assertThat(testSinhVien.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testSinhVien.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSinhVien.getDienThoai()).isEqualTo(DEFAULT_DIEN_THOAI);
    }

    @Test
    @Transactional
    void createSinhVienWithExistingId() throws Exception {
        // Create the SinhVien with an existing ID
        sinhVien.setId(1L);

        int databaseSizeBeforeCreate = sinhVienRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSinhVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sinhVien)))
            .andExpect(status().isBadRequest());

        // Validate the SinhVien in the database
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = sinhVienRepository.findAll().size();
        // set the field null
        sinhVien.setTen(null);

        // Create the SinhVien, which fails.

        restSinhVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sinhVien)))
            .andExpect(status().isBadRequest());

        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = sinhVienRepository.findAll().size();
        // set the field null
        sinhVien.setEmail(null);

        // Create the SinhVien, which fails.

        restSinhVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sinhVien)))
            .andExpect(status().isBadRequest());

        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDienThoaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = sinhVienRepository.findAll().size();
        // set the field null
        sinhVien.setDienThoai(null);

        // Create the SinhVien, which fails.

        restSinhVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sinhVien)))
            .andExpect(status().isBadRequest());

        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSinhViens() throws Exception {
        // Initialize the database
        sinhVienRepository.saveAndFlush(sinhVien);

        // Get all the sinhVienList
        restSinhVienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sinhVien.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dienThoai").value(hasItem(DEFAULT_DIEN_THOAI)));
    }

    @Test
    @Transactional
    void getSinhVien() throws Exception {
        // Initialize the database
        sinhVienRepository.saveAndFlush(sinhVien);

        // Get the sinhVien
        restSinhVienMockMvc
            .perform(get(ENTITY_API_URL_ID, sinhVien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sinhVien.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dienThoai").value(DEFAULT_DIEN_THOAI));
    }

    @Test
    @Transactional
    void getNonExistingSinhVien() throws Exception {
        // Get the sinhVien
        restSinhVienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSinhVien() throws Exception {
        // Initialize the database
        sinhVienRepository.saveAndFlush(sinhVien);

        int databaseSizeBeforeUpdate = sinhVienRepository.findAll().size();

        // Update the sinhVien
        SinhVien updatedSinhVien = sinhVienRepository.findById(sinhVien.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSinhVien are not directly saved in db
        em.detach(updatedSinhVien);
        updatedSinhVien.ten(UPDATED_TEN).email(UPDATED_EMAIL).dienThoai(UPDATED_DIEN_THOAI);

        restSinhVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSinhVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSinhVien))
            )
            .andExpect(status().isOk());

        // Validate the SinhVien in the database
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeUpdate);
        SinhVien testSinhVien = sinhVienList.get(sinhVienList.size() - 1);
        assertThat(testSinhVien.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testSinhVien.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSinhVien.getDienThoai()).isEqualTo(UPDATED_DIEN_THOAI);
    }

    @Test
    @Transactional
    void putNonExistingSinhVien() throws Exception {
        int databaseSizeBeforeUpdate = sinhVienRepository.findAll().size();
        sinhVien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sinhVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sinhVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the SinhVien in the database
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSinhVien() throws Exception {
        int databaseSizeBeforeUpdate = sinhVienRepository.findAll().size();
        sinhVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sinhVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the SinhVien in the database
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSinhVien() throws Exception {
        int databaseSizeBeforeUpdate = sinhVienRepository.findAll().size();
        sinhVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sinhVien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SinhVien in the database
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSinhVienWithPatch() throws Exception {
        // Initialize the database
        sinhVienRepository.saveAndFlush(sinhVien);

        int databaseSizeBeforeUpdate = sinhVienRepository.findAll().size();

        // Update the sinhVien using partial update
        SinhVien partialUpdatedSinhVien = new SinhVien();
        partialUpdatedSinhVien.setId(sinhVien.getId());

        partialUpdatedSinhVien.email(UPDATED_EMAIL).dienThoai(UPDATED_DIEN_THOAI);

        restSinhVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSinhVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSinhVien))
            )
            .andExpect(status().isOk());

        // Validate the SinhVien in the database
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeUpdate);
        SinhVien testSinhVien = sinhVienList.get(sinhVienList.size() - 1);
        assertThat(testSinhVien.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testSinhVien.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSinhVien.getDienThoai()).isEqualTo(UPDATED_DIEN_THOAI);
    }

    @Test
    @Transactional
    void fullUpdateSinhVienWithPatch() throws Exception {
        // Initialize the database
        sinhVienRepository.saveAndFlush(sinhVien);

        int databaseSizeBeforeUpdate = sinhVienRepository.findAll().size();

        // Update the sinhVien using partial update
        SinhVien partialUpdatedSinhVien = new SinhVien();
        partialUpdatedSinhVien.setId(sinhVien.getId());

        partialUpdatedSinhVien.ten(UPDATED_TEN).email(UPDATED_EMAIL).dienThoai(UPDATED_DIEN_THOAI);

        restSinhVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSinhVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSinhVien))
            )
            .andExpect(status().isOk());

        // Validate the SinhVien in the database
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeUpdate);
        SinhVien testSinhVien = sinhVienList.get(sinhVienList.size() - 1);
        assertThat(testSinhVien.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testSinhVien.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSinhVien.getDienThoai()).isEqualTo(UPDATED_DIEN_THOAI);
    }

    @Test
    @Transactional
    void patchNonExistingSinhVien() throws Exception {
        int databaseSizeBeforeUpdate = sinhVienRepository.findAll().size();
        sinhVien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sinhVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sinhVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the SinhVien in the database
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSinhVien() throws Exception {
        int databaseSizeBeforeUpdate = sinhVienRepository.findAll().size();
        sinhVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sinhVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the SinhVien in the database
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSinhVien() throws Exception {
        int databaseSizeBeforeUpdate = sinhVienRepository.findAll().size();
        sinhVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSinhVienMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sinhVien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SinhVien in the database
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSinhVien() throws Exception {
        // Initialize the database
        sinhVienRepository.saveAndFlush(sinhVien);

        int databaseSizeBeforeDelete = sinhVienRepository.findAll().size();

        // Delete the sinhVien
        restSinhVienMockMvc
            .perform(delete(ENTITY_API_URL_ID, sinhVien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SinhVien> sinhVienList = sinhVienRepository.findAll();
        assertThat(sinhVienList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
