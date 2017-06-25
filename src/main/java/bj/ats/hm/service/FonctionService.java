package bj.ats.hm.service;

import bj.ats.hm.domain.Fonction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Fonction.
 */
public interface FonctionService {

    /**
     * Save a fonction.
     *
     * @param fonction the entity to save
     * @return the persisted entity
     */
    Fonction save(Fonction fonction);

    /**
     *  Get all the fonctions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Fonction> findAll(Pageable pageable);

    /**
     *  Get the "id" fonction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Fonction findOne(Long id);

    /**
     *  Delete the "id" fonction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the fonction corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Fonction> search(String query, Pageable pageable);
}
