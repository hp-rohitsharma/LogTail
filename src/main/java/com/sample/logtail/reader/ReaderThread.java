package com.sample.logtail.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class ReaderThread implements Runnable{

	private File file;
	private LinkedList<String> queue;
	public ReaderThread(File file, LinkedList<String> queue) {
		this.file = file;
		this.queue = queue;
	}
	
	public void run() {
		
		BufferedReader br = null;
		try {
			FileReader reader = new FileReader(file);
			br = new BufferedReader(reader);
			String line;
			while(true) {
				if(br.ready()) {
					line = br.readLine();
					// add to queue of 20 size
					synchronized (queue) {
						if(queue.size() > 20)
							queue.removeFirst();
						queue.add(line);
					}
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}