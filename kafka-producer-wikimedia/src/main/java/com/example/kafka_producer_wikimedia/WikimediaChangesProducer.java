package com.example.kafka_producer_wikimedia;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import okhttp3.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private static final String TOPIC = "wikimedia_recentchange";
    private static final String WIKIMEDIA_STREAM_URL =
            "https://stream.wikimedia.org/v2/stream/recentchange";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() {
        BackgroundEventHandler eventHandler =
                new WikimediaChangesHandler(TOPIC, kafkaTemplate);

        Headers headers = new Headers.Builder()
                .add("User-Agent", "kafka-producer-wikimedia/1.0 (contact: your-email@example.com)")
                .build();

        BackgroundEventSource eventSource = new BackgroundEventSource.Builder(
                eventHandler,
                new EventSource.Builder(
                        ConnectStrategy.http(URI.create(WIKIMEDIA_STREAM_URL))
                                .headers(headers)
                )
        ).build();

        LOGGER.info("Starting Wikimedia stream...");
        eventSource.start();
    }
}