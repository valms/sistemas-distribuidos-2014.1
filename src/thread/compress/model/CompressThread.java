package thread.compress.model;

import java.io.File;
import java.util.concurrent.Semaphore;

import util.FileUtils;

public class CompressThread implements Runnable {
	private String filePath;
	private File file;
	private Semaphore semaforo;

	public CompressThread(String filePath, File file, Semaphore semaforo) {
		this.filePath = filePath;
		this.file = file;
		this.semaforo = semaforo;
	}

	@Override
	public void run() {
		FileUtils.compressFile(filePath, file.getName(), file.getAbsolutePath());
		semaforo.release(1);
	}

}
