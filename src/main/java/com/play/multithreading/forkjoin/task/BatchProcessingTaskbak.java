//package com.play.multithreading.forkjoin.task;
//
//import com.play.multithreading.common.model.gateway.Batch;
//import com.play.multithreading.processor.BatchProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.RecursiveTask;
//
//public class BatchProcessingTaskbak extends RecursiveTask<List<Batch>> {
//
//    private static final int BATCH_PROCEESING_THRESHOLD = 10;
//    List<Batch> toSettleBatchList;
//    private int taskWorkload = toSettleBatchList.size();
//
//    @Autowired
//    BatchProcessor batchProcessor;
//
//    public BatchProcessingTaskbak(List<Batch> toSettleBatchList) {
//        this.toSettleBatchList = toSettleBatchList;
//    }
//
//    @Override
//    protected List<Batch> compute() {
//        if(taskWorkload > BATCH_PROCEESING_THRESHOLD) {
//            System.out.println("Splitting batch processing workLoad : " + (toSettleBatchList.size() - this.taskWorkload));
//            List<BatchProcessingTaskbak> subtasks =  new ArrayList<>();
//            createSubTasks(subtasks, 0);
//            for(BatchProcessingTaskbak subtask : subtasks){
//                subtask.invoke();
//            }
//        } else {
//            batchProcessor.processBatch(toSettleBatchList);
//        }
//            return this.toSettleBatchList;
//    }
//
//    private List<BatchProcessingTaskbak> createSubTasks(List<BatchProcessingTaskbak> subTasks, int startIndex) {
//        int pendingWorkload = toSettleBatchList.size() - BatchProcessingTaskbak.BATCH_PROCEESING_THRESHOLD;
//        BatchProcessingTaskbak subtask = new BatchProcessingTaskbak(toSettleBatchList.subList(startIndex, (startIndex + BATCH_PROCEESING_THRESHOLD - 1)));
//        subTasks.add(subtask);
//        if(pendingWorkload > taskWorkload) {
//            createSubTasks(subTasks, startIndex + BATCH_PROCEESING_THRESHOLD);
//        }
//        return subTasks;
//    }
//}
