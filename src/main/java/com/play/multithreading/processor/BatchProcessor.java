package com.play.multithreading.processor;

import com.play.multithreading.common.entity.BatchEntity;
import com.play.multithreading.common.model.gateway.Batch;
import com.play.multithreading.common.model.gateway.BatchStatus;
import com.play.multithreading.common.model.gateway.updated.PaymentMessage;
import com.play.multithreading.common.model.gateway.updated.SettlementStatus;
import com.play.multithreading.common.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class BatchProcessor {

    @Autowired
    BatchRepository repository;

    public void processBatch(List<Batch> batches) {
        transportToAce(batches);
        updateBatchStatus(batches);
        updateSOR(batches);
    }

    Function<Batch, BatchEntity> toBatchEntity = x -> {
      BatchEntity entity = new BatchEntity();
        entity.setBatchNumber(x.getBatchNumber());
        entity.setBatchStatus(x.getBatchStatus().name());
        entity.setProcessorId(x.getProcessorId());
        entity.setMerchantId(x.getMerchantId());
        entity.setEndTime(x.getEndTime());
        entity.setStartTime(x.getStartTime());
        entity.setNumberOfSettledRecords(getCountOf(x.getSettlementRecords()));
      return entity;
    };

    private int getCountOf(Collection<List<PaymentMessage>> settlementRecords) {
        List<PaymentMessage> pmList = null;
        if(settlementRecords.stream().findFirst().isPresent()) {
            pmList = settlementRecords.stream().findFirst().get();
            return pmList.size();
        }
        return 0;
    }

    private void updateSOR(List<Batch> batches) {
        List<BatchEntity> batchEntities = batches.stream().map(x -> toBatchEntity.apply(x)).collect(Collectors.toList());
        repository.saveAll(batchEntities);
    }

    private void updateBatchStatus(List<Batch> batches) {
        batches.stream().forEach(batch -> {
                                            Optional<PaymentMessage> failedRecord = batch.getSettlementRecordsResponse().stream().
                                                    filter(x -> x.getSettlementStatus().
                                                    equals(SettlementStatus.FAILED)).findFirst();
                                            if(failedRecord.isPresent()) {
                                                batch.setBatchStatus(BatchStatus.FAILED);
                                            } else {
                                                batch.setBatchStatus(BatchStatus.SUCCESS);
                                            }
                                            batch.setStartTime(LocalDateTime.now());
                                            batch.setEndTime(LocalDateTime.now());
                                      });
    }

    private void transportToAce(List<Batch> batches) {
            // connect to Ace and send batches for processing
            // receives updated batches with individual record status and batch status
            // Assign to Response Processor and update records in DB

            sendToAce(batches);
    }

    private void sendToAce(List<Batch> batches) {
        // request received in Ace and processing those
        System.out.println("Batch Requests received in Ace");
        batches.stream().forEach(batch -> batch.getSettlementRecords().forEach(sRecord -> batch.getSettlementRecordsResponse().addAll((processRecords(batch, sRecord)))));

        System.out.println("Batch Requests after processing by Ace################");
        batches.forEach(System.out::println);
    }

    Function<PaymentMessage, PaymentMessage> settleRecord = x -> (PaymentMessage.Builder.from(x).
                                                                                withSettlementStatus(SettlementStatus.SUCCESS).
                                                                                withSettledAt(LocalDateTime.now()).build());
    private List<PaymentMessage> processRecords(Batch batch, List<PaymentMessage> sRecord) {
         return sRecord.stream().map(x -> settleRecord.apply(x)).collect(Collectors.toList());
    }

}
