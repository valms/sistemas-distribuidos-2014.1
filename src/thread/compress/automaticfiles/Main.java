package thread.compress.automaticfiles;

import util.FileUtils;

public class Main {
	public static void main(String[] args) {
		FileUtils.createDummyFile(1);
		FileUtils.createDummyFile(10);
		FileUtils.createDummyFile(100);
	}
}
