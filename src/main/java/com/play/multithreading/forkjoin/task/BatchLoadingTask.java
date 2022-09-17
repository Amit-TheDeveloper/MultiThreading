package com.play.multithreading.forkjoin.task;

import com.play.multithreading.common.entity.TransactionEntity;
import com.play.multithreading.common.model.gateway.Batch;
import com.play.multithreading.common.model.gateway.Transaction;
import com.play.multithreading.common.model.gateway.updated.PaymentMessage;
import com.play.multithreading.common.repository.TransactionRepository;
import com.play.multithreading.utils.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BatchLoadingTask extends RecursiveTask<Map<String, List<PaymentMessage>>> {

    private int taskWorkload = 0;
    private static final int TASK_THRESHOLD = 24;
    private static int PENDING_TASK_THRESHOLD;

    public LocalDateTime getBatchFrom() {
        return batchFrom;
    }

    public void setBatchFrom(LocalDateTime batchFrom) {
        this.batchFrom = batchFrom;
    }

    public LocalDateTime getBatchTo() {
        return batchTo;
    }

    public void setBatchTo(LocalDateTime batchTo) {
        this.batchTo = batchTo;
    }

    private LocalDateTime batchFrom;
    private LocalDateTime batchTo;
    private List<PaymentMessage> result;
    private Map<String, List<PaymentMessage>> resultMap = new HashMap<String, List<PaymentMessage>>();
    private DataHelper dataHelper;

    private List<Batch> settlementBatchList;

    public BatchLoadingTask(LocalDateTime batchFrom, LocalDateTime batchTo, DataHelper dataHelper, Map<String, List<PaymentMessage>> resultMap) {
        this.settlementBatchList = new ArrayList<Batch>();
        this.dataHelper = dataHelper;
        this.resultMap = resultMap;
        this.batchFrom = batchFrom;
        this.batchTo = batchTo;
        PENDING_TASK_THRESHOLD = PENDING_TASK_THRESHOLD - this.taskWorkload;
        this.taskWorkload = (int)Duration.between(batchTo, batchFrom).toHours();
    }

//    @Override
//    protected Map<String, List<PaymentMessage>> compute() {
//        if(taskWorkload > TASK_THRESHOLD) {
//            System.out.println("Splitting workLoad : " + this.taskWorkload);
//            List<BatchLoadingTask> subtasks =  new ArrayList<>();
//            createSubTasks(subtasks, taskWorkload, LocalDateTime.now(), resultMap);
//            for(BatchLoadingTask subtask : subtasks){
//                subtask.fork();
//            }
//            for(BatchLoadingTask subtask : subtasks) {
//                Map<String, List<PaymentMessage>> tmpres = subtask.join();
//                System.out.print(tmpres);
//            }
//            return resultMap;
//        } else {
//            //result.addAll(loadTransactions().stream().map(x -> toPaymentMessge.apply(x)).collect(Collectors.toList()));
//            // Grouping Map merchant id: List<Payment Message> //execute query to load records to settle. Group the tasks by MID
//            Map<String, List<PaymentMessage>> tmpres = loadTransactions().stream().map(x -> toPaymentMessge.apply(x)).collect(Collectors.groupingBy(PaymentMessage::getMerchantId));
//            for(String key : tmpres.keySet()) {
//                if(resultMap.containsKey(key)) {
//                    resultMap.get(key).addAll(tmpres.get(key));
//                } else {
//                    List<PaymentMessage> pmList = new ArrayList<>();
//                    pmList.addAll(tmpres.get(key));
//                    resultMap.put(key, pmList);
//                }
//            }
//            //Map<String, List<PaymentMessage>> xx = loadTransactions().stream().map(x -> toPaymentMessge.apply(x)).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(PaymentMessage::getMerchantId));
//            return resultMap;
//        }
//        //execute records
//        //Process response
//    }

    @Override
    protected Map<String, List<PaymentMessage>> compute() {
        if(taskWorkload > TASK_THRESHOLD) {
            //check the split logic if required. Currently it is for whole 24 hrs.
            return resultMap;
        } else {
            resultMap.putAll(loadTransactions().stream().map(x -> toPaymentMessge.apply(x)).collect(Collectors.groupingBy(PaymentMessage::getMerchantId)));
            //Map<String, List<PaymentMessage>> xx = loadTransactions().stream().map(x -> toPaymentMessge.apply(x)).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(PaymentMessage::getMerchantId));
            return resultMap;
        }
    }

    Function<TransactionEntity, PaymentMessage> toPaymentMessge = (x) -> {
        PaymentMessage paymentMessage =  PaymentMessage.Builder.getInstance()
                            .withAmount(x.getAmount())
                            .withCardNumber(x.getCardNumber())
                            .withCurrencyCode(x.getCurrencyCode())
                            .withMerchantId(x.getMerchantId())
                            .withMerchantName(x.getMerchantName())
                            .withProcessorId(x.getProcessorId())
                            .withReferenceId(x.getReferenceId())
                            .withSettledAt(x.getSettledAt())
                            .withStatus(x.getStatus())
                            .withTransactedAt(x.getTransactedAt())
                            .withSettlementStatus(x.getSettlementStatus())
                            .build();
                return paymentMessage;
    };

    private List<TransactionEntity> loadTransactions() {
        return dataHelper.findAllWithTransactedAtBetween(batchFrom, batchTo);
    }

//    private List<BatchLoadingTask>  createSubTasks(List<BatchLoadingTask> subTasks, int taskWorkload, LocalDateTime tasksFrom, Map<String, List<PaymentMessage>> resultMap) {
//        int pendingThreshold = taskWorkload - TASK_THRESHOLD;
//        BatchLoadingTask subtask = new BatchLoadingTask(TASK_THRESHOLD, this.dataHelper, resultMap);
//        subtask.batchFrom = tasksFrom.minusHours(taskWorkload);
//        subtask.batchTo = subtask.batchFrom.plusHours(TASK_THRESHOLD);
//        subTasks.add(subtask);
//       if(pendingThreshold >= TASK_THRESHOLD) {
//            createSubTasks(subTasks, pendingThreshold, tasksFrom, resultMap);
//        }
//        return subTasks;
//    }
}
