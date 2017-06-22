package bj.ats.hm.service.impl;

import bj.ats.hm.service.GroupeService;
import bj.ats.hm.domain.Groupe;
import bj.ats.hm.repository.GroupeRepository;
import bj.ats.hm.repository.search.GroupeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Groupe.
 */
@Service
@Transactional
public class GroupeServiceImpl implements GroupeService{

    private final Logger log = LoggerFactory.getLogger(GroupeServiceImpl.class);

    private final GroupeRepository groupeRepository;

    private final GroupeSearchRepository groupeSearchRepository;

    public GroupeServiceImpl(GroupeRepository groupeRepository, GroupeSearchRepository groupeSearchRepository) {
        this.groupeRepository = groupeRepository;
        this.groupeSearchRepository = groupeSearchRepository;
    }

    /**
     * Save a groupe.
     *
     * @param groupe the entity to save
     * @return the persisted entity
     */
    @Override
    public Groupe save(Groupe groupe) {
        log.debug("Request to save Groupe : {}", groupe);
        Groupe result = groupeRepository.save(groupe);
        groupeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the groupes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Groupe> findAll() {
        log.debug("Request to get all Groupes");
        return groupeRepository.findAll();
    }

    /**
     *  Get one groupe by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Groupe findOne(Long id) {
        log.debug("Request to get Groupe : {}", id);
        return groupeRepository.findOne(id);
    }

    /**
     *  Delete the  groupe by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Groupe : {}", id);
        groupeRepository.delete(id);
        groupeSearchRepository.delete(id);
    }

    /**
     * Search for the groupe corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Groupe> search(String query) {
        log.debug("Request to search Groupes for query {}", query);
        return StreamSupport
            .stream(groupeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
