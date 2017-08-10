package bj.ats.hm.repository;

import bj.ats.hm.domain.Rooms;
import bj.ats.hm.domain.enumeration.Etatromms;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Rooms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomsRepository extends JpaRepository<Rooms,Long> {

    List<Rooms> findAllByEtat(Etatromms etatromms);
}
