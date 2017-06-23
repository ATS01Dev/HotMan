package bj.ats.hm.service;

import bj.ats.hm.domain.Horraires;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Horraires.
 */
public interface HorrairesService {

    /**
     * Save a horraires.
     *
     * @param horraires the entity to save
     * @return the persisted entity
     */
    Horraires save(Horraires horraires);

    /**
     *  Get all the horraires.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Horraires> findAll(Pageable pageable);

    /**
     *  Get the "id" horraires.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Horraires findOne(Long id);

    /**
     *  Delete the "id" horraires.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the horraires corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Horraires> search(String query, Pageable pageable);
}
