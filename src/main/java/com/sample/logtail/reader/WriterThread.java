package com.sample.logtail.reader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterThread implements Runnable{

	private File file;
	public WriterThread(File file) {
		this.file = file;
	}
	
	public void run() {
		BufferedWriter bw = null;
		try {
			FileWriter reader = new FileWriter(file);
			bw = new BufferedWriter(reader);
			String line;
			while(true) {
				line = "text "+System.currentTimeMillis();
				bw.write(line);
				bw.flush();
				System.out.println("written : "+line);
				Thread.sleep(5*1000);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			try {
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}