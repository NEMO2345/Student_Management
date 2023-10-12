package com.quanlysinhvien.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.quanlysinhvien.web.IntegrationTest;
import com.quanlysinhvien.web.domain.LopDangKi;
import com.quanlysinhvien.web.repository.LopDangKiRepository;
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
 * Integration tests for the {@link LopDangKiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LopDangKiResourceIT {

    private static final Float DEFAULT_DIEM_QUA_TRINH = 1F;
    private static final Float UPDATED_DIEM_QUA_TRINH = 2F;

    private static final Float DEFAULT_DIEM_THI = 1F;
    private static final Float UPDATED_DIEM_THI = 2F;

    private static final Float DEFAULT_DIEM_KET_THUC_HOC_PHAN = 1F;
    private static final Float UPDATED_DIEM_KET_THUC_HOC_PHAN = 2F;

    private static final String ENTITY_API_URL = "/api/lop-dang-kis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LopDangKiRepository lopDangKiRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLopDangKiMockMvc;

    private LopDangKi lopDangKi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LopDangKi createEntity(EntityManager em) {
        LopDangKi lopDangKi = new LopDangKi()
            .diemQuaTrinh(DEFAULT_DIEM_QUA_TRINH)
            .diemThi(DEFAULT_DIEM_THI)
            .diemKetThucHocPhan(DEFAULT_DIEM_KET_THUC_HOC_PHAN);
        return lopDangKi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LopDangKi createUpdatedEntity(EntityManager em) {
        LopDangKi lopDangKi = new LopDangKi()
            .diemQuaTrinh(UPDATED_DIEM_QUA_TRINH)
            .diemThi(UPDATED_DIEM_THI)
            .diemKetThucHocPhan(UPDATED_DIEM_KET_THUC_HOC_PHAN);
        return lopDangKi;
    }

    @BeforeEach
    public void initTest() {
        lopDangKi = createEntity(em);
    }

    @Test
    @Transactional
    void createLopDangKi() throws Exception {
        int databaseSizeBeforeCreate = lopDangKiRepository.findAll().size();
        // Create the LopDangKi
        restLopDangKiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopDangKi)))
            .andExpect(status().isCreated());

        // Validate the LopDangKi in the database
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeCreate + 1);
        LopDangKi testLopDangKi = lopDangKiList.get(lopDangKiList.size() - 1);
        assertThat(testLopDangKi.getDiemQuaTrinh()).isEqualTo(DEFAULT_DIEM_QUA_TRINH);
        assertThat(testLopDangKi.getDiemThi()).isEqualTo(DEFAULT_DIEM_THI);
        assertThat(testLopDangKi.getDiemKetThucHocPhan()).isEqualTo(DEFAULT_DIEM_KET_THUC_HOC_PHAN);
    }

    @Test
    @Transactional
    void createLopDangKiWithExistingId() throws Exception {
        // Create the LopDangKi with an existing ID
        lopDangKi.setId(1L);

        int databaseSizeBeforeCreate = lopDangKiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLopDangKiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopDangKi)))
            .andExpect(status().isBadRequest());

        // Validate the LopDangKi in the database
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLopDangKis() throws Exception {
        // Initialize the database
        lopDangKiRepository.saveAndFlush(lopDangKi);

        // Get all the lopDangKiList
        restLopDangKiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lopDangKi.getId().intValue())))
            .andExpect(jsonPath("$.[*].diemQuaTrinh").value(hasItem(DEFAULT_DIEM_QUA_TRINH.doubleValue())))
            .andExpect(jsonPath("$.[*].diemThi").value(hasItem(DEFAULT_DIEM_THI.doubleValue())))
            .andExpect(jsonPath("$.[*].diemKetThucHocPhan").value(hasItem(DEFAULT_DIEM_KET_THUC_HOC_PHAN.doubleValue())));
    }

    @Test
    @Transactional
    void getLopDangKi() throws Exception {
        // Initialize the database
        lopDangKiRepository.saveAndFlush(lopDangKi);

        // Get the lopDangKi
        restLopDangKiMockMvc
            .perform(get(ENTITY_API_URL_ID, lopDangKi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lopDangKi.getId().intValue()))
            .andExpect(jsonPath("$.diemQuaTrinh").value(DEFAULT_DIEM_QUA_TRINH.doubleValue()))
            .andExpect(jsonPath("$.diemThi").value(DEFAULT_DIEM_THI.doubleValue()))
            .andExpect(jsonPath("$.diemKetThucHocPhan").value(DEFAULT_DIEM_KET_THUC_HOC_PHAN.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingLopDangKi() throws Exception {
        // Get the lopDangKi
        restLopDangKiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLopDangKi() throws Exception {
        // Initialize the database
        lopDangKiRepository.saveAndFlush(lopDangKi);

        int databaseSizeBeforeUpdate = lopDangKiRepository.findAll().size();

        // Update the lopDangKi
        LopDangKi updatedLopDangKi = lopDangKiRepository.findById(lopDangKi.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLopDangKi are not directly saved in db
        em.detach(updatedLopDangKi);
        updatedLopDangKi.diemQuaTrinh(UPDATED_DIEM_QUA_TRINH).diemThi(UPDATED_DIEM_THI).diemKetThucHocPhan(UPDATED_DIEM_KET_THUC_HOC_PHAN);

        restLopDangKiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLopDangKi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLopDangKi))
            )
            .andExpect(status().isOk());

        // Validate the LopDangKi in the database
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeUpdate);
        LopDangKi testLopDangKi = lopDangKiList.get(lopDangKiList.size() - 1);
        assertThat(testLopDangKi.getDiemQuaTrinh()).isEqualTo(UPDATED_DIEM_QUA_TRINH);
        assertThat(testLopDangKi.getDiemThi()).isEqualTo(UPDATED_DIEM_THI);
        assertThat(testLopDangKi.getDiemKetThucHocPhan()).isEqualTo(UPDATED_DIEM_KET_THUC_HOC_PHAN);
    }

    @Test
    @Transactional
    void putNonExistingLopDangKi() throws Exception {
        int databaseSizeBeforeUpdate = lopDangKiRepository.findAll().size();
        lopDangKi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLopDangKiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lopDangKi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lopDangKi))
            )
            .andExpect(status().isBadRequest());

        // Validate the LopDangKi in the database
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLopDangKi() throws Exception {
        int databaseSizeBeforeUpdate = lopDangKiRepository.findAll().size();
        lopDangKi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLopDangKiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lopDangKi))
            )
            .andExpect(status().isBadRequest());

        // Validate the LopDangKi in the database
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLopDangKi() throws Exception {
        int databaseSizeBeforeUpdate = lopDangKiRepository.findAll().size();
        lopDangKi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLopDangKiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopDangKi)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LopDangKi in the database
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLopDangKiWithPatch() throws Exception {
        // Initialize the database
        lopDangKiRepository.saveAndFlush(lopDangKi);

        int databaseSizeBeforeUpdate = lopDangKiRepository.findAll().size();

        // Update the lopDangKi using partial update
        LopDangKi partialUpdatedLopDangKi = new LopDangKi();
        partialUpdatedLopDangKi.setId(lopDangKi.getId());

        partialUpdatedLopDangKi.diemQuaTrinh(UPDATED_DIEM_QUA_TRINH);

        restLopDangKiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLopDangKi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLopDangKi))
            )
            .andExpect(status().isOk());

        // Validate the LopDangKi in the database
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeUpdate);
        LopDangKi testLopDangKi = lopDangKiList.get(lopDangKiList.size() - 1);
        assertThat(testLopDangKi.getDiemQuaTrinh()).isEqualTo(UPDATED_DIEM_QUA_TRINH);
        assertThat(testLopDangKi.getDiemThi()).isEqualTo(DEFAULT_DIEM_THI);
        assertThat(testLopDangKi.getDiemKetThucHocPhan()).isEqualTo(DEFAULT_DIEM_KET_THUC_HOC_PHAN);
    }

    @Test
    @Transactional
    void fullUpdateLopDangKiWithPatch() throws Exception {
        // Initialize the database
        lopDangKiRepository.saveAndFlush(lopDangKi);

        int databaseSizeBeforeUpdate = lopDangKiRepository.findAll().size();

        // Update the lopDangKi using partial update
        LopDangKi partialUpdatedLopDangKi = new LopDangKi();
        partialUpdatedLopDangKi.setId(lopDangKi.getId());

        partialUpdatedLopDangKi
            .diemQuaTrinh(UPDATED_DIEM_QUA_TRINH)
            .diemThi(UPDATED_DIEM_THI)
            .diemKetThucHocPhan(UPDATED_DIEM_KET_THUC_HOC_PHAN);

        restLopDangKiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLopDangKi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLopDangKi))
            )
            .andExpect(status().isOk());

        // Validate the LopDangKi in the database
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeUpdate);
        LopDangKi testLopDangKi = lopDangKiList.get(lopDangKiList.size() - 1);
        assertThat(testLopDangKi.getDiemQuaTrinh()).isEqualTo(UPDATED_DIEM_QUA_TRINH);
        assertThat(testLopDangKi.getDiemThi()).isEqualTo(UPDATED_DIEM_THI);
        assertThat(testLopDangKi.getDiemKetThucHocPhan()).isEqualTo(UPDATED_DIEM_KET_THUC_HOC_PHAN);
    }

    @Test
    @Transactional
    void patchNonExistingLopDangKi() throws Exception {
        int databaseSizeBeforeUpdate = lopDangKiRepository.findAll().size();
        lopDangKi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLopDangKiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lopDangKi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lopDangKi))
            )
            .andExpect(status().isBadRequest());

        // Validate the LopDangKi in the database
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLopDangKi() throws Exception {
        int databaseSizeBeforeUpdate = lopDangKiRepository.findAll().size();
        lopDangKi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLopDangKiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lopDangKi))
            )
            .andExpect(status().isBadRequest());

        // Validate the LopDangKi in the database
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLopDangKi() throws Exception {
        int databaseSizeBeforeUpdate = lopDangKiRepository.findAll().size();
        lopDangKi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLopDangKiMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lopDangKi))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LopDangKi in the database
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLopDangKi() throws Exception {
        // Initialize the database
        lopDangKiRepository.saveAndFlush(lopDangKi);

        int databaseSizeBeforeDelete = lopDangKiRepository.findAll().size();

        // Delete the lopDangKi
        restLopDangKiMockMvc
            .perform(delete(ENTITY_API_URL_ID, lopDangKi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LopDangKi> lopDangKiList = lopDangKiRepository.findAll();
        assertThat(lopDangKiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
