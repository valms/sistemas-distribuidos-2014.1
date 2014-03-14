package thread.compress;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Semaphore;

import jxl.write.WriteException;
import thread.compress.model.CompressThread;
import thread.compress.model.Profile;
import util.DataSheetUtils;
import util.FileUtils;

public class Trabalho {
	public static final int ITERACOES = 5;
	public static final int QT_100 = 100;
	public static final int QT_50 = 50;
	public static final int QT_10 = 10;
	private static final String ZIP_FILE_PATH = "src"+File.separator+"resources"+File.separator+"zip";
	public static void main(String[] args) {
		File zipFolder = new File(ZIP_FILE_PATH);
		zipFolder.mkdirs();
		List<Profile> profilesSeriais = new ArrayList<Profile>();
		List<Profile> profilesParalelos = new ArrayList<Profile>();
		List<File> listaTxt = FileUtils.listarArquivos("src"+File.separator+"resources"+File.separator+"txt");
		List<File> listaBin = FileUtils.listarArquivos("src"+File.separator+"resources"+File.separator+"bin");
		List<String> listaZip = new ArrayList<String>();
		//Compactacao Serial
		long inicioProg = Calendar.getInstance().getTimeInMillis();
		gerarProfilesSeriais(profilesSeriais, listaTxt, listaBin, listaZip);
		long fimProg = Calendar.getInstance().getTimeInMillis();
		System.out.println("Tempo Total do serial: "+ ((fimProg - inicioProg) / 1000.0) + "(s)");
		//Compactacao Paralelo
		inicioProg = Calendar.getInstance().getTimeInMillis();
		gerarProfilesParalelos(profilesParalelos, listaTxt, listaBin, listaZip);
		fimProg = Calendar.getInstance().getTimeInMillis();
		System.out.println("Tempo Total do paralelo: "+ ((fimProg - inicioProg) / 1000.0) + "(s)");
		try {
			DataSheetUtils dsu = new DataSheetUtils("src" + File.separator + "resources" + File.separator+"planilha teste.xls"
													, profilesSeriais
													, profilesParalelos);
			dsu.closeSheet();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("QT Profiles seriais: "+profilesSeriais.size());
		System.out.println("QT Profiles paralelos: "+profilesParalelos.size());
	}
	
	private static void gerarProfilesSeriais(List<Profile> profilesSeriais, List<File> listaTxt, List<File> listaBin, List<String> listaZip) {
		long inicio;
		long fim10;
		long fim50;
		long fim100;
		Profile p;
		for (int i = 0; i < ITERACOES; i++) {
			for (File txt : listaTxt) {
				inicio = Calendar.getInstance().getTimeInMillis();
				p = new Profile(i+1, txt.getName(), "TXT", String.valueOf(txt.length()/1024.0), "Serial");
				for (int j = 0; j < QT_100; j++) {
					String path = ZIP_FILE_PATH+File.separator+"txt-"+p.getNome()+"-"+i+"-"+j+".zip";
					listaZip.add(path);
					FileUtils.compressFile(path, txt.getName(), txt.getAbsolutePath());
					if(j+1 == QT_10){
						fim10 = Calendar.getInstance().getTimeInMillis();
						p.setTempo10((fim10 - inicio) / 1000.0);
					}
					if(j+1 == QT_50){
						fim50 = Calendar.getInstance().getTimeInMillis();
						p.setTempo50((fim50 - inicio) / 1000.0);
					}
				}
				fim100 = Calendar.getInstance().getTimeInMillis();
				p.setTempo100((fim100 - inicio) / 1000.0);
				profilesSeriais.add(p);
				p.toString();
			}
			FileUtils.deleteFiles(listaZip);
			listaZip = new ArrayList<String>();
			for (File bin : listaBin) {
				p = new Profile(i+1, bin.getName(), "BIN", String.valueOf(bin.length()/1024.0), "Serial");
				inicio = Calendar.getInstance().getTimeInMillis();
				for (int j = 0; j < QT_100; j++) {
					String path = ZIP_FILE_PATH+File.separator+"bin-"+p.getNome()+"-"+i+"-"+j+".zip";
					listaZip.add(path);
					FileUtils.compressFile(path, bin.getName(), bin.getAbsolutePath());
					if(j+1 == QT_10){
						fim10 = Calendar.getInstance().getTimeInMillis();
						p.setTempo10((fim10 - inicio) / 1000.0);
					}
					if(j+1 == QT_50){
						fim50 = Calendar.getInstance().getTimeInMillis();
						p.setTempo50((fim50 - inicio) / 1000.0);
					}
				}
				fim100 = Calendar.getInstance().getTimeInMillis();
				p.setTempo100((fim100 - inicio) / 1000.0);
				profilesSeriais.add(p);
				p.toString();
			}
			FileUtils.deleteFiles(listaZip);
			listaZip = new ArrayList<String>();
		}
	}
	
	private static void gerarProfilesParalelos(List<Profile> profilesParalelos, List<File> listaTxt, List<File> listaBin, List<String> listaZip) {
		long inicio;
		long fim10;
		long fim50;
		long fim100;
		Profile p;
		Semaphore s10 = new Semaphore(10);
		Semaphore s50 = new Semaphore(50);
		Semaphore s100 = new Semaphore(100);
		for (int i = 0; i < ITERACOES; i++) {
			for (File txt : listaTxt) {
				inicio = Calendar.getInstance().getTimeInMillis();
				p = new Profile(i+1, txt.getName(), "TXT", String.valueOf(txt.length()/1024.0), "Paralelo");
				//Paralelo de 10
				for (int j = 0; j < QT_10; j++) {
					try {
						String path = ZIP_FILE_PATH+File.separator+"txt-"+p.getNome()+"-"+i+"-"+j+".zip";
						listaZip.add(path);
						new Thread(new CompressThread(path, txt, s10)).start();
						s10.acquire(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					s10.acquire(QT_10);
					fim10 = Calendar.getInstance().getTimeInMillis();
					s10.release(QT_10);
					p.setTempo10((fim10 - inicio) / 1000.0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				FileUtils.deleteFiles(listaZip);
				listaZip = new ArrayList<String>();
				//Paralelo de 50
				for (int j = 0; j < QT_50; j++) {
					try {
						String path = ZIP_FILE_PATH+File.separator+"txt-"+p.getNome()+"-"+i+"-"+j+".zip";
						listaZip.add(path);
						new Thread(new CompressThread(path, txt, s50)).start();
						s50.acquire(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					s50.acquire(QT_50);
					fim50 = Calendar.getInstance().getTimeInMillis();
					s50.release(QT_50);
					p.setTempo50((fim50 - inicio) / 1000.0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				FileUtils.deleteFiles(listaZip);
				listaZip = new ArrayList<String>();
				//Paralelo de 100
				for (int j = 0; j < QT_100; j++) {
					try {
						String path = ZIP_FILE_PATH+File.separator+"txt-"+p.getNome()+"-"+i+"-"+j+".zip";
						listaZip.add(path);
						new Thread(new CompressThread(path, txt, s100)).start();
						s100.acquire(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					s100.acquire(QT_100);
					fim100 = Calendar.getInstance().getTimeInMillis();
					s100.release(QT_100);
					p.setTempo100((fim100 - inicio) / 1000.0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				profilesParalelos.add(p);
				p.toString();
			}
			FileUtils.deleteFiles(listaZip);
			listaZip = new ArrayList<String>();
			//BINARIO PARALELO
			for (File bin : listaBin) {
				inicio = Calendar.getInstance().getTimeInMillis();
				p = new Profile(i+1, bin.getName(), "BIN", String.valueOf(bin.length()/1024.0), "Paralelo");
				//Paralelo de 10
				for (int j = 0; j < QT_10; j++) {
					try {
						String path = ZIP_FILE_PATH+File.separator+"bin-"+p.getNome()+"-"+i+"-"+j+".zip";
						listaZip.add(path);
						new Thread(new CompressThread(path, bin, s10)).start();
						s10.acquire(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					s10.acquire(QT_10);
					fim10 = Calendar.getInstance().getTimeInMillis();
					s10.release(QT_10);
					p.setTempo10((fim10 - inicio) / 1000.0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				FileUtils.deleteFiles(listaZip);
				listaZip = new ArrayList<String>();
				//Paralelo de 50
				for (int j = 0; j < QT_50; j++) {
					try {
						String path = ZIP_FILE_PATH+File.separator+"bin-"+p.getNome()+"-"+i+"-"+j+".zip";
						listaZip.add(path);
						new Thread(new CompressThread(path, bin, s50)).start();
						s50.acquire(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					s50.acquire(QT_50);
					fim50 = Calendar.getInstance().getTimeInMillis();
					s50.release(QT_50);
					p.setTempo50((fim50 - inicio) / 1000.0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				FileUtils.deleteFiles(listaZip);
				listaZip = new ArrayList<String>();
				//Paralelo de 100
				for (int j = 0; j < QT_100; j++) {
					try {
						String path = ZIP_FILE_PATH+File.separator+"bin-"+p.getNome()+"-"+i+"-"+j+".zip";
						listaZip.add(path);
						new Thread(new CompressThread(path, bin, s100)).start();
						s100.acquire(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					s100.acquire(QT_100);
					fim100 = Calendar.getInstance().getTimeInMillis();
					s100.release(QT_100);
					p.setTempo100((fim100 - inicio) / 1000.0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				p.toString();
				profilesParalelos.add(p);
			}
		}
		FileUtils.deleteFiles(listaZip);
		listaZip = new ArrayList<String>();
	}
}
