package bj.ats.hm.web.rest;

import bj.ats.hm.HotManApp;

import bj.ats.hm.domain.Personel;
import bj.ats.hm.repository.PersonelRepository;
import bj.ats.hm.service.PersonelService;
import bj.ats.hm.repository.search.PersonelSearchRepository;
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

import bj.ats.hm.domain.enumeration.Sexe;
/**
 * Test class for the PersonelResource REST controller.
 *
 * @see PersonelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotManApp.class)
public class PersonelResourceIntTest {

    private static final String DEFAULT_FISTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FISTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.HOMME;
    private static final Sexe UPDATED_SEXE = Sexe.FEMME;

    @Autowired
    private PersonelRepository personelRepository;

    @Autowired
    private PersonelService personelService;

    @Autowired
    private PersonelSearchRepository personelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonelMockMvc;

    private Personel personel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonelResource personelResource = new PersonelResource(personelService);
        this.restPersonelMockMvc = MockMvcBuilders.standaloneSetup(personelResource)
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
    public static Personel createEntity(EntityManager em) {
        Personel personel = new Personel()
            .fistname(DEFAULT_FISTNAME)
            .lastname(DEFAULT_LASTNAME)
            .sexe(DEFAULT_SEXE);
        return personel;
    }

    @Before
    public void initTest() {
        personelSearchRepository.deleteAll();
        personel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonel() throws Exception {
        int databaseSizeBeforeCreate = personelRepository.findAll().size();

        // Create the Personel
        restPersonelMockMvc.perform(post("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personel)))
            .andExpect(status().isCreated());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeCreate + 1);
        Personel testPersonel = personelList.get(personelList.size() - 1);
        assertThat(testPersonel.getFistname()).isEqualTo(DEFAULT_FISTNAME);
        assertThat(testPersonel.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testPersonel.getSexe()).isEqualTo(DEFAULT_SEXE);

        // Validate the Personel in Elasticsearch
        Personel personelEs = personelSearchRepository.findOne(testPersonel.getId());
        assertThat(personelEs).isEqualToComparingFieldByField(testPersonel);
    }

    @Test
    @Transactional
    public void createPersonelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personelRepository.findAll().size();

        // Create the Personel with an existing ID
        personel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonelMockMvc.perform(post("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFistnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personelRepository.findAll().size();
        // set the field null
        personel.setFistname(null);

        // Create the Personel, which fails.

        restPersonelMockMvc.perform(post("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personel)))
            .andExpect(status().isBadRequest());

        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personelRepository.findAll().size();
        // set the field null
        personel.setLastname(null);

        // Create the Personel, which fails.

        restPersonelMockMvc.perform(post("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personel)))
            .andExpect(status().isBadRequest());

        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonels() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList
        restPersonelMockMvc.perform(get("/api/personels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personel.getId().intValue())))
            .andExpect(jsonPath("$.[*].fistname").value(hasItem(DEFAULT_FISTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())));
    }

    @Test
    @Transactional
    public void getPersonel() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get the personel
        restPersonelMockMvc.perform(get("/api/personels/{id}", personel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personel.getId().intValue()))
            .andExpect(jsonPath("$.fistname").value(DEFAULT_FISTNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonel() throws Exception {
        // Get the personel
        restPersonelMockMvc.perform(get("/api/personels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonel() throws Exception {
        // Initialize the database
        personelService.save(personel);

        int databaseSizeBeforeUpdate = personelRepository.findAll().size();

        // Update the personel
        Personel updatedPersonel = personelRepository.findOne(personel.getId());
        updatedPersonel
            .fistname(UPDATED_FISTNAME)
            .lastname(UPDATED_LASTNAME)
            .sexe(UPDATED_SEXE);

        restPersonelMockMvc.perform(put("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonel)))
            .andExpect(status().isOk());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
        Personel testPersonel = personelList.get(personelList.size() - 1);
        assertThat(testPersonel.getFistname()).isEqualTo(UPDATED_FISTNAME);
        assertThat(testPersonel.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPersonel.getSexe()).isEqualTo(UPDATED_SEXE);

        // Validate the Personel in Elasticsearch
        Personel personelEs = personelSearchRepository.findOne(testPersonel.getId());
        assertThat(personelEs).isEqualToComparingFieldByField(testPersonel);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonel() throws Exception {
        int databaseSizeBeforeUpdate = personelRepository.findAll().size();

        // Create the Personel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonelMockMvc.perform(put("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personel)))
            .andExpect(status().isCreated());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonel() throws Exception {
        // Initialize the database
        personelService.save(personel);

        int databaseSizeBeforeDelete = personelRepository.findAll().size();

        // Get the personel
        restPersonelMockMvc.perform(delete("/api/personels/{id}", personel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean personelExistsInEs = personelSearchRepository.exists(personel.getId());
        assertThat(personelExistsInEs).isFalse();

        // Validate the database is empty
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPersonel() throws Exception {
        // Initialize the database
        personelService.save(personel);

        // Search the personel
        restPersonelMockMvc.perform(get("/api/_search/personels?query=id:" + personel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personel.getId().intValue())))
            .andExpect(jsonPath("$.[*].fistname").value(hasItem(DEFAULT_FISTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personel.class);
        Personel personel1 = new Personel();
        personel1.setId(1L);
        Personel personel2 = new Personel();
        personel2.setId(personel1.getId());
        assertThat(personel1).isEqualTo(personel2);
        personel2.setId(2L);
        assertThat(personel1).isNotEqualTo(personel2);
        personel1.setId(null);
        assertThat(personel1).isNotEqualTo(personel2);
    }
}
