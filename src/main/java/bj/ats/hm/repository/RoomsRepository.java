package bj.ats.hm.repository;

import bj.ats.hm.domain.Rooms;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Rooms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomsRepository extends JpaRepository<Rooms,Long> {
    
}
