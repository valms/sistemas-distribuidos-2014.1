package thread.compress;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Semaphore;

import javax.swing.JFileChooser;

import jxl.write.WriteException;
import thread.compress.model.CompressThread;
import util.DataSheetUtils;
import util.FileUtils;

public class Main {
	public static int index;

	public static void main(String[] args) {
		int aux = 0;
		JFileChooser fileChooser = new JFileChooser();
		// TODO mudar de ArrayList<File> listaArquivos para List<File>
		// listaArquivos
		ArrayList<File> listaArquivos = new ArrayList<File>();
		while (aux == 0) {
			aux = fileChooser.showDialog(null, "Selecionar");
			if (aux == 0) {
				listaArquivos.add(fileChooser.getSelectedFile());
			}
		}
		int size = listaArquivos.size();
		System.out.println("Total de arquivos: " + size);
		System.out.println("Compactacao linear");
		double mediaLinear = 0.0;
		double[] temposLinear = new double[5];
		String[] filePaths = new String[listaArquivos.size()];
		for (int i = 0; i < 5; i++) {
			long inicio = Calendar.getInstance().getTimeInMillis();
			for (int j = 0; j < listaArquivos.size(); j++) {
				File file = listaArquivos.get(j);
				filePaths[j] = "src" + File.separator + "resources" + File.separator + file.getName() + "-" + (i + 1)
						+ ".zip";
				FileUtils.compressFile(filePaths[j], file.getName(), file.getAbsolutePath());
			}
			long fim = Calendar.getInstance().getTimeInMillis();
			// FileUtils.deleteFiles(filePaths);
			temposLinear[i] = (fim - inicio) / 1000.0;
			mediaLinear += temposLinear[i];
		}
		mediaLinear /= 5;

		System.out.println("Compactacao em paralelo");
		Semaphore semaforo = new Semaphore(size);
		double mediaParalelo = 0.0;
		double[] temposParalelo = new double[5];
		long inicio = Calendar.getInstance().getTimeInMillis();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < listaArquivos.size(); j++) {
				filePaths[j] = "src" + File.separator + "resources" + File.separator + listaArquivos.get(j).getName()
						+ "-" + "Thread-" + (j + 1) + "-" + ((int) (Math.random() * 1000)) + ".zip";
				try {
					semaforo.acquire(1);
					new Thread(new CompressThread(filePaths[j], listaArquivos.get(j), semaforo)).start();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				semaforo.acquire(size);
				long fim = Calendar.getInstance().getTimeInMillis();
				semaforo.release(size);
				// FileUtils.deleteFiles(filePaths);
				temposParalelo[i] = (fim - inicio) / 1000.0;
				mediaParalelo += temposParalelo[i];
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		mediaParalelo /= 5;
		// try {
		// //escreve na planilha
		// DataSheetUtils dsu = new DataSheetUtils("src" + File.separator +
		// "resources" + File.separator+"planilha teste.xls");
		// dsu.writeLine("profile teste", 1, mediaLinear, mediaParalelo);
		// dsu.closeSheet();
		// } catch (WriteException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
