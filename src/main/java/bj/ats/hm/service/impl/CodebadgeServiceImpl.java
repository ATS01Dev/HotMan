package bj.ats.hm.service.impl;

import bj.ats.hm.service.CodebadgeService;
import bj.ats.hm.domain.Codebadge;
import bj.ats.hm.repository.CodebadgeRepository;
import bj.ats.hm.repository.search.CodebadgeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Codebadge.
 */
@Service
@Transactional
public class CodebadgeServiceImpl implements CodebadgeService{

    private final Logger log = LoggerFactory.getLogger(CodebadgeServiceImpl.class);

    private final CodebadgeRepository codebadgeRepository;

    private final CodebadgeSearchRepository codebadgeSearchRepository;

    public CodebadgeServiceImpl(CodebadgeRepository codebadgeRepository, CodebadgeSearchRepository codebadgeSearchRepository) {
        this.codebadgeRepository = codebadgeRepository;
        this.codebadgeSearchRepository = codebadgeSearchRepository;
    }

    /**
     * Save a codebadge.
     *
     * @param codebadge the entity to save
     * @return the persisted entity
     */
    @Override
    public Codebadge save(Codebadge codebadge) {
        log.debug("Request to save Codebadge : {}", codebadge);
        Codebadge result = codebadgeRepository.save(codebadge);
        codebadgeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the codebadges.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Codebadge> findAll() {
        log.debug("Request to get all Codebadges");
        return codebadgeRepository.findAll();
    }

    /**
     *  Get one codebadge by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Codebadge findOne(Long id) {
        log.debug("Request to get Codebadge : {}", id);
        return codebadgeRepository.findOne(id);
    }

    /**
     *  Delete the  codebadge by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Codebadge : {}", id);
        codebadgeRepository.delete(id);
        codebadgeSearchRepository.delete(id);
    }

    /**
     * Search for the codebadge corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Codebadge> search(String query) {
        log.debug("Request to search Codebadges for query {}", query);
        return StreamSupport
            .stream(codebadgeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
