package bj.ats.hm.web.rest;

import bj.ats.hm.HotManApp;

import bj.ats.hm.domain.Horraires;
import bj.ats.hm.repository.HorrairesRepository;
import bj.ats.hm.service.HorrairesService;
import bj.ats.hm.repository.search.HorrairesSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static bj.ats.hm.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HorrairesResource REST controller.
 *
 * @see HorrairesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotManApp.class)
public class HorrairesResourceIntTest {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private HorrairesRepository horrairesRepository;

    @Autowired
    private HorrairesService horrairesService;

    @Autowired
    private HorrairesSearchRepository horrairesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHorrairesMockMvc;

    private Horraires horraires;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HorrairesResource horrairesResource = new HorrairesResource(horrairesService);
        this.restHorrairesMockMvc = MockMvcBuilders.standaloneSetup(horrairesResource)
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
    public static Horraires createEntity(EntityManager em) {
        Horraires horraires = new Horraires()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .description(DEFAULT_DESCRIPTION);
        return horraires;
    }

    @Before
    public void initTest() {
        horrairesSearchRepository.deleteAll();
        horraires = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorraires() throws Exception {
        int databaseSizeBeforeCreate = horrairesRepository.findAll().size();

        // Create the Horraires
        restHorrairesMockMvc.perform(post("/api/horraires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horraires)))
            .andExpect(status().isCreated());

        // Validate the Horraires in the database
        List<Horraires> horrairesList = horrairesRepository.findAll();
        assertThat(horrairesList).hasSize(databaseSizeBeforeCreate + 1);
        Horraires testHorraires = horrairesList.get(horrairesList.size() - 1);
        assertThat(testHorraires.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testHorraires.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testHorraires.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Horraires in Elasticsearch
        Horraires horrairesEs = horrairesSearchRepository.findOne(testHorraires.getId());
        assertThat(horrairesEs).isEqualToComparingFieldByField(testHorraires);
    }

    @Test
    @Transactional
    public void createHorrairesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horrairesRepository.findAll().size();

        // Create the Horraires with an existing ID
        horraires.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorrairesMockMvc.perform(post("/api/horraires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horraires)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Horraires> horrairesList = horrairesRepository.findAll();
        assertThat(horrairesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHorraires() throws Exception {
        // Initialize the database
        horrairesRepository.saveAndFlush(horraires);

        // Get all the horrairesList
        restHorrairesMockMvc.perform(get("/api/horraires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horraires.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getHorraires() throws Exception {
        // Initialize the database
        horrairesRepository.saveAndFlush(horraires);

        // Get the horraires
        restHorrairesMockMvc.perform(get("/api/horraires/{id}", horraires.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(horraires.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHorraires() throws Exception {
        // Get the horraires
        restHorrairesMockMvc.perform(get("/api/horraires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorraires() throws Exception {
        // Initialize the database
        horrairesService.save(horraires);

        int databaseSizeBeforeUpdate = horrairesRepository.findAll().size();

        // Update the horraires
        Horraires updatedHorraires = horrairesRepository.findOne(horraires.getId());
        updatedHorraires
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION);

        restHorrairesMockMvc.perform(put("/api/horraires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHorraires)))
            .andExpect(status().isOk());

        // Validate the Horraires in the database
        List<Horraires> horrairesList = horrairesRepository.findAll();
        assertThat(horrairesList).hasSize(databaseSizeBeforeUpdate);
        Horraires testHorraires = horrairesList.get(horrairesList.size() - 1);
        assertThat(testHorraires.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testHorraires.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testHorraires.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Horraires in Elasticsearch
        Horraires horrairesEs = horrairesSearchRepository.findOne(testHorraires.getId());
        assertThat(horrairesEs).isEqualToComparingFieldByField(testHorraires);
    }

    @Test
    @Transactional
    public void updateNonExistingHorraires() throws Exception {
        int databaseSizeBeforeUpdate = horrairesRepository.findAll().size();

        // Create the Horraires

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHorrairesMockMvc.perform(put("/api/horraires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horraires)))
            .andExpect(status().isCreated());

        // Validate the Horraires in the database
        List<Horraires> horrairesList = horrairesRepository.findAll();
        assertThat(horrairesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHorraires() throws Exception {
        // Initialize the database
        horrairesService.save(horraires);

        int databaseSizeBeforeDelete = horrairesRepository.findAll().size();

        // Get the horraires
        restHorrairesMockMvc.perform(delete("/api/horraires/{id}", horraires.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean horrairesExistsInEs = horrairesSearchRepository.exists(horraires.getId());
        assertThat(horrairesExistsInEs).isFalse();

        // Validate the database is empty
        List<Horraires> horrairesList = horrairesRepository.findAll();
        assertThat(horrairesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHorraires() throws Exception {
        // Initialize the database
        horrairesService.save(horraires);

        // Search the horraires
        restHorrairesMockMvc.perform(get("/api/_search/horraires?query=id:" + horraires.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horraires.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Horraires.class);
        Horraires horraires1 = new Horraires();
        horraires1.setId(1L);
        Horraires horraires2 = new Horraires();
        horraires2.setId(horraires1.getId());
        assertThat(horraires1).isEqualTo(horraires2);
        horraires2.setId(2L);
        assertThat(horraires1).isNotEqualTo(horraires2);
        horraires1.setId(null);
        assertThat(horraires1).isNotEqualTo(horraires2);
    }
}
