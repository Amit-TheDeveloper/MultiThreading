package com.play.multithreading;

import com.play.multithreading.common.model.gateway.Transaction;
import com.play.multithreading.utils.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MultithreadingApplication  {

	@Autowired
	DataHelper dataHelper;

	public static void main(String[] args) {
		SpringApplication.run(MultithreadingApplication.class, args);
	}

}
