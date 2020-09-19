package beans;

import entidades.Usuario;
import servico.UsuarioServico;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;


@ManagedBean
@SessionScoped
public class CadastroBean implements Serializable{

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
	
    public void salvar(){
    	FacesContext context = FacesContext.getCurrentInstance();
    	try {
    		usuarioServico.salvarUsuario(this.usuario);
    		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado com sucesso!", null));
    		this.usuario = new Usuario();    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro para cadastrar usuário!", null));
    	}
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
