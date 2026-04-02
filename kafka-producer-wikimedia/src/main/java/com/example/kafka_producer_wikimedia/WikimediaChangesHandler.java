package com.example.kafka_producer_wikimedia;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class WikimediaChangesHandler implements BackgroundEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesHandler.class);

    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    public WikimediaChangesHandler(String topic, KafkaTemplate<String, String> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void onOpen() throws Exception {
        System.out.println("Connection opened");
    }

    @Override
    public void onClosed() throws Exception {
        System.out.println("Connection closed");
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) throws Exception {
        LOGGER.info(String.format("event data ->%s", messageEvent.getData()));
        System.out.println("Event: " + event);
        System.out.println("Data: " + messageEvent.getData());
        kafkaTemplate.send(topic, messageEvent.getData());

    }

    @Override
    public void onComment(String comment) throws Exception {
        System.out.println("Comment: " + comment);
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Error in EventSource");
        t.printStackTrace();
    }
}