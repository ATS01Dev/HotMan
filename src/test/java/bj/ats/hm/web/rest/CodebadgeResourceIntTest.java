package bj.ats.hm.web.rest;

import bj.ats.hm.HotManApp;

import bj.ats.hm.domain.Codebadge;
import bj.ats.hm.repository.CodebadgeRepository;
import bj.ats.hm.service.CodebadgeService;
import bj.ats.hm.repository.search.CodebadgeSearchRepository;
import bj.ats.hm.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CodebadgeResource REST controller.
 *
 * @see CodebadgeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotManApp.class)
public class CodebadgeResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private CodebadgeRepository codebadgeRepository;

    @Autowired
    private CodebadgeService codebadgeService;

    @Autowired
    private CodebadgeSearchRepository codebadgeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCodebadgeMockMvc;

    private Codebadge codebadge;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CodebadgeResource codebadgeResource = new CodebadgeResource(codebadgeService);
        this.restCodebadgeMockMvc = MockMvcBuilders.standaloneSetup(codebadgeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Codebadge createEntity(EntityManager em) {
        Codebadge codebadge = new Codebadge()
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE);
        return codebadge;
    }

    @Before
    public void initTest() {
        codebadgeSearchRepository.deleteAll();
        codebadge = createEntity(em);
    }

    @Test
    @Transactional
    public void createCodebadge() throws Exception {
        int databaseSizeBeforeCreate = codebadgeRepository.findAll().size();

        // Create the Codebadge
        restCodebadgeMockMvc.perform(post("/api/codebadges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codebadge)))
            .andExpect(status().isCreated());

        // Validate the Codebadge in the database
        List<Codebadge> codebadgeList = codebadgeRepository.findAll();
        assertThat(codebadgeList).hasSize(databaseSizeBeforeCreate + 1);
        Codebadge testCodebadge = codebadgeList.get(codebadgeList.size() - 1);
        assertThat(testCodebadge.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCodebadge.getCode()).isEqualTo(DEFAULT_CODE);

        // Validate the Codebadge in Elasticsearch
        Codebadge codebadgeEs = codebadgeSearchRepository.findOne(testCodebadge.getId());
        assertThat(codebadgeEs).isEqualToComparingFieldByField(testCodebadge);
    }

    @Test
    @Transactional
    public void createCodebadgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codebadgeRepository.findAll().size();

        // Create the Codebadge with an existing ID
        codebadge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodebadgeMockMvc.perform(post("/api/codebadges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codebadge)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Codebadge> codebadgeList = codebadgeRepository.findAll();
        assertThat(codebadgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCodebadges() throws Exception {
        // Initialize the database
        codebadgeRepository.saveAndFlush(codebadge);

        // Get all the codebadgeList
        restCodebadgeMockMvc.perform(get("/api/codebadges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codebadge.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getCodebadge() throws Exception {
        // Initialize the database
        codebadgeRepository.saveAndFlush(codebadge);

        // Get the codebadge
        restCodebadgeMockMvc.perform(get("/api/codebadges/{id}", codebadge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(codebadge.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCodebadge() throws Exception {
        // Get the codebadge
        restCodebadgeMockMvc.perform(get("/api/codebadges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodebadge() throws Exception {
        // Initialize the database
        codebadgeService.save(codebadge);

        int databaseSizeBeforeUpdate = codebadgeRepository.findAll().size();

        // Update the codebadge
        Codebadge updatedCodebadge = codebadgeRepository.findOne(codebadge.getId());
        updatedCodebadge
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE);

        restCodebadgeMockMvc.perform(put("/api/codebadges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCodebadge)))
            .andExpect(status().isOk());

        // Validate the Codebadge in the database
        List<Codebadge> codebadgeList = codebadgeRepository.findAll();
        assertThat(codebadgeList).hasSize(databaseSizeBeforeUpdate);
        Codebadge testCodebadge = codebadgeList.get(codebadgeList.size() - 1);
        assertThat(testCodebadge.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCodebadge.getCode()).isEqualTo(UPDATED_CODE);

        // Validate the Codebadge in Elasticsearch
        Codebadge codebadgeEs = codebadgeSearchRepository.findOne(testCodebadge.getId());
        assertThat(codebadgeEs).isEqualToComparingFieldByField(testCodebadge);
    }

    @Test
    @Transactional
    public void updateNonExistingCodebadge() throws Exception {
        int databaseSizeBeforeUpdate = codebadgeRepository.findAll().size();

        // Create the Codebadge

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCodebadgeMockMvc.perform(put("/api/codebadges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codebadge)))
            .andExpect(status().isCreated());

        // Validate the Codebadge in the database
        List<Codebadge> codebadgeList = codebadgeRepository.findAll();
        assertThat(codebadgeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCodebadge() throws Exception {
        // Initialize the database
        codebadgeService.save(codebadge);

        int databaseSizeBeforeDelete = codebadgeRepository.findAll().size();

        // Get the codebadge
        restCodebadgeMockMvc.perform(delete("/api/codebadges/{id}", codebadge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean codebadgeExistsInEs = codebadgeSearchRepository.exists(codebadge.getId());
        assertThat(codebadgeExistsInEs).isFalse();

        // Validate the database is empty
        List<Codebadge> codebadgeList = codebadgeRepository.findAll();
        assertThat(codebadgeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCodebadge() throws Exception {
        // Initialize the database
        codebadgeService.save(codebadge);

        // Search the codebadge
        restCodebadgeMockMvc.perform(get("/api/_search/codebadges?query=id:" + codebadge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codebadge.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Codebadge.class);
        Codebadge codebadge1 = new Codebadge();
        codebadge1.setId(1L);
        Codebadge codebadge2 = new Codebadge();
        codebadge2.setId(codebadge1.getId());
        assertThat(codebadge1).isEqualTo(codebadge2);
        codebadge2.setId(2L);
        assertThat(codebadge1).isNotEqualTo(codebadge2);
        codebadge1.setId(null);
        assertThat(codebadge1).isNotEqualTo(codebadge2);
    }
}
