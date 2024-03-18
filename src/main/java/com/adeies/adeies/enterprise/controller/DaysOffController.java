package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.dto.daysOff.InitializeDaysOffRq;
import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.entities.SuccessResponse;
import com.adeies.adeies.enterprise.repository.DaysOffDefinitionRepo;
import com.adeies.adeies.enterprise.service.DaysOffService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1")
public class DaysOffController {
    private final int NUMBER_OF_USERS = 110000;
    @Autowired
    private DaysOffService daysOffService;
    @Autowired
    private DaysOffDefinitionRepo daysOffDefinitionRepo;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/initializeDaysOff")
    public ResponseEntity<SuccessResponse> initializeDaysOff(@RequestBody InitializeDaysOffRq rq) {
        daysOffService.initializeDaysOff(rq);
        return ResponseEntity.ok(new SuccessResponse("Days off successfully registered.", null));
    }

    @GetMapping("/agg")
    public ResponseEntity<SuccessResponse> agg() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        AtomicInteger idGenerator = new AtomicInteger(1000);

        try {
            for (int i = 0; i < 12; i++) {
                executorService.submit(() -> {
                    int startId = idGenerator.getAndAdd(NUMBER_OF_USERS);

                    try (Connection conn = jdbcTemplate.getDataSource()
                                                       .getConnection(); PreparedStatement dayOffPs = conn.prepareStatement(
                            "insert into days_off_definition (id, description, date_create, user_create) values (?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS); PreparedStatement testakiPs = conn.prepareStatement(
                            "insert into testaki (year, brand, date_create, user_create) values (?, ?, ?, ?)")) {

                        int batchSize = 11000; // Adjust the batch size as needed
                        int batchCount = 0;

                        for (int j = 0; j < NUMBER_OF_USERS; j++) {
                            int id = startId + j;

                            // Set the dayOff name parameter and add to the batch
                            dayOffPs.setLong(1, id);
                            dayOffPs.setString(2, "DaysOffDefinition " + id);
                            dayOffPs.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                            dayOffPs.setString(4, "Tsoni");
                            dayOffPs.addBatch();

                            testakiPs.setLong(1, id);
                            testakiPs.setString(2, "DaysOffDefinition " + id);
                            testakiPs.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                            testakiPs.setString(4, "Tsoni");
                            testakiPs.addBatch();

                            batchCount++;

                            if (batchCount % batchSize == 0) {
                                // Execute the batch update within the thread
                                dayOffPs.executeBatch();
                                testakiPs.executeBatch();
                                dayOffPs.clearBatch();
                                testakiPs.clearBatch();
                            }
                        }

                        // Execute any remaining statements in the batch
                        if (batchCount % batchSize != 0) {
                            dayOffPs.executeBatch();
                            testakiPs.executeBatch();
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });
            }
            // Shutdown the executor service and wait for tasks to complete
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // Ensure the executor service is properly shut down
            if (!executorService.isShutdown()) {
                executorService.shutdownNow();
            }
        }

        return ResponseEntity.ok(new SuccessResponse("Days off successfully registered.", null));
    }

    @GetMapping("/multi")
    public ResponseEntity<SuccessResponse> multi() {
        int numThreads = 4; // Number of threads to use
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();

            // Create a prepared statement for inserting dayOffs
            PreparedStatement dayOffPs = conn.prepareStatement(
                    "insert into days_off_definition (id, description) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            // Create a prepared statement for inserting emails
            PreparedStatement testakiPs = conn.prepareStatement(
                    "insert into testaki (year, brand) values (?, ?)");

            long startThreadTime = System.currentTimeMillis(); // Start the timer
            for (int i = 1000; i < NUMBER_OF_USERS; i++) {
                final int index = i;
                executorService.submit(() -> {
                    try {
                        // Set the dayOff name parameter and add to the batch
                        dayOffPs.setLong(1, Long.parseLong(String.valueOf(index)));
                        dayOffPs.setString(2, "DaysOffDefinition " + index);
                        dayOffPs.addBatch();
                        testakiPs.setLong(1, Long.parseLong(String.valueOf(index)));
                        testakiPs.setString(2, "DaysOffDefinition " + index);
                        testakiPs.addBatch();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });
            }

            // Wait for all tasks to complete
            executorService.shutdown();
            boolean tasksCompleted = executorService.awaitTermination(1, TimeUnit.MINUTES);

            if (tasksCompleted) {
                long endThreadTime = System.currentTimeMillis(); // Start the timer
                long startTime = System.currentTimeMillis(); // Start the timer
                for (int i = 1000; i < NUMBER_OF_USERS; i++) {
                    try {
                        // Set the dayOff name parameter and add to the batch
                        dayOffPs.setLong(1, Long.parseLong(String.valueOf(i)));
                        dayOffPs.setString(2, "DaysOffDefinition " + i);
                        dayOffPs.addBatch();
                        testakiPs.setLong(1, Long.parseLong(String.valueOf(i)));
                        testakiPs.setString(2, "DaysOffDefinition " + i);
                        testakiPs.addBatch();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                long endTime = System.currentTimeMillis(); // Start the timer
                long executionTime = endTime - startTime;
                long executionThreadTime = endThreadTime - startThreadTime;
                System.out.println(
                        "Thread Execution time: " + executionThreadTime + " milliseconds");
                System.out.println("Execution time: " + executionTime + " milliseconds");
                testakiPs.close();
                dayOffPs.close();
                conn.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // Ensure the executor service is properly shut down
            if (!executorService.isShutdown()) {
                executorService.shutdownNow();
            }
        }
        return ResponseEntity.ok(new SuccessResponse("Days off successfully registered.", null));
    }

    @GetMapping("/noAgg")
    public ResponseEntity<SuccessResponse> noAgg() {
        List<DaysOffDefinition> data = daysOffDefinitionRepo.customJavaJoin();
        return ResponseEntity.ok(new SuccessResponse("Days off successfully registered.", data));
    }
}