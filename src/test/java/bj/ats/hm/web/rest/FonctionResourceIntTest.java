package bj.ats.hm.web.rest;

import bj.ats.hm.HotManApp;

import bj.ats.hm.domain.Fonction;
import bj.ats.hm.repository.FonctionRepository;
import bj.ats.hm.service.FonctionService;
import bj.ats.hm.repository.search.FonctionSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FonctionResource REST controller.
 *
 * @see FonctionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotManApp.class)
public class FonctionResourceIntTest {

    private static final String DEFAULT_POSTE = "AAAAAAAAAA";
    private static final String UPDATED_POSTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STARDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TYPECONTRAT = "AAAAAAAAAA";
    private static final String UPDATED_TYPECONTRAT = "BBBBBBBBBB";

    private static final String DEFAULT_DURRECONTRAT = "AAAAAAAAAA";
    private static final String UPDATED_DURRECONTRAT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATENAISSANE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATENAISSANE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHONENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONENUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_SALARY = 1L;
    private static final Long UPDATED_SALARY = 2L;

    @Autowired
    private FonctionRepository fonctionRepository;

    @Autowired
    private FonctionService fonctionService;

    @Autowired
    private FonctionSearchRepository fonctionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFonctionMockMvc;

    private Fonction fonction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FonctionResource fonctionResource = new FonctionResource(fonctionService);
        this.restFonctionMockMvc = MockMvcBuilders.standaloneSetup(fonctionResource)
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
    public static Fonction createEntity(EntityManager em) {
        Fonction fonction = new Fonction()
            .poste(DEFAULT_POSTE)
            .stardate(DEFAULT_STARDATE)
            .typecontrat(DEFAULT_TYPECONTRAT)
            .durrecontrat(DEFAULT_DURRECONTRAT)
            .datenaissane(DEFAULT_DATENAISSANE)
            .phonenumber(DEFAULT_PHONENUMBER)
            .email(DEFAULT_EMAIL)
            .salary(DEFAULT_SALARY);
        return fonction;
    }

    @Before
    public void initTest() {
        fonctionSearchRepository.deleteAll();
        fonction = createEntity(em);
    }

