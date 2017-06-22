package bj.ats.hm.repository.search;

import bj.ats.hm.domain.Groupe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Groupe entity.
 */
public interface GroupeSearchRepository extends ElasticsearchRepository<Groupe, Long> {
}
