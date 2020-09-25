package beans;

import entidades.Usuario;
import servico.UsuarioServico;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;


@ManagedBean
@SessionScoped
//@RequestScoped
//@Named
public class LoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
//	@Inject
	private UsuarioServico usuarioServico;
	
//	private FacesContext context;
	
	private Usuario usuario;
	
	@PostConstruct
    public void init() {
        usuario = new Usuario();
    }
	
    public String login(){
        FacesContext context = FacesContext.getCurrentInstance();
    	try {
    		((HttpServletRequest) context.getExternalContext().getRequest()).login(this.usuario.getEmail(), this.usuario.getSenha());
    		
    		Usuario usuarioLogado = usuarioServico.getUsuarioByEmail(this.usuario.getEmail());
    		
    		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
    		session.setAttribute("usuario", usuarioLogado);
    		return "home";
    	}catch(Exception e) {
    		this.usuario = new Usuario();
    		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha e/ou email incorretos", null));
    		e.printStackTrace();
    		return null;
    	}
    }
    

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	private String convertStringToMd5(String valor) {
		  MessageDigest mDigest;
		  try {
		      //Instanciamos o nosso HASH MD5, poderíamos usar outro como
		      //SHA, por exemplo, mas optamos por MD5.
		      mDigest = MessageDigest.getInstance("MD5");

		      //Convert a String valor para um array de bytes em MD5
		      byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));

		      //Convertemos os bytes para hexadecimal, assim podemos salvar
		      //no banco para posterior comparação se senhas
		      StringBuffer sb = new StringBuffer();
		      for (byte b : valorMD5){
		             sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1,3));
		      }

		      return sb.toString();

		  } catch (NoSuchAlgorithmException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		      return null;
		  } catch (UnsupportedEncodingException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		      return null;
		  }
		}

}
