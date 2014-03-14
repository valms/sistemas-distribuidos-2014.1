package thread.counter.model;

public class ContadorThread extends Thread{
	public  void run() {
		for (int i = 0; i <= 10; i++) {
			System.out.println("Contando: " + i);
		}
	}
}
