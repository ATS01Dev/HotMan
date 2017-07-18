package bj.ats.hm.web.rest;

import bj.ats.hm.HotManApp;

import bj.ats.hm.domain.Reservation;
import bj.ats.hm.domain.Rooms;
import bj.ats.hm.repository.ReservationRepository;
import bj.ats.hm.service.ReservationService;
import bj.ats.hm.repository.search.ReservationSearchRepository;
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
 * Test class for the ReservationResource REST controller.
 *
 * @see ReservationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotManApp.class)
public class ReservationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EVENEMENT = "AAAAAAAAAA";
    private static final String UPDATED_EVENEMENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_REVSERVATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_REVSERVATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_DEBUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEBUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_DUREE = 1L;
    private static final Long UPDATED_DUREE = 2L;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationSearchRepository reservationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReservationMockMvc;

    private Reservation reservation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReservationResource reservationResource = new ReservationResource(reservationService);
        this.restReservationMockMvc = MockMvcBuilders.standaloneSetup(reservationResource)
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
    public static Reservation createEntity(EntityManager em) {
        Reservation reservation = new Reservation()
            .name(DEFAULT_NAME)
            .evenement(DEFAULT_EVENEMENT)
            .date_Revservation(DEFAULT_DATE_REVSERVATION)
            .date_debut(DEFAULT_DATE_DEBUT)
            .date_fin(DEFAULT_DATE_FIN)
            .duree(DEFAULT_DUREE);
        // Add required entity
        Rooms rooms = RoomsResourceIntTest.createEntity(em);
        em.persist(rooms);
        em.flush();
        reservation.setRooms(rooms);
        return reservation;
    }

    @Before
    public void initTest() {
        reservationSearchRepository.deleteAll();
        reservation = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservation() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // Create the Reservation
        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isCreated());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate + 1);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReservation.getEvenement()).isEqualTo(DEFAULT_EVENEMENT);
        assertThat(testReservation.getDate_Revservation()).isEqualTo(DEFAULT_DATE_REVSERVATION);
        assertThat(testReservation.getDate_debut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testReservation.getDate_fin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testReservation.getDuree()).isEqualTo(DEFAULT_DUREE);

        // Validate the Reservation in Elasticsearch
        Reservation reservationEs = reservationSearchRepository.findOne(testReservation.getId());
        assertThat(reservationEs).isEqualToComparingFieldByField(testReservation);
    }

    @Test
    @Transactional
    public void createReservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // Create the Reservation with an existing ID
        reservation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setName(null);

        // Create the Reservation, which fails.

        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_RevservationIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setDate_Revservation(null);

        // Create the Reservation, which fails.

        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReservations() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList
        restReservationMockMvc.perform(get("/api/reservations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].evenement").value(hasItem(DEFAULT_EVENEMENT.toString())))
            .andExpect(jsonPath("$.[*].date_Revservation").value(hasItem(sameInstant(DEFAULT_DATE_REVSERVATION))))
            .andExpect(jsonPath("$.[*].date_debut").value(hasItem(sameInstant(DEFAULT_DATE_DEBUT))))
            .andExpect(jsonPath("$.[*].date_fin").value(hasItem(sameInstant(DEFAULT_DATE_FIN))))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE.intValue())));
    }

    @Test
    @Transactional
    public void getReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reservation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.evenement").value(DEFAULT_EVENEMENT.toString()))
            .andExpect(jsonPath("$.date_Revservation").value(sameInstant(DEFAULT_DATE_REVSERVATION)))
            .andExpect(jsonPath("$.date_debut").value(sameInstant(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.date_fin").value(sameInstant(DEFAULT_DATE_FIN)))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingReservation() throws Exception {
        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservation() throws Exception {
        // Initialize the database
        reservationService.save(reservation);

        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation
        Reservation updatedReservation = reservationRepository.findOne(reservation.getId());
        updatedReservation
            .name(UPDATED_NAME)
            .evenement(UPDATED_EVENEMENT)
            .date_Revservation(UPDATED_DATE_REVSERVATION)
            .date_debut(UPDATED_DATE_DEBUT)
            .date_fin(UPDATED_DATE_FIN)
            .duree(UPDATED_DUREE);

        restReservationMockMvc.perform(put("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReservation)))
            .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReservation.getEvenement()).isEqualTo(UPDATED_EVENEMENT);
        assertThat(testReservation.getDate_Revservation()).isEqualTo(UPDATED_DATE_REVSERVATION);
        assertThat(testReservation.getDate_debut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testReservation.getDate_fin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testReservation.getDuree()).isEqualTo(UPDATED_DUREE);

        // Validate the Reservation in Elasticsearch
        Reservation reservationEs = reservationSearchRepository.findOne(testReservation.getId());
        assertThat(reservationEs).isEqualToComparingFieldByField(testReservation);
    }

    @Test
    @Transactional
    public void updateNonExistingReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Create the Reservation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReservationMockMvc.perform(put("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isCreated());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReservation() throws Exception {
        // Initialize the database
        reservationService.save(reservation);

        int databaseSizeBeforeDelete = reservationRepository.findAll().size();

        // Get the reservation
        restReservationMockMvc.perform(delete("/api/reservations/{id}", reservation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean reservationExistsInEs = reservationSearchRepository.exists(reservation.getId());
        assertThat(reservationExistsInEs).isFalse();

        // Validate the database is empty
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReservation() throws Exception {
        // Initialize the database
        reservationService.save(reservation);

        // Search the reservation
        restReservationMockMvc.perform(get("/api/_search/reservations?query=id:" + reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].evenement").value(hasItem(DEFAULT_EVENEMENT.toString())))
            .andExpect(jsonPath("$.[*].date_Revservation").value(hasItem(sameInstant(DEFAULT_DATE_REVSERVATION))))
            .andExpect(jsonPath("$.[*].date_debut").value(hasItem(sameInstant(DEFAULT_DATE_DEBUT))))
            .andExpect(jsonPath("$.[*].date_fin").value(hasItem(sameInstant(DEFAULT_DATE_FIN))))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reservation.class);
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        Reservation reservation2 = new Reservation();
        reservation2.setId(reservation1.getId());
        assertThat(reservation1).isEqualTo(reservation2);
        reservation2.setId(2L);
        assertThat(reservation1).isNotEqualTo(reservation2);
        reservation1.setId(null);
        assertThat(reservation1).isNotEqualTo(reservation2);
    }
}
