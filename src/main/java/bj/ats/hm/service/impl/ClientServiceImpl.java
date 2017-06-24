package bj.ats.hm.service.impl;

import bj.ats.hm.domain.Rooms;
import bj.ats.hm.domain.enumeration.Etatromms;
import bj.ats.hm.service.ClientService;
import bj.ats.hm.domain.Client;
import bj.ats.hm.repository.ClientRepository;
import bj.ats.hm.repository.search.ClientSearchRepository;
import bj.ats.hm.service.RoomsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Client.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService{

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    private final ClientSearchRepository clientSearchRepository;
    private final RoomsService roomsService;

    public ClientServiceImpl(ClientRepository clientRepository, ClientSearchRepository clientSearchRepository, RoomsService roomsService) {
        this.clientRepository = clientRepository;
        this.clientSearchRepository = clientSearchRepository;
        this.roomsService = roomsService;
    }

    /**
     * Save a client.
     *
     * @param client the entity to save
     * @return the persisted entity
     */
    @Override
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        Client result = clientRepository.save(client);
        Rooms rooms=client.getRoom();
        roomsService.save(rooms.etat(Etatromms.OCCUPE));
        clientSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the clients.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    /**
     *  Get one client by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Client findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findOne(id);
    }

    /**
     *  Delete the  client by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.delete(id);
        clientSearchRepository.delete(id);
    }

    /**
     * Search for the client corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Client> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Clients for query {}", query);
        Page<Client> result = clientSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    @Override
    public long Count() {
        return clientRepository.count();
    }

    @Override
    public List<Client> findAllByOrderByDatecome() {
        return clientRepository.findAllByOrderByDatecomeDesc();
    }
}
