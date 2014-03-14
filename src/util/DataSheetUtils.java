package util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import thread.compress.Trabalho;
import thread.compress.model.Profile;

public class DataSheetUtils {

	private WritableSheet s;
	private WritableWorkbook workbook;
	private List<Profile> listaSerial;
	private List<Profile> listaParalelo;
	private LinkedHashMap<String, Double> media = new LinkedHashMap<String, Double>();
	public DataSheetUtils(String fileName, List<Profile> listaSerial, List<Profile> listaParalelo) throws WriteException, IOException {
		this.listaSerial = listaSerial;
		this.listaParalelo = listaParalelo;
		createSheet(fileName);
	}

	public void createSheet(String fileName) throws IOException, WriteException {
		// cria planilha e folha
		WorkbookSettings ws = new WorkbookSettings();
		workbook = Workbook.createWorkbook(new File(fileName), ws);
		s = workbook.createSheet("Folha1", 0);
		
		gerarTabela(listaSerial, "Serial", 0);
		media = new LinkedHashMap<String, Double>();
		gerarTabela(listaParalelo, "Paralelo", 12);
		
		
	}

	private void gerarTabela(List<Profile> lista, String tituloTabela, int linhaInicial) throws IOException, WriteException {
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf);

		NumberFormat dp3 = new NumberFormat("####.###");
		WritableCellFormat casasDecimais = new WritableCellFormat(dp3);

		cf.setWrap(true);
		// estranhamente as células são na ordem "coluna, linha"
		int coluna = 0;
		int linha = linhaInicial;
		Label l = new Label(coluna, linha, tituloTabela, cf);
		s.addCell(l);
		s.mergeCells(coluna, linha, coluna + 18, linha);

		linha++;

		l = new Label(coluna, linha, "Iteração", cf);
		s.addCell(l);

		coluna++;

		l = new Label(coluna, linha, "Tempo em (s) - TXT", cf);
		s.addCell(l);
		s.mergeCells(coluna, linha, coluna + 8, linha);
		coluna += 9;
		l = new Label(coluna, linha, "Tempo em (s) - BIN", cf);
		s.addCell(l);
		s.mergeCells(coluna, linha, coluna + 8, linha);

		linha++;

		coluna = 1;
		for (Profile p : lista) {
			if (p.getIteracao() > 1) {
				break;
			}
			l = new Label(coluna, linha, p.getNome(), cf);
			s.addCell(l);
			s.mergeCells(coluna, linha, coluna + 2, linha);

			linha++;

			l = new Label(coluna, linha, "10", cf);
			s.addCell(l);

			coluna++;

			l = new Label(coluna, linha, "50", cf);
			s.addCell(l);

			coluna++;

			l = new Label(coluna, linha, "100", cf);
			s.addCell(l);

			coluna++;
			linha--;
		}

		coluna = 0;
		linha += 2;
		for (int i = 0; i < Trabalho.ITERACOES; i++) {
			l = new Label(coluna, linha, (i + 1) + "", cf);
			s.addCell(l);
			linha++;
		}

		int itr = 1;
		coluna = 1;
		linha = linhaInicial+4;
		for (Profile p : lista) {
			if (itr != p.getIteracao()) {
				coluna = 1;
				linha++;
				itr = p.getIteracao();
			}

			if(media.get(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_10) == null){
				media.put(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_10, p.getTempo10());
			}else{
				media.put(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_10
						  , media.get(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_10)+p.getTempo10());
				
			}
			
			l = new Label(coluna, linha, p.getTempo10() + "", casasDecimais);
			s.addCell(l);

			coluna++;

			if(media.get(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_50) == null){
				media.put(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_50, p.getTempo50());
			}else{
				media.put(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_50
						, media.get(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_50)+p.getTempo50());
				
			}
			l = new Label(coluna, linha, p.getTempo50() + "", casasDecimais);
			s.addCell(l);

			coluna++;

			if(media.get(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_100) == null){
				media.put(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_100, p.getTempo100());
			}else{
				media.put(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_100
						, media.get(p.getTamanho()+p.getTipoAqruivo()+Trabalho.QT_100)+p.getTempo100());
				
			}
			l = new Label(coluna, linha, p.getTempo100() + "", casasDecimais);
			s.addCell(l);

			coluna++;
		}
		linha++;
		coluna = 0;
		l = new Label(coluna, linha, "Media", cf);
		s.addCell(l);
		coluna++;
		for (Entry<String, Double> e : media.entrySet()) {
			double md = (e.getValue().doubleValue() / (Trabalho.ITERACOES + 0.0) );
			l = new Label(coluna, linha, String.valueOf(md), casasDecimais);
			s.addCell(l);

			coluna++;
		}

	}

	public void closeSheet() throws WriteException, IOException {
		workbook.write();
		workbook.close();
	}

	// Escreve uma linha na planilha. As linhas começam a partir do 1

	public void writeLine(String profileName, int linha, double tempoLinear, double tempoParalelo) throws WriteException, IOException {
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf);
		cf.setWrap(true);
		Label l = new Label(0, linha, profileName, cf);
		s.addCell(l);
		NumberFormat dp3 = new NumberFormat("#.###");
		WritableCellFormat casasDecimais = new WritableCellFormat(dp3);
		l = new Label(1, linha, Double.toString(tempoLinear), casasDecimais);
		s.addCell(l);
		l = new Label(2, linha, Double.toString(tempoParalelo), casasDecimais);
		s.addCell(l);

	}
	
}
