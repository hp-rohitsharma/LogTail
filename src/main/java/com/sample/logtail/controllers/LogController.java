package com.sample.logtail.controllers;

import java.io.File;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.sample.logtail.reader.PrinterThread;
import com.sample.logtail.reader.ReaderThread;

@PropertySource("classpath:app.properties")
@Controller
public class LogController {

	@Autowired
	private SimpMessagingTemplate template;
	
	private LinkedList<String> queue= new LinkedList<String>();
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private boolean launched = false;

	@Value("${log.file.path}")
	private String logFilePath;

	@MessageMapping("/register")
	public void register() throws Exception {
		log.info("Registered client");
		if(!launched) {
			ReaderThread readerThread = new ReaderThread(new File(logFilePath), queue);
			PrinterThread printerThread = new PrinterThread(queue, template);
			new Thread(readerThread).start();
			new Thread(printerThread).start();
			log.info("Initilized Log monitor threads");
		}
		
		launched = true;
	}

}