package com.brownfield.search.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface SearchSink {

    String QUEUE_NAME = "invetoryQ";

    @Input(SearchSink.QUEUE_NAME)
    MessageChannel channel();
}
