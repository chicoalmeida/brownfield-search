package com.brownfield.search.consumer;



import com.brownfield.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableBinding(SearchSink.class)
public class SearchReceiver {

    @Autowired
    private SearchService searchService;

    @ServiceActivator(inputChannel = SearchSink.QUEUE_NAME)
    public void accept(final Map<String, Object> fare) {
        searchService.updateInventory((String) fare.get("FLIGHT_NUMBER"), (String) fare.get("FLIGHT_DATE"), (int) fare.get("NEW_INVENTORY"));
    }
}
