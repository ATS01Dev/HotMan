package bj.ats.hm.service;

import bj.ats.hm.domain.Codebadge;
import java.util.List;

/**
 * Service Interface for managing Codebadge.
 */
public interface CodebadgeService {

    /**
     * Save a codebadge.
     *
     * @param codebadge the entity to save
     * @return the persisted entity
     */
    Codebadge save(Codebadge codebadge);

    /**
     *  Get all the codebadges.
     *
     *  @return the list of entities
     */
    List<Codebadge> findAll();

    /**
     *  Get the "id" codebadge.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Codebadge findOne(Long id);

    /**
     *  Delete the "id" codebadge.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the codebadge corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Codebadge> search(String query);
}
