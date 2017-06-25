package bj.ats.hm.web.rest;

import com.codahale.metrics.annotation.Timed;
import bj.ats.hm.domain.Horraires;
import bj.ats.hm.service.HorrairesService;
import bj.ats.hm.web.rest.util.HeaderUtil;
import bj.ats.hm.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Horraires.
 */
@RestController
@RequestMapping("/api")
public class HorrairesResource {

    private final Logger log = LoggerFactory.getLogger(HorrairesResource.class);

    private static final String ENTITY_NAME = "horraires";

    private final HorrairesService horrairesService;

    public HorrairesResource(HorrairesService horrairesService) {
        this.horrairesService = horrairesService;
    }

    /**
     * POST  /horraires : Create a new horraires.
     *
     * @param horraires the horraires to create
     * @return the ResponseEntity with status 201 (Created) and with body the new horraires, or with status 400 (Bad Request) if the horraires has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/horraires")
    @Timed
    public ResponseEntity<Horraires> createHorraires(@RequestBody Horraires horraires) throws URISyntaxException {
        log.debug("REST request to save Horraires : {}", horraires);
        if (horraires.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new horraires cannot already have an ID")).body(null);
        }
        Horraires result = horrairesService.save(horraires);
        return ResponseEntity.created(new URI("/api/horraires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /horraires : Updates an existing horraires.
     *
     * @param horraires the horraires to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated horraires,
     * or with status 400 (Bad Request) if the horraires is not valid,
     * or with status 500 (Internal Server Error) if the horraires couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/horraires")
    @Timed
    public ResponseEntity<Horraires> updateHorraires(@RequestBody Horraires horraires) throws URISyntaxException {
        log.debug("REST request to update Horraires : {}", horraires);
        if (horraires.getId() == null) {
            return createHorraires(horraires);
        }
        Horraires result = horrairesService.save(horraires);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, horraires.getId().toString()))
            .body(result);
    }

    /**
     * GET  /horraires : get all the horraires.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of horraires in body
     */
    @GetMapping("/horraires")
    @Timed
    public ResponseEntity<List<Horraires>> getAllHorraires(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Horraires");
        Page<Horraires> page = horrairesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/horraires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /horraires/:id : get the "id" horraires.
     *
     * @param id the id of the horraires to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the horraires, or with status 404 (Not Found)
     */
    @GetMapping("/horraires/{id}")
    @Timed
    public ResponseEntity<Horraires> getHorraires(@PathVariable Long id) {
        log.debug("REST request to get Horraires : {}", id);
        Horraires horraires = horrairesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(horraires));
    }

    /**
     * DELETE  /horraires/:id : delete the "id" horraires.
     *
     * @param id the id of the horraires to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/horraires/{id}")
    @Timed
    public ResponseEntity<Void> deleteHorraires(@PathVariable Long id) {
        log.debug("REST request to delete Horraires : {}", id);
        horrairesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/horraires?query=:query : search for the horraires corresponding
     * to the query.
     *
     * @param query the query of the horraires search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/horraires")
    @Timed
    public ResponseEntity<List<Horraires>> searchHorraires(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Horraires for query {}", query);
        Page<Horraires> page = horrairesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/horraires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
