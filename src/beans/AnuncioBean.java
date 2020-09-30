package beans;

import entidades.Anuncio;
import entidades.Usuario;
import servico.AnuncioServico;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

public class AnuncioBean implements Serializable{
	
	@EJB
	private AnuncioServico anuncioServico;
	
	private Anuncio anuncio;
	
	public Anuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}

	public AnuncioBean() {
		// TODO Auto-generated constructor stub
	}

	
}
