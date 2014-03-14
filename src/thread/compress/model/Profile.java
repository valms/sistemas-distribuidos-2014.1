package thread.compress.model;

public class Profile {
	private int iteracao;
	private String nome;
	private String tipoAqruivo;
	private String tamanho;
	private String tipoCompressao;
	private double tempo10;
	private double tempo50;
	private double tempo100;
	
	public Profile(int i, String nome, String tipoArquivo, String tamanho, String tipoCompressao){
		this.iteracao = i;
		this.nome = nome;
		this.tipoAqruivo = tipoArquivo;
		this.tamanho = tamanho;
		this.tipoCompressao = tipoCompressao;
	}
	
	@Override
	public String toString() {
		System.out.println("Iteração: "+this.getIteracao());
		System.out.println(this.getNome()+" - "+this.getTamanho()+" - "+this.getTipoAqruivo());
		System.out.println("Tempo10: "+this.getTempo10()+" (s)");
		System.out.println("Tempo50: "+this.getTempo50()+" (s)");
		System.out.println("Tempo100: "+this.getTempo100()+" (s)");
		System.out.println("--------------------------------------------------------");
		return "";
	}

	public int getIteracao() {
		return iteracao;
	}

	public void setIteracao(int iteracao) {
		this.iteracao = iteracao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoAqruivo() {
		return tipoAqruivo;
	}

	public void setTipoAqruivo(String tipoAqruivo) {
		this.tipoAqruivo = tipoAqruivo;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public String getTipoCompressao() {
		return tipoCompressao;
	}

	public void setTipoCompressao(String tipoCompressao) {
		this.tipoCompressao = tipoCompressao;
	}

	public double getTempo10() {
		return tempo10;
	}

	public void setTempo10(double tempo10) {
		this.tempo10 = tempo10;
	}

	public double getTempo50() {
		return tempo50;
	}

	public void setTempo50(double tempo50) {
		this.tempo50 = tempo50;
	}

	public double getTempo100() {
		return tempo100;
	}

	public void setTempo100(double tempo100) {
		this.tempo100 = tempo100;
	}
	
}
