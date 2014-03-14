package thread.counter;

import thread.counter.model.ContadorThread;

public class Main {
	public static void main(String[] args) {
		System.out.println("Criando os contadores...");
		ContadorThread contador1 = new ContadorThread();
		ContadorThread contador2 = new ContadorThread();
		ContadorThread contador3 = new ContadorThread();
		System.out.println("Iniciando os contadores...");
		contador1.start();
		contador2.start();
		contador3.start();
		System.out.println("Contadores foram iniciados.");
		}
}
