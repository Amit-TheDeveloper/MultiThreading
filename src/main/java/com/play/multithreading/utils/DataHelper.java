package com.play.multithreading.utils;

import com.play.multithreading.common.entity.TransactionEntity;
import com.play.multithreading.common.model.gateway.Transaction;
import com.play.multithreading.common.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DataHelper {

    @Autowired
    TransactionRepository txnRepository;

    private final Random random = new Random();
//    private final static String[] MERCHANT_IDS = {"7876546374", "2390584765", "0928475645",
//                                                  "1209678463", "2309482987", "0987465746",
//                                                  "2000998876","3432222212", "0987456734",
//                                                  "8907653547"};
private final static String[] MERCHANT_IDS = {"7876546374"};
    private final static String[] CARD_NUMBERS = {"787654637412121", "2390584765234234", "0928475645213213",
            "120967846322323", "2309482987435435", "0987465746234234",
            "200099887645345","34322222123454", "0987456734543534",
            "8907653547564464"};

    public List<Transaction> createData(int numberOfRecords) {
        List<Transaction> txns = new ArrayList<Transaction>();
        IntStream.range(0, numberOfRecords).forEach(i -> txns.add(createTxn()));
        return txns;
    }

    private Transaction createTxn() {
        return Transaction.Builder.getInstance().withCardNumber(CARD_NUMBERS[random.nextInt(10)])
                                         .withAmount(random.nextDouble())
                                         .withCurrencyCode("840")
                                         //.withMerchantId(MERCHANT_IDS[random.nextInt(10)])
                                          .withMerchantId(MERCHANT_IDS[0])
                                         .withMerchantName("TEST Merchant")
                                         .withTransactedAt(LocalDateTime.now().minusHours(random.nextInt(24)).minusMinutes(random.nextInt(24)))
                                         .withProcessorId("WLF")
                                         .build();
    }

    public void saveRecords(List<Transaction> txns) {
        List<TransactionEntity> txnEntity = txns.stream().map(txn -> toEntity.apply(txn)).collect(Collectors.toList());

        System.out.println("Saving records###### " + txnEntity.size());
        txnEntity.forEach(System.out::println);
        txnRepository.saveAll(txnEntity);
        System.out.println("Data seed complete for number of records " + txnEntity.size());
    }

    public List<TransactionEntity> fetchRecords() {
        return txnRepository.findAll();
    }

    private Function<Transaction, TransactionEntity> toEntity = (txn) -> {
        TransactionEntity entity = new TransactionEntity();
        entity.setMerchantId(txn.getMerchantId());
        entity.setAmount(txn.getAmount());
        entity.setCardNumber(txn.getCardNumber());
        entity.setMerchantName(txn.getMerchantName());
        entity.setSettledAt(txn.getSettledAt());
        entity.setSettlementStatus(txn.getSettlementStatus());
        entity.setProcessorId(txn.getProcessorId());
        entity.setStatus(txn.getStatus());
        entity.setTransactedAt(txn.getTransactedAt());
            return entity;
    };

    public List<TransactionEntity> findAllWithTransactedAtBetween(LocalDateTime batchFrom, LocalDateTime batchTo) {
        return txnRepository.findAllWithTransactedAtBetween(batchFrom, batchTo);
    }
}
