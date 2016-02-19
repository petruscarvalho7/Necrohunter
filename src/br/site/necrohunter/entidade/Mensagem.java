package br.site.necrohunter.entidade;

public class Mensagem {

	private String mensagem;
	private String remetente, contatoRemetente;
	
	public Mensagem(){
		
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public String getContatoRemetente() {
		return contatoRemetente;
	}

	public void setContatoRemetente(String contatoRemetente) {
		this.contatoRemetente = contatoRemetente;
	}
	
	
}
