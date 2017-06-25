package bj.ats.hm.service.impl;

import bj.ats.hm.service.FonctionService;
import bj.ats.hm.domain.Fonction;
import bj.ats.hm.repository.FonctionRepository;
import bj.ats.hm.repository.search.FonctionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Fonction.
 */
@Service
@Transactional
public class FonctionServiceImpl implements FonctionService{

    private final Logger log = LoggerFactory.getLogger(FonctionServiceImpl.class);

    private final FonctionRepository fonctionRepository;

    private final FonctionSearchRepository fonctionSearchRepository;

    public FonctionServiceImpl(FonctionRepository fonctionRepository, FonctionSearchRepository fonctionSearchRepository) {
        this.fonctionRepository = fonctionRepository;
        this.fonctionSearchRepository = fonctionSearchRepository;
    }

    /**
     * Save a fonction.
     *
     * @param fonction the entity to save
     * @return the persisted entity
     */
    @Override
    public Fonction save(Fonction fonction) {
        log.debug("Request to save Fonction : {}", fonction);
        Fonction result = fonctionRepository.save(fonction);
        fonctionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the fonctions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Fonction> findAll(Pageable pageable) {
        log.debug("Request to get all Fonctions");
        return fonctionRepository.findAll(pageable);
    }

    /**
     *  Get one fonction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Fonction findOne(Long id) {
        log.debug("Request to get Fonction : {}", id);
        return fonctionRepository.findOne(id);
    }

    /**
     *  Delete the  fonction by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fonction : {}", id);
        fonctionRepository.delete(id);
        fonctionSearchRepository.delete(id);
    }

    /**
     * Search for the fonction corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Fonction> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fonctions for query {}", query);
        Page<Fonction> result = fonctionSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