    @Test
    @Transactional
    public void createFonction() throws Exception {
        int databaseSizeBeforeCreate = fonctionRepository.findAll().size();

        // Create the Fonction
        restFonctionMockMvc.perform(post("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isCreated());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeCreate + 1);
        Fonction testFonction = fonctionList.get(fonctionList.size() - 1);
        assertThat(testFonction.getPoste()).isEqualTo(DEFAULT_POSTE);
        assertThat(testFonction.getStardate()).isEqualTo(DEFAULT_STARDATE);
        assertThat(testFonction.getTypecontrat()).isEqualTo(DEFAULT_TYPECONTRAT);
        assertThat(testFonction.getDurrecontrat()).isEqualTo(DEFAULT_DURRECONTRAT);
        assertThat(testFonction.getDatenaissane()).isEqualTo(DEFAULT_DATENAISSANE);
        assertThat(testFonction.getPhonenumber()).isEqualTo(DEFAULT_PHONENUMBER);
        assertThat(testFonction.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFonction.getSalary()).isEqualTo(DEFAULT_SALARY);

        // Validate the Fonction in Elasticsearch
        Fonction fonctionEs = fonctionSearchRepository.findOne(testFonction.getId());
        assertThat(fonctionEs).isEqualToComparingFieldByField(testFonction);
    }

    @Test
    @Transactional
    public void createFonctionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fonctionRepository.findAll().size();

        // Create the Fonction with an existing ID
        fonction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFonctionMockMvc.perform(post("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPosteIsRequired() throws Exception {
        int databaseSizeBeforeTest = fonctionRepository.findAll().size();
        // set the field null
        fonction.setPoste(null);

        // Create the Fonction, which fails.

        restFonctionMockMvc.perform(post("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isBadRequest());

        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStardateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fonctionRepository.findAll().size();
        // set the field null
        fonction.setStardate(null);

        // Create the Fonction, which fails.

        restFonctionMockMvc.perform(post("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isBadRequest());

        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFonctions() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList
        restFonctionMockMvc.perform(get("/api/fonctions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fonction.getId().intValue())))
            .andExpect(jsonPath("$.[*].poste").value(hasItem(DEFAULT_POSTE.toString())))
            .andExpect(jsonPath("$.[*].stardate").value(hasItem(DEFAULT_STARDATE.toString())))
            .andExpect(jsonPath("$.[*].typecontrat").value(hasItem(DEFAULT_TYPECONTRAT.toString())))
            .andExpect(jsonPath("$.[*].durrecontrat").value(hasItem(DEFAULT_DURRECONTRAT.toString())))
            .andExpect(jsonPath("$.[*].datenaissane").value(hasItem(DEFAULT_DATENAISSANE.toString())))
            .andExpect(jsonPath("$.[*].phonenumber").value(hasItem(DEFAULT_PHONENUMBER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())));
    }

    @Test
    @Transactional
    public void getFonction() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get the fonction
        restFonctionMockMvc.perform(get("/api/fonctions/{id}", fonction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fonction.getId().intValue()))
            .andExpect(jsonPath("$.poste").value(DEFAULT_POSTE.toString()))
            .andExpect(jsonPath("$.stardate").value(DEFAULT_STARDATE.toString()))
            .andExpect(jsonPath("$.typecontrat").value(DEFAULT_TYPECONTRAT.toString()))
            .andExpect(jsonPath("$.durrecontrat").value(DEFAULT_DURRECONTRAT.toString()))
            .andExpect(jsonPath("$.datenaissane").value(DEFAULT_DATENAISSANE.toString()))
            .andExpect(jsonPath("$.phonenumber").value(DEFAULT_PHONENUMBER.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFonction() throws Exception {
        // Get the fonction
        restFonctionMockMvc.perform(get("/api/fonctions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFonction() throws Exception {
        // Initialize the database
        fonctionService.save(fonction);

        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();

        // Update the fonction
        Fonction updatedFonction = fonctionRepository.findOne(fonction.getId());
        updatedFonction
            .poste(UPDATED_POSTE)
            .stardate(UPDATED_STARDATE)
            .typecontrat(UPDATED_TYPECONTRAT)
            .durrecontrat(UPDATED_DURRECONTRAT)
            .datenaissane(UPDATED_DATENAISSANE)
            .phonenumber(UPDATED_PHONENUMBER)
            .email(UPDATED_EMAIL)
            .salary(UPDATED_SALARY);

        restFonctionMockMvc.perform(put("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFonction)))
            .andExpect(status().isOk());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
        Fonction testFonction = fonctionList.get(fonctionList.size() - 1);
        assertThat(testFonction.getPoste()).isEqualTo(UPDATED_POSTE);
        assertThat(testFonction.getStardate()).isEqualTo(UPDATED_STARDATE);
        assertThat(testFonction.getTypecontrat()).isEqualTo(UPDATED_TYPECONTRAT);
        assertThat(testFonction.getDurrecontrat()).isEqualTo(UPDATED_DURRECONTRAT);
        assertThat(testFonction.getDatenaissane()).isEqualTo(UPDATED_DATENAISSANE);
        assertThat(testFonction.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
        assertThat(testFonction.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFonction.getSalary()).isEqualTo(UPDATED_SALARY);

        // Validate the Fonction in Elasticsearch
        Fonction fonctionEs = fonctionSearchRepository.findOne(testFonction.getId());
        assertThat(fonctionEs).isEqualToComparingFieldByField(testFonction);
    }

    @Test
    @Transactional
    public void updateNonExistingFonction() throws Exception {
        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();

        // Create the Fonction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFonctionMockMvc.perform(put("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isCreated());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFonction() throws Exception {
        // Initialize the database
        fonctionService.save(fonction);

        int databaseSizeBeforeDelete = fonctionRepository.findAll().size();

        // Get the fonction
        restFonctionMockMvc.perform(delete("/api/fonctions/{id}", fonction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean fonctionExistsInEs = fonctionSearchRepository.exists(fonction.getId());
        assertThat(fonctionExistsInEs).isFalse();

        // Validate the database is empty
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFonction() throws Exception {
        // Initialize the database
        fonctionService.save(fonction);

        // Search the fonction
        restFonctionMockMvc.perform(get("/api/_search/fonctions?query=id:" + fonction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fonction.getId().intValue())))
            .andExpect(jsonPath("$.[*].poste").value(hasItem(DEFAULT_POSTE.toString())))
            .andExpect(jsonPath("$.[*].stardate").value(hasItem(DEFAULT_STARDATE.toString())))
            .andExpect(jsonPath("$.[*].typecontrat").value(hasItem(DEFAULT_TYPECONTRAT.toString())))
            .andExpect(jsonPath("$.[*].durrecontrat").value(hasItem(DEFAULT_DURRECONTRAT.toString())))
            .andExpect(jsonPath("$.[*].datenaissane").value(hasItem(DEFAULT_DATENAISSANE.toString())))
            .andExpect(jsonPath("$.[*].phonenumber").value(hasItem(DEFAULT_PHONENUMBER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fonction.class);
        Fonction fonction1 = new Fonction();
        fonction1.setId(1L);
        Fonction fonction2 = new Fonction();
        fonction2.setId(fonction1.getId());
        assertThat(fonction1).isEqualTo(fonction2);
        fonction2.setId(2L);
        assertThat(fonction1).isNotEqualTo(fonction2);
        fonction1.setId(null);
        assertThat(fonction1).isNotEqualTo(fonction2);
    }
}
