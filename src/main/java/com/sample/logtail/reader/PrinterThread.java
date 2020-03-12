package com.sample.logtail.reader;
import java.util.LinkedList;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.sample.logtail.dto.LogInfo;

public class PrinterThread implements Runnable{

	private LinkedList<String> queue;
	private SimpMessagingTemplate template;
	
	public PrinterThread(LinkedList<String> queue, SimpMessagingTemplate template) {
		this.queue = queue;
		this.template = template;
	}
	
	public void run() {
		// print all first
		String last = null;
		while(true) {
			synchronized (queue) {
				if(queue.size() > 0 && last != queue.getLast()) {
					last = queue.getLast();
					this.template.convertAndSend("/topic/logs", new LogInfo(last));
				}
			}
		}
	}

}