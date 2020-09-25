package beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import entidades.Usuario;

@ManagedBean
@SessionScoped
public class LeilaoBean implements Serializable{
	
	private Usuario usuario;
	
	@PostConstruct
    public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getCurrentInstance().getExternalContext().getSession(false);
		this.usuario =  (Usuario) session.getAttribute("usuario");
    }
	
//	public String logout() {
//		try {
//			FacesContext context = FacesContext.getCurrentInstance();
//			HttpSession session = (HttpSession) context.getCurrentInstance().getExternalContext().getSession(false);
//			session.removeAttribute("usuario");
//			session.invalidate();
//			return "login";			
//		}catch(Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
