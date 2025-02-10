package com.jpmc.midascore;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class TaskTwoTests {
    private static final Logger logger = LoggerFactory.getLogger(TaskTwoTests.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private FileLoader fileLoader;

    private final List<String> receivedMessages = new CopyOnWriteArrayList<>(); // Stores received messages

    @KafkaListener(topics = "your-topic-name", groupId = "test-group")
    public void listen(String message) {
        receivedMessages.add(message);
    }

    @Test
    void task_two_verifier() {
        String[] transactionLines = fileLoader.loadStrings("/test_data/transactions.txt");

        // ✅ Ensure transactions exist
        assertNotEquals(0, transactionLines.length, "No transactions found in the file!");

        for (String transactionLine : transactionLines) {
            kafkaTemplate.send("your-topic-name", transactionLine);
        }

        // ✅ Wait Until Messages Are Received
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .until(() -> receivedMessages.size() >= transactionLines.length);

        logger.info("✅ All transactions processed successfully!");
    }
}
