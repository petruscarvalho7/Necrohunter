package br.site.necrohunter.bean;

import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import br.site.necrohunter.entidade.Mensagem;

@ManagedBean
@SessionScoped

	//clase que retorna uma autenticacao para ser enviada e verificada pelo servidor smtp
	public class EnviarEmailBean {
		
	    private String mailSMTPServer;
	    private String mailSMTPServerPort;
	    private Mensagem mensagem = new Mensagem();
	    /*
	     * quando instanciar um Objeto ja sera atribuido o servidor SMTP do GMAIL 
	     * e a porta usada por ele
	     */
	    public EnviarEmailBean() { //Para o GMAIL 
	        mailSMTPServer = "smtp.gmail.com";
	        mailSMTPServerPort = "465";
	    }
	    /*
	     * caso queira mudar o servidor e a porta, so enviar para o contrutor
	     * os valor como string
	     */

	    public EnviarEmailBean(String mailSMTPServer, String mailSMTPServerPort) { //Para outro Servidor
	        this.mailSMTPServer = mailSMTPServer;
	        this.mailSMTPServerPort = mailSMTPServerPort;
	    }
	    public void sendMail(String from, String to, String subject, String message) {
	        Properties props = new Properties();
	       // quem estiver utilizando um SERVIDOR PROXY descomente essa parte e atribua as propriedades do SERVIDOR PROXY utilizado
	                /*
	        props.setProperty("proxySet","true");
	        props.setProperty("socksProxyHost","192.168.155.1"); // IP do Servidor Proxy
	        props.setProperty("socksProxyPort","1080");  // Porta do servidor Proxy
	         */
	        props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", mailSMTPServer); //server SMTP do GMAIL
	        props.put("mail.smtp.auth", "true"); //ativa autenticacao
	        props.put("mail.smtp.user", from); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)
	        props.put("mail.debug", "true");
	        props.put("mail.smtp.port", mailSMTPServerPort); //porta
	        props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.socketFactory.fallback", "false");
	        //Cria um autenticador que sera usado a seguir
	        SimpleAuth auth = new SimpleAuth("necrohunterdeathmetal@gmail.com", "necromauro5790");
	        //Session - objeto que ira realizar a conex���o com o servidor
	        /*Como h��� necessidade de autentica������o ��� criada uma autenticacao que
	         * ��� responsavel por solicitar e retornar o usu���rio e mauro3899 para 
	         * autentica������o */
	        Session session = Session.getInstance(props, auth);
	        session.setDebug(true); //Habilita o LOG das a������es executadas durante o envio do email
	        //Objeto que cont���m a mensagem
	        Message msg = new MimeMessage(session);
	        try {
	            //Setando o destinat���rio
	            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
	            //Setando a origem do email
	            msg.setFrom(new InternetAddress(from));
	            //Setando o assunto
	            msg.setSubject(subject);
	            //Setando o conte���do/corpo do email
	            msg.setContent(message, "text/plain");

	        } catch (Exception e) {
	            System.out.println(">> Erro: Completar Mensagem");
	            e.printStackTrace();
	        }

	        //Objeto encarregado de enviar os dados para o email
	        Transport tr;
	        try {
	            tr = session.getTransport("smtp"); //define smtp para transporte
	   /*
	             *  1 - define o servidor smtp
	             *  2 - seu nome de usuario do gmail
	             *  3 - sua mauro3899 do gmail
	             */
	            tr.connect(mailSMTPServer, "necrohunterdeathmetal@gmail.com", "necromauro5790");
	            msg.saveChanges(); // don't forget this
	            //envio da mensagem
	            tr.sendMessage(msg, msg.getAllRecipients());
	            tr.close();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            System.out.println(">> Erro: Envio Mensagem");
	            e.printStackTrace();
	        }

	    }

		 public void enviarEmail(){
		    	String montarMensagem = "Nome:"+this.mensagem.getRemetente() + "\nContato:"+ this.mensagem.getContatoRemetente() +
		    			"\nMensagem:" + this.mensagem.getMensagem();
		    	sendMail("necrohunterdeathmetal@gmail.com", "petrus.carvalho7@gmail.com", "Contratar a NecroHunter!!", montarMensagem);
		    	novo();
		    	FacesContext fc = FacesContext.getCurrentInstance(); 
		          
		    	fc.addMessage("contact-form", new FacesMessage("Obrigado por entrar em contato com a NecroHunter.")); 
		    }
		 public String novo()
			{
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
	class SimpleAuth extends Authenticator {
	    public String username = null;
	    public String password = null;
	    public SimpleAuth(String user, String pwd) {
	        username = user;
	        password = pwd;
	    }
	    protected PasswordAuthentication getPasswordAuthentication() {
	        return new PasswordAuthentication(username, password);
	    }
	    
	}
	