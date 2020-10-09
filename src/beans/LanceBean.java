package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import entidades.Anuncio;
import entidades.Lance;
import entidades.Usuario;
import servico.AnuncioServico;
import servico.LanceServico;

@ManagedBean
@SessionScoped
public class LanceBean {

	@EJB
	private LanceServico lanceServico;
	
	@EJB
	private AnuncioServico anuncioServico;
	
	private List<Lance> lancesDiretoRecebidos;
	private Lance lanceDiretoSelecionado;

	private Usuario usuario;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getCurrentInstance().getExternalContext().getSession(false);
		this.usuario = (Usuario) session.getAttribute("usuario");
		
		todosLancesDireto();
	}
	
	public void todosLancesDireto() {
		List<Anuncio> anunciosProprios = anuncioServico.getAnunciosByUsuarioVendedor(this.usuario.getId());
		List<Lance> lancesDireto = new ArrayList<Lance>();
		for(int index = 0; index < anunciosProprios.size(); index++) {
			Anuncio anuncio = anunciosProprios.get(index);
			List<Lance> lancesDiretoAnuncio = lanceServico.getLancesDiretoByAnuncio(anuncio.getId());
			for(int i = 0; i < lancesDiretoAnuncio.size(); i++) {
				lancesDireto.add(lancesDiretoAnuncio.get(i));
			}
		}
		setLancesDiretoRecebidos(lancesDireto);
	}

	public List<Lance> getLancesDiretoRecebidos() {
		return lancesDiretoRecebidos;
	}

	public void setLancesDiretoRecebidos(List<Lance> lancesDiretoRecebidos) {
		this.lancesDiretoRecebidos = lancesDiretoRecebidos;
	}

	public Lance getLanceDiretoSelecionado() {
		return lanceDiretoSelecionado;
	}

	public void setLanceDiretoSelecionado(Lance lanceDiretoSelecionado) {
		this.lanceDiretoSelecionado = lanceDiretoSelecionado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
