package com.play.multithreading.utils;

import com.play.multithreading.common.entity.TransactionEntity;
import com.play.multithreading.common.model.gateway.Batch;
import com.play.multithreading.common.model.gateway.Transaction;
import com.play.multithreading.common.model.gateway.updated.PaymentMessage;
import com.play.multithreading.forkjoin.task.BatchLoadingTask;
import com.play.multithreading.processor.BatchProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

@Component
public class DataSeeder implements CommandLineRunner{

    @Autowired
    DataHelper dataHelper;

    @Autowired
    BatchGenerator batchGenerator;

    @Autowired
    BatchProcessor batchProcessor;

    @Override
    public void run(String... args) throws InterruptedException {
        List<Transaction> txns = dataHelper.createData(50);
        dataHelper.saveRecords(txns);

        List<TransactionEntity> ent = dataHelper.fetchRecords();
        System.out.println("fetching records ###  Please check" + ent.size());
        ent.forEach(System.out::println);

        ForkJoinPool pool = ForkJoinPool.commonPool();
        BatchLoadingTask batchLoadingTask = new BatchLoadingTask(LocalDateTime.now().minusHours(24), LocalDateTime.now(), dataHelper, new HashMap<String, List<PaymentMessage>>());

        Map<String, List<PaymentMessage>> result= pool.invoke(batchLoadingTask);

        if(batchLoadingTask.isDone()) {
            List<Batch> toSettleBatch  = batchGenerator.generateBatches(result);
            batchProcessor.processBatch(toSettleBatch);
         System.out.println("Batches are .......  ");
            toSettleBatch.stream().forEach(System.out::println);
        }

        System.out.println("Here are the results ***************");
        System.out.print(result.toString());


    }
}
