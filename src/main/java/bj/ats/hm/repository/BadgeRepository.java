package bj.ats.hm.repository;

import bj.ats.hm.domain.Badge;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Badge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeRepository extends JpaRepository<Badge,Long> {
    
}
