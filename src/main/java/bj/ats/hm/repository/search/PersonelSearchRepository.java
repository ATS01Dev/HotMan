package bj.ats.hm.repository.search;

import bj.ats.hm.domain.Personel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Personel entity.
 */
public interface PersonelSearchRepository extends ElasticsearchRepository<Personel, Long> {
}
