package bj.ats.hm.service.impl;

import bj.ats.hm.service.HorrairesService;
import bj.ats.hm.domain.Horraires;
import bj.ats.hm.repository.HorrairesRepository;
import bj.ats.hm.repository.search.HorrairesSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Horraires.
 */
@Service
@Transactional
public class HorrairesServiceImpl implements HorrairesService{

    private final Logger log = LoggerFactory.getLogger(HorrairesServiceImpl.class);

    private final HorrairesRepository horrairesRepository;

    private final HorrairesSearchRepository horrairesSearchRepository;

    public HorrairesServiceImpl(HorrairesRepository horrairesRepository, HorrairesSearchRepository horrairesSearchRepository) {
        this.horrairesRepository = horrairesRepository;
        this.horrairesSearchRepository = horrairesSearchRepository;
    }

    /**
     * Save a horraires.
     *
     * @param horraires the entity to save
     * @return the persisted entity
     */
    @Override
    public Horraires save(Horraires horraires) {
        log.debug("Request to save Horraires : {}", horraires);
        Horraires result = horrairesRepository.save(horraires);
        horrairesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the horraires.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Horraires> findAll(Pageable pageable) {
        log.debug("Request to get all Horraires");
        return horrairesRepository.findAll(pageable);
    }

    /**
     *  Get one horraires by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Horraires findOne(Long id) {
        log.debug("Request to get Horraires : {}", id);
        return horrairesRepository.findOne(id);
    }

    /**
     *  Delete the  horraires by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Horraires : {}", id);
        horrairesRepository.delete(id);
        horrairesSearchRepository.delete(id);
    }

    /**
     * Search for the horraires corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Horraires> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Horraires for query {}", query);
        Page<Horraires> result = horrairesSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
