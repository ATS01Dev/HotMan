package bj.ats.hm.repository.search;

import bj.ats.hm.domain.Codebadge;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Codebadge entity.
 */
public interface CodebadgeSearchRepository extends ElasticsearchRepository<Codebadge, Long> {
}
