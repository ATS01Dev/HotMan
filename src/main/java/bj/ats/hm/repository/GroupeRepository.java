package bj.ats.hm.repository;

import bj.ats.hm.domain.Groupe;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Groupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupeRepository extends JpaRepository<Groupe,Long> {
    
}
