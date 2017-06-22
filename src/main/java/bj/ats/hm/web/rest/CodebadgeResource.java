package bj.ats.hm.web.rest;

import com.codahale.metrics.annotation.Timed;
import bj.ats.hm.domain.Codebadge;
import bj.ats.hm.service.CodebadgeService;
import bj.ats.hm.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Codebadge.
 */
@RestController
@RequestMapping("/api")
public class CodebadgeResource {

    private final Logger log = LoggerFactory.getLogger(CodebadgeResource.class);

    private static final String ENTITY_NAME = "codebadge";

    private final CodebadgeService codebadgeService;

    public CodebadgeResource(CodebadgeService codebadgeService) {
        this.codebadgeService = codebadgeService;
    }

    /**
     * POST  /codebadges : Create a new codebadge.
     *
     * @param codebadge the codebadge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new codebadge, or with status 400 (Bad Request) if the codebadge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/codebadges")
    @Timed
    public ResponseEntity<Codebadge> createCodebadge(@RequestBody Codebadge codebadge) throws URISyntaxException {
        log.debug("REST request to save Codebadge : {}", codebadge);
        if (codebadge.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new codebadge cannot already have an ID")).body(null);
        }
        Codebadge result = codebadgeService.save(codebadge);
        return ResponseEntity.created(new URI("/api/codebadges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /codebadges : Updates an existing codebadge.
     *
     * @param codebadge the codebadge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated codebadge,
     * or with status 400 (Bad Request) if the codebadge is not valid,
     * or with status 500 (Internal Server Error) if the codebadge couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/codebadges")
    @Timed
    public ResponseEntity<Codebadge> updateCodebadge(@RequestBody Codebadge codebadge) throws URISyntaxException {
        log.debug("REST request to update Codebadge : {}", codebadge);
        if (codebadge.getId() == null) {
            return createCodebadge(codebadge);
        }
        Codebadge result = codebadgeService.save(codebadge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, codebadge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /codebadges : get all the codebadges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of codebadges in body
     */
    @GetMapping("/codebadges")
    @Timed
    public List<Codebadge> getAllCodebadges() {
        log.debug("REST request to get all Codebadges");
        return codebadgeService.findAll();
    }

    /**
     * GET  /codebadges/:id : get the "id" codebadge.
     *
     * @param id the id of the codebadge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the codebadge, or with status 404 (Not Found)
     */
    @GetMapping("/codebadges/{id}")
    @Timed
    public ResponseEntity<Codebadge> getCodebadge(@PathVariable Long id) {
        log.debug("REST request to get Codebadge : {}", id);
        Codebadge codebadge = codebadgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(codebadge));
    }

    /**
     * DELETE  /codebadges/:id : delete the "id" codebadge.
     *
     * @param id the id of the codebadge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/codebadges/{id}")
    @Timed
    public ResponseEntity<Void> deleteCodebadge(@PathVariable Long id) {
        log.debug("REST request to delete Codebadge : {}", id);
        codebadgeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/codebadges?query=:query : search for the codebadge corresponding
     * to the query.
     *
     * @param query the query of the codebadge search
     * @return the result of the search
     */
    @GetMapping("/_search/codebadges")
    @Timed
    public List<Codebadge> searchCodebadges(@RequestParam String query) {
        log.debug("REST request to search Codebadges for query {}", query);
        return codebadgeService.search(query);
    }

}
