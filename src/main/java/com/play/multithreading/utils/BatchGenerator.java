package com.play.multithreading.utils;

import com.play.multithreading.common.model.gateway.Batch;
import com.play.multithreading.common.model.gateway.BatchStatus;
import com.play.multithreading.common.model.gateway.updated.PaymentMessage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class BatchGenerator {

    int BATCH_SIZE = 10;
    static final AtomicInteger BATCH_NUMBER_SEED = new AtomicInteger(1);

    private static int getBatchNumber() {
        if(BATCH_NUMBER_SEED.get() == 999) {
            BATCH_NUMBER_SEED.set(1);
            return BATCH_NUMBER_SEED.getAndIncrement();
        } else {
            return BATCH_NUMBER_SEED.getAndIncrement();
        }
    }

    public List<Batch> generateBatches(Map<String, List<PaymentMessage>> paymentMessgaesMap) {
        final AtomicInteger counter = new AtomicInteger();
        List<Batch> batches = new ArrayList<>();
        for(String key : paymentMessgaesMap.keySet()) {
            final Collection<List<PaymentMessage>> toSettleRecords = paymentMessgaesMap.get(key).stream()
                    .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / BATCH_SIZE))
                    .values();
            Batch batch = new Batch.Builder()
                             .setBatchNumber(String.format("%03d", getBatchNumber()))
                             .setBatchStatus(BatchStatus.PREPARED)
                             .setMerchantId(key)
                             .setProcessorId(toSettleRecords.stream().findFirst().get().get(0).getProcessorId())
                             .setSettlementRecords(toSettleRecords)
                             .build();
            batches.add(batch);
        }
        System.out.print("Total batches################" + batches.size());
        for(Batch b : batches) {
            System.out.println("Total batches numbers################" + b.getSettlementRecords().size());
        }
        return batches;
    }
}
