package bj.ats.hm.repository;

import bj.ats.hm.domain.Client;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    List<Client> findAllByOrderByDatecomeAsc();
    //List<Client> findAllByOrderByDatecomeDsc();

    List<Client> findAllByOrderByDatecomeDesc();
}
