package com.play.multithreading.forkjoin.task;

import com.play.multithreading.common.model.gateway.Batch;
import com.play.multithreading.processor.BatchProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class BatchProcessingTask extends RecursiveTask<List<Batch>> {

    private static final int BATCH_PROCEESING_THRESHOLD = 1;
    List<Batch> toSettleBatchList;
    private int taskWorkload = toSettleBatchList.size();

    @Autowired
    BatchProcessor batchProcessor;

    public BatchProcessingTask(List<Batch> toSettleBatchList) {
        this.toSettleBatchList = toSettleBatchList;
    }

    @Override
    protected List<Batch> compute() {
        if(taskWorkload > BATCH_PROCEESING_THRESHOLD) {
            System.out.println("Splitting batch processing workLoad : " + (toSettleBatchList.size() - this.taskWorkload));
            createSubTasks(toSettleBatchList);
        } else {
            batchProcessor.processBatch(toSettleBatchList);
        }
            return this.toSettleBatchList;
    }

    private List<BatchProcessingTask> createSubTasks(int startIndex) {
        BatchProcessingTask subtask = new BatchProcessingTask(toSettleBatchList.subList(startIndex, (startIndex + BATCH_PROCEESING_THRESHOLD - 1)));
        if(pendingWorkload > taskWorkload) {
            createSubTasks( startIndex + BATCH_PROCEESING_THRESHOLD);
        }
        return subTasks;
    }
}
