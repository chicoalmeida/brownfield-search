package com.brownfield.search.controller;

import com.brownfield.search.entity.Flight;
import com.brownfield.search.model.SearchQuery;
import com.brownfield.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RefreshScope
@CrossOrigin
@RestController
@RequestMapping(SearchController.SEARCH_ENDPOINT)
@Slf4j
public class SearchController {
    public static final String SEARCH_ENDPOINT = "/search";
    private SearchService searchService;
    @Value("${orginairports.shutdown}")
    private String originAirportShutdownList;


    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity<List<Flight>> search(@RequestBody SearchQuery query) {
        log.info("Input : " + query);
        if (Arrays.asList(originAirportShutdownList.split(",")).contains(query.getOrigin())) {
            String message = "The origin airport is in shutdown state";
            log.info(message);
            return new ResponseEntity<>(BAD_REQUEST);
        }

        return new ResponseEntity<>(searchService.search(query), OK);
    }

}
