package bj.ats.hm.repository;

import bj.ats.hm.domain.Horraires;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Horraires entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorrairesRepository extends JpaRepository<Horraires,Long> {
    
}
