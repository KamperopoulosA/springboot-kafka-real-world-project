package com.example.kafka_producer_wikimedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class KafkaProducerWikimediaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerWikimediaApplication.class, args);
	}

    @Autowired
    private WikimediaChangesProducer wikimediaChangesProducer;

    @Override
    public void run(String... args) throws Exception {
        wikimediaChangesProducer.sendMessage();

        // keep JVM alive so the Wikimedia stream can continue running
        new CountDownLatch(1).await();
    }
}
