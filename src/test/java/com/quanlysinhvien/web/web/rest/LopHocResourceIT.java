package com.quanlysinhvien.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.quanlysinhvien.web.IntegrationTest;
import com.quanlysinhvien.web.domain.LopHoc;
import com.quanlysinhvien.web.repository.LopHocRepository;
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
 * Integration tests for the {@link LopHocResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LopHocResourceIT {

    private static final String DEFAULT_KI_HOC = "AAAAAAAAAA";
    private static final String UPDATED_KI_HOC = "BBBBBBBBBB";

    private static final String DEFAULT_NGAY_HOC_TRONG_TUAN = "AAAAAAAAAA";
    private static final String UPDATED_NGAY_HOC_TRONG_TUAN = "BBBBBBBBBB";

    private static final String DEFAULT_THOI_GIAN_BAT_DAU = "AAAAAAAAAA";
    private static final String UPDATED_THOI_GIAN_BAT_DAU = "BBBBBBBBBB";

    private static final String DEFAULT_THOI_GIAN_KET_THUC = "AAAAAAAAAA";
    private static final String UPDATED_THOI_GIAN_KET_THUC = "BBBBBBBBBB";

    private static final String DEFAULT_PHONG_HOC = "AAAAAAAAAA";
    private static final String UPDATED_PHONG_HOC = "BBBBBBBBBB";

    private static final String DEFAULT_NGAY_THI = "AAAAAAAAAA";
    private static final String UPDATED_NGAY_THI = "BBBBBBBBBB";

    private static final String DEFAULT_GIO_THI = "AAAAAAAAAA";
    private static final String UPDATED_GIO_THI = "BBBBBBBBBB";

    private static final String DEFAULT_PHONG_THI = "AAAAAAAAAA";
    private static final String UPDATED_PHONG_THI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lop-hocs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LopHocRepository lopHocRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLopHocMockMvc;

    private LopHoc lopHoc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LopHoc createEntity(EntityManager em) {
        LopHoc lopHoc = new LopHoc()
            .kiHoc(DEFAULT_KI_HOC)
            .ngayHocTrongTuan(DEFAULT_NGAY_HOC_TRONG_TUAN)
            .thoiGianBatDau(DEFAULT_THOI_GIAN_BAT_DAU)
            .thoiGianKetThuc(DEFAULT_THOI_GIAN_KET_THUC)
            .phongHoc(DEFAULT_PHONG_HOC)
            .ngayThi(DEFAULT_NGAY_THI)
            .gioThi(DEFAULT_GIO_THI)
            .phongThi(DEFAULT_PHONG_THI);
        return lopHoc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LopHoc createUpdatedEntity(EntityManager em) {
        LopHoc lopHoc = new LopHoc()
            .kiHoc(UPDATED_KI_HOC)
            .ngayHocTrongTuan(UPDATED_NGAY_HOC_TRONG_TUAN)
            .thoiGianBatDau(UPDATED_THOI_GIAN_BAT_DAU)
            .thoiGianKetThuc(UPDATED_THOI_GIAN_KET_THUC)
            .phongHoc(UPDATED_PHONG_HOC)
            .ngayThi(UPDATED_NGAY_THI)
            .gioThi(UPDATED_GIO_THI)
            .phongThi(UPDATED_PHONG_THI);
        return lopHoc;
    }

    @BeforeEach
    public void initTest() {
        lopHoc = createEntity(em);
    }

    @Test
    @Transactional
    void createLopHoc() throws Exception {
        int databaseSizeBeforeCreate = lopHocRepository.findAll().size();
        // Create the LopHoc
        restLopHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isCreated());

        // Validate the LopHoc in the database
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeCreate + 1);
        LopHoc testLopHoc = lopHocList.get(lopHocList.size() - 1);
        assertThat(testLopHoc.getKiHoc()).isEqualTo(DEFAULT_KI_HOC);
        assertThat(testLopHoc.getNgayHocTrongTuan()).isEqualTo(DEFAULT_NGAY_HOC_TRONG_TUAN);
        assertThat(testLopHoc.getThoiGianBatDau()).isEqualTo(DEFAULT_THOI_GIAN_BAT_DAU);
        assertThat(testLopHoc.getThoiGianKetThuc()).isEqualTo(DEFAULT_THOI_GIAN_KET_THUC);
        assertThat(testLopHoc.getPhongHoc()).isEqualTo(DEFAULT_PHONG_HOC);
        assertThat(testLopHoc.getNgayThi()).isEqualTo(DEFAULT_NGAY_THI);
        assertThat(testLopHoc.getGioThi()).isEqualTo(DEFAULT_GIO_THI);
        assertThat(testLopHoc.getPhongThi()).isEqualTo(DEFAULT_PHONG_THI);
    }

    @Test
    @Transactional
    void createLopHocWithExistingId() throws Exception {
        // Create the LopHoc with an existing ID
        lopHoc.setId(1L);

        int databaseSizeBeforeCreate = lopHocRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLopHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isBadRequest());

        // Validate the LopHoc in the database
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKiHocIsRequired() throws Exception {
        int databaseSizeBeforeTest = lopHocRepository.findAll().size();
        // set the field null
        lopHoc.setKiHoc(null);

        // Create the LopHoc, which fails.

        restLopHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isBadRequest());

        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNgayHocTrongTuanIsRequired() throws Exception {
        int databaseSizeBeforeTest = lopHocRepository.findAll().size();
        // set the field null
        lopHoc.setNgayHocTrongTuan(null);

        // Create the LopHoc, which fails.

        restLopHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isBadRequest());

        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThoiGianBatDauIsRequired() throws Exception {
        int databaseSizeBeforeTest = lopHocRepository.findAll().size();
        // set the field null
        lopHoc.setThoiGianBatDau(null);

        // Create the LopHoc, which fails.

        restLopHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isBadRequest());

        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThoiGianKetThucIsRequired() throws Exception {
        int databaseSizeBeforeTest = lopHocRepository.findAll().size();
        // set the field null
        lopHoc.setThoiGianKetThuc(null);

        // Create the LopHoc, which fails.

        restLopHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isBadRequest());

        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhongHocIsRequired() throws Exception {
        int databaseSizeBeforeTest = lopHocRepository.findAll().size();
        // set the field null
        lopHoc.setPhongHoc(null);

        // Create the LopHoc, which fails.

        restLopHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isBadRequest());

        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNgayThiIsRequired() throws Exception {
        int databaseSizeBeforeTest = lopHocRepository.findAll().size();
        // set the field null
        lopHoc.setNgayThi(null);

        // Create the LopHoc, which fails.

        restLopHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isBadRequest());

        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGioThiIsRequired() throws Exception {
        int databaseSizeBeforeTest = lopHocRepository.findAll().size();
        // set the field null
        lopHoc.setGioThi(null);

        // Create the LopHoc, which fails.

        restLopHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isBadRequest());

        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhongThiIsRequired() throws Exception {
        int databaseSizeBeforeTest = lopHocRepository.findAll().size();
        // set the field null
        lopHoc.setPhongThi(null);

        // Create the LopHoc, which fails.

        restLopHocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isBadRequest());

        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLopHocs() throws Exception {
        // Initialize the database
        lopHocRepository.saveAndFlush(lopHoc);

        // Get all the lopHocList
        restLopHocMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lopHoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].kiHoc").value(hasItem(DEFAULT_KI_HOC)))
            .andExpect(jsonPath("$.[*].ngayHocTrongTuan").value(hasItem(DEFAULT_NGAY_HOC_TRONG_TUAN)))
            .andExpect(jsonPath("$.[*].thoiGianBatDau").value(hasItem(DEFAULT_THOI_GIAN_BAT_DAU)))
            .andExpect(jsonPath("$.[*].thoiGianKetThuc").value(hasItem(DEFAULT_THOI_GIAN_KET_THUC)))
            .andExpect(jsonPath("$.[*].phongHoc").value(hasItem(DEFAULT_PHONG_HOC)))
            .andExpect(jsonPath("$.[*].ngayThi").value(hasItem(DEFAULT_NGAY_THI)))
            .andExpect(jsonPath("$.[*].gioThi").value(hasItem(DEFAULT_GIO_THI)))
            .andExpect(jsonPath("$.[*].phongThi").value(hasItem(DEFAULT_PHONG_THI)));
    }

    @Test
    @Transactional
    void getLopHoc() throws Exception {
        // Initialize the database
        lopHocRepository.saveAndFlush(lopHoc);

        // Get the lopHoc
        restLopHocMockMvc
            .perform(get(ENTITY_API_URL_ID, lopHoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lopHoc.getId().intValue()))
            .andExpect(jsonPath("$.kiHoc").value(DEFAULT_KI_HOC))
            .andExpect(jsonPath("$.ngayHocTrongTuan").value(DEFAULT_NGAY_HOC_TRONG_TUAN))
            .andExpect(jsonPath("$.thoiGianBatDau").value(DEFAULT_THOI_GIAN_BAT_DAU))
            .andExpect(jsonPath("$.thoiGianKetThuc").value(DEFAULT_THOI_GIAN_KET_THUC))
            .andExpect(jsonPath("$.phongHoc").value(DEFAULT_PHONG_HOC))
            .andExpect(jsonPath("$.ngayThi").value(DEFAULT_NGAY_THI))
            .andExpect(jsonPath("$.gioThi").value(DEFAULT_GIO_THI))
            .andExpect(jsonPath("$.phongThi").value(DEFAULT_PHONG_THI));
    }

    @Test
    @Transactional
    void getNonExistingLopHoc() throws Exception {
        // Get the lopHoc
        restLopHocMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLopHoc() throws Exception {
        // Initialize the database
        lopHocRepository.saveAndFlush(lopHoc);

        int databaseSizeBeforeUpdate = lopHocRepository.findAll().size();

        // Update the lopHoc
        LopHoc updatedLopHoc = lopHocRepository.findById(lopHoc.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLopHoc are not directly saved in db
        em.detach(updatedLopHoc);
        updatedLopHoc
            .kiHoc(UPDATED_KI_HOC)
            .ngayHocTrongTuan(UPDATED_NGAY_HOC_TRONG_TUAN)
            .thoiGianBatDau(UPDATED_THOI_GIAN_BAT_DAU)
            .thoiGianKetThuc(UPDATED_THOI_GIAN_KET_THUC)
            .phongHoc(UPDATED_PHONG_HOC)
            .ngayThi(UPDATED_NGAY_THI)
            .gioThi(UPDATED_GIO_THI)
            .phongThi(UPDATED_PHONG_THI);

        restLopHocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLopHoc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLopHoc))
            )
            .andExpect(status().isOk());

        // Validate the LopHoc in the database
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeUpdate);
        LopHoc testLopHoc = lopHocList.get(lopHocList.size() - 1);
        assertThat(testLopHoc.getKiHoc()).isEqualTo(UPDATED_KI_HOC);
        assertThat(testLopHoc.getNgayHocTrongTuan()).isEqualTo(UPDATED_NGAY_HOC_TRONG_TUAN);
        assertThat(testLopHoc.getThoiGianBatDau()).isEqualTo(UPDATED_THOI_GIAN_BAT_DAU);
        assertThat(testLopHoc.getThoiGianKetThuc()).isEqualTo(UPDATED_THOI_GIAN_KET_THUC);
        assertThat(testLopHoc.getPhongHoc()).isEqualTo(UPDATED_PHONG_HOC);
        assertThat(testLopHoc.getNgayThi()).isEqualTo(UPDATED_NGAY_THI);
        assertThat(testLopHoc.getGioThi()).isEqualTo(UPDATED_GIO_THI);
        assertThat(testLopHoc.getPhongThi()).isEqualTo(UPDATED_PHONG_THI);
    }

    @Test
    @Transactional
    void putNonExistingLopHoc() throws Exception {
        int databaseSizeBeforeUpdate = lopHocRepository.findAll().size();
        lopHoc.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLopHocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lopHoc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lopHoc))
            )
            .andExpect(status().isBadRequest());

        // Validate the LopHoc in the database
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLopHoc() throws Exception {
        int databaseSizeBeforeUpdate = lopHocRepository.findAll().size();
        lopHoc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLopHocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lopHoc))
            )
            .andExpect(status().isBadRequest());

        // Validate the LopHoc in the database
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLopHoc() throws Exception {
        int databaseSizeBeforeUpdate = lopHocRepository.findAll().size();
        lopHoc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLopHocMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LopHoc in the database
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLopHocWithPatch() throws Exception {
        // Initialize the database
        lopHocRepository.saveAndFlush(lopHoc);

        int databaseSizeBeforeUpdate = lopHocRepository.findAll().size();

        // Update the lopHoc using partial update
        LopHoc partialUpdatedLopHoc = new LopHoc();
        partialUpdatedLopHoc.setId(lopHoc.getId());

        partialUpdatedLopHoc.kiHoc(UPDATED_KI_HOC);

        restLopHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLopHoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLopHoc))
            )
            .andExpect(status().isOk());

        // Validate the LopHoc in the database
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeUpdate);
        LopHoc testLopHoc = lopHocList.get(lopHocList.size() - 1);
        assertThat(testLopHoc.getKiHoc()).isEqualTo(UPDATED_KI_HOC);
        assertThat(testLopHoc.getNgayHocTrongTuan()).isEqualTo(DEFAULT_NGAY_HOC_TRONG_TUAN);
        assertThat(testLopHoc.getThoiGianBatDau()).isEqualTo(DEFAULT_THOI_GIAN_BAT_DAU);
        assertThat(testLopHoc.getThoiGianKetThuc()).isEqualTo(DEFAULT_THOI_GIAN_KET_THUC);
        assertThat(testLopHoc.getPhongHoc()).isEqualTo(DEFAULT_PHONG_HOC);
        assertThat(testLopHoc.getNgayThi()).isEqualTo(DEFAULT_NGAY_THI);
        assertThat(testLopHoc.getGioThi()).isEqualTo(DEFAULT_GIO_THI);
        assertThat(testLopHoc.getPhongThi()).isEqualTo(DEFAULT_PHONG_THI);
    }

    @Test
    @Transactional
    void fullUpdateLopHocWithPatch() throws Exception {
        // Initialize the database
        lopHocRepository.saveAndFlush(lopHoc);

        int databaseSizeBeforeUpdate = lopHocRepository.findAll().size();

        // Update the lopHoc using partial update
        LopHoc partialUpdatedLopHoc = new LopHoc();
        partialUpdatedLopHoc.setId(lopHoc.getId());

        partialUpdatedLopHoc
            .kiHoc(UPDATED_KI_HOC)
            .ngayHocTrongTuan(UPDATED_NGAY_HOC_TRONG_TUAN)
            .thoiGianBatDau(UPDATED_THOI_GIAN_BAT_DAU)
            .thoiGianKetThuc(UPDATED_THOI_GIAN_KET_THUC)
            .phongHoc(UPDATED_PHONG_HOC)
            .ngayThi(UPDATED_NGAY_THI)
            .gioThi(UPDATED_GIO_THI)
            .phongThi(UPDATED_PHONG_THI);

        restLopHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLopHoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLopHoc))
            )
            .andExpect(status().isOk());

        // Validate the LopHoc in the database
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeUpdate);
        LopHoc testLopHoc = lopHocList.get(lopHocList.size() - 1);
        assertThat(testLopHoc.getKiHoc()).isEqualTo(UPDATED_KI_HOC);
        assertThat(testLopHoc.getNgayHocTrongTuan()).isEqualTo(UPDATED_NGAY_HOC_TRONG_TUAN);
        assertThat(testLopHoc.getThoiGianBatDau()).isEqualTo(UPDATED_THOI_GIAN_BAT_DAU);
        assertThat(testLopHoc.getThoiGianKetThuc()).isEqualTo(UPDATED_THOI_GIAN_KET_THUC);
        assertThat(testLopHoc.getPhongHoc()).isEqualTo(UPDATED_PHONG_HOC);
        assertThat(testLopHoc.getNgayThi()).isEqualTo(UPDATED_NGAY_THI);
        assertThat(testLopHoc.getGioThi()).isEqualTo(UPDATED_GIO_THI);
        assertThat(testLopHoc.getPhongThi()).isEqualTo(UPDATED_PHONG_THI);
    }

    @Test
    @Transactional
    void patchNonExistingLopHoc() throws Exception {
        int databaseSizeBeforeUpdate = lopHocRepository.findAll().size();
        lopHoc.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLopHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lopHoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lopHoc))
            )
            .andExpect(status().isBadRequest());

        // Validate the LopHoc in the database
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLopHoc() throws Exception {
        int databaseSizeBeforeUpdate = lopHocRepository.findAll().size();
        lopHoc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLopHocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lopHoc))
            )
            .andExpect(status().isBadRequest());

        // Validate the LopHoc in the database
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLopHoc() throws Exception {
        int databaseSizeBeforeUpdate = lopHocRepository.findAll().size();
        lopHoc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLopHocMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lopHoc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LopHoc in the database
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLopHoc() throws Exception {
        // Initialize the database
        lopHocRepository.saveAndFlush(lopHoc);

        int databaseSizeBeforeDelete = lopHocRepository.findAll().size();

        // Delete the lopHoc
        restLopHocMockMvc
            .perform(delete(ENTITY_API_URL_ID, lopHoc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LopHoc> lopHocList = lopHocRepository.findAll();
        assertThat(lopHocList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
