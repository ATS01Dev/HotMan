package bj.ats.hm.repository.search;

import bj.ats.hm.domain.Fonction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Fonction entity.
 */
public interface FonctionSearchRepository extends ElasticsearchRepository<Fonction, Long> {
}
