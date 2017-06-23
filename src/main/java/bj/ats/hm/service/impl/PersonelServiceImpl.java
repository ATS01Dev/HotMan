package bj.ats.hm.service.impl;

import bj.ats.hm.service.PersonelService;
import bj.ats.hm.domain.Personel;
import bj.ats.hm.repository.PersonelRepository;
import bj.ats.hm.repository.search.PersonelSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Personel.
 */
@Service
@Transactional
public class PersonelServiceImpl implements PersonelService{

    private final Logger log = LoggerFactory.getLogger(PersonelServiceImpl.class);

    private final PersonelRepository personelRepository;

    private final PersonelSearchRepository personelSearchRepository;

    public PersonelServiceImpl(PersonelRepository personelRepository, PersonelSearchRepository personelSearchRepository) {
        this.personelRepository = personelRepository;
        this.personelSearchRepository = personelSearchRepository;
    }

    /**
     * Save a personel.
     *
     * @param personel the entity to save
     * @return the persisted entity
     */
    @Override
    public Personel save(Personel personel) {
        log.debug("Request to save Personel : {}", personel);
        Personel result = personelRepository.save(personel);
        personelSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the personels.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Personel> findAll(Pageable pageable) {
        log.debug("Request to get all Personels");
        return personelRepository.findAll(pageable);
    }

    /**
     *  Get one personel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Personel findOne(Long id) {
        log.debug("Request to get Personel : {}", id);
        return personelRepository.findOne(id);
    }

    /**
     *  Delete the  personel by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personel : {}", id);
        personelRepository.delete(id);
        personelSearchRepository.delete(id);
    }

    /**
     * Search for the personel corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Personel> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Personels for query {}", query);
        Page<Personel> result = personelSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
