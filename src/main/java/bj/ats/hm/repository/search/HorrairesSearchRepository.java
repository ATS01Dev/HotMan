package bj.ats.hm.repository.search;

import bj.ats.hm.domain.Horraires;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Horraires entity.
 */
public interface HorrairesSearchRepository extends ElasticsearchRepository<Horraires, Long> {
}
