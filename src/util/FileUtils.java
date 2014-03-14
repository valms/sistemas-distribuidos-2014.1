package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {
	/**
	 * 
	 * @param destinationCompressedFile
	 *            - path do arquivo compactado
	 * @param fileName
	 *            - nome do arquivo dentro do zip
	 * @param srcFilePath
	 *            - path do arquivo a ser compactado
	 */
	public static void compressFile(String destinationCompressedFile, String fileName, String srcFilePath) {
		byte[] buffer = new byte[1024];
		try {
			FileOutputStream fos = new FileOutputStream(destinationCompressedFile, false);
			ZipOutputStream zos = new ZipOutputStream(fos);
			ZipEntry ze = new ZipEntry(fileName);
			zos.putNextEntry(ze);
			FileInputStream in = new FileInputStream(srcFilePath);
			int len;
			while ((len = in.read(buffer)) > -1) {
				zos.write(buffer, 0, len);
			}
			in.close();
			zos.closeEntry();
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que deleta os arquivos .zip gerados entre as execuções.
	 * @param listaZip
	 */
	public static void deleteFiles(List<String> listaZip) {
		for (int i = 0; i < listaZip.size(); i++) {
			try {
				File file = new File(listaZip.get(i));

				if (!file.delete()) {
					System.out.println("Delete do arquivo " + file.getName() + " falhou.");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Método que gera os arquivos txt.
	 * @param mb
	 * @return
	 */
	public static File createDummyFile(int mb){
		String letras = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		File directory = null;
		try {
			directory = new File("src"+File.separator+"resources"+File.separator+"txt"+File.separator);
			directory.mkdirs();
			File file = new File("src"+File.separator+"resources"+File.separator+"txt"+File.separator+(mb*100)+"kb.txt");
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			// TODO usar file.listFiles() para pesquisar a pasta
			while(file.length() < (1024 * (mb * (1024 / 10)))){
				int index = (int) ( Math.random() * letras.length() );
				fw.write(letras.charAt(index)+"\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return directory;
	}
	
	/**
	 * Método para listar todos os arquivos das subPastas. 
	 * Retorna uma Lista de File, que deve ser passada para a rotina de compressão.
	 * 
	 * @param path
	 * @return
	 */
	public static List<File> listarArquivos(String path) {
		File directory = new File(path);
		List<File> resultList = new ArrayList<File>();

		// get all the files from a directory
		File[] fList = directory.listFiles();
		resultList.addAll(Arrays.asList(fList));
		for (File file : fList) {
			// Neste momento 
			// if (file.isFile()) {
			// // System.out.println(file.getPath());
			// } else
			if (file.isDirectory()) {
				resultList.addAll(listarArquivos(file.getPath()));
			}
		}
		return resultList;
	}
}
