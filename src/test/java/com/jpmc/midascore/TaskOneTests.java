package com.jpmc.midascore;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MidasCoreApplication.class)
public class TaskOneTests {

    private static final Logger logger = LoggerFactory.getLogger(TaskOneTests.class);

    @Test
    void taskOneVerifier() throws InterruptedException {
        Thread.sleep(2000);
        logger.info("----------------------------------------------------------");
        logger.info("Congrats! It looks like your application booted without issue.");
        logger.info("Submit the following output to complete the task (include begin and end output denotations)");

        StringBuilder output = new StringBuilder("\n").append("---begin output ---\n");
        for (int i = 0; i < 10; i++) {
            output.append((int) Math.pow(i, i)).append("\n");
        }
        output.append("---end output ---");

        logger.info(output.toString());
    }
}
