package bj.ats.hm.repository;

import bj.ats.hm.domain.Codebadge;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Codebadge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodebadgeRepository extends JpaRepository<Codebadge,Long> {
    
}
