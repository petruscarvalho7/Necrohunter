package br.site.necrohunter.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.site.necrohunter.controllers.EnviarEmail;
import br.site.necrohunter.controllers.EnviarEmailIF;
import br.site.necrohunter.entidade.Mensagem;

@ManagedBean
@SessionScoped

public class EnviarEmailBean {
	private Mensagem mensagem = new Mensagem();
	private EnviarEmailIF enviarEmail;

	public EnviarEmailBean() {
		this.enviarEmail = new EnviarEmail();
	}

	public void enviarEmail() {
		String montarMensagem = "Nome:" + this.mensagem.getRemetente() + "\nContato:"
				+ this.mensagem.getContatoRemetente() + "\nMensagem:" + this.mensagem.getMensagem();
		this.enviarEmail.sendMail("necrohunterdeathmetal@gmail.com", "petrus.carvalho7@gmail.com",
				"Contratar a NecroHunter!!", montarMensagem);
		novo();
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage("contact-form", new FacesMessage("Obrigado por entrar em contato com a NecroHunter."));
	}

	public String novo() {
		this.mensagem = new Mensagem();
		return "contacts?faces-redirect=true";
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}
}
