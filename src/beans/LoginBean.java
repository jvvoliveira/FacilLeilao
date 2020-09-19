package beans;

import entidades.Usuario;
import servico.UsuarioServico;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;


@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioServico usuarioServico;
	
	private Usuario usuario;
	
	@PostConstruct
    public void init() {
        usuario = new Usuario();
    }
	
    public void login(){
    	
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
