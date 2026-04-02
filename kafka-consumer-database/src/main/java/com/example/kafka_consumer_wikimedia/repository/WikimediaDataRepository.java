package com.example.kafka_consumer_wikimedia.repository;

import com.example.kafka_consumer_wikimedia.entity.WikimediaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikimediaDataRepository  extends JpaRepository<WikimediaData, Long> {
}
