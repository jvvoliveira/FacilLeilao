package beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import entidades.Anuncio;
import entidades.Categoria;
import entidades.Usuario;
import servico.AnuncioServico;
import servico.CategoriaServico;

@ManagedBean
@SessionScoped
public class LeilaoBean implements Serializable{
	
	@EJB
	private AnuncioServico anuncioServico;
	
	@EJB
	private CategoriaServico categoriaServico;
	
	private List<Anuncio> anunciosDisponiveis;
	
	private List<Categoria> categorias;
	
	private Anuncio anuncioSelecionado;
	
	private Usuario usuario;
	
	@PostConstruct
    public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getCurrentInstance().getExternalContext().getSession(false);
		this.usuario = (Usuario) session.getAttribute("usuario");
		
		todosAnunciosDisponiveis();
		setCategorias(categoriaServico.getCategorias());
    }
	
	public void todosAnunciosDisponiveis() {
		filtrarAnunciosDisponiveis(anuncioServico.getAnuncios());
	}
	
	private void filtrarAnunciosDisponiveis(List<Anuncio> anuncios) {
		List<Anuncio> anunciosDisponiveis = new ArrayList<Anuncio>();
		
		for(int i = 0; i < anuncios.size(); i++) {
			LocalDateTime dateTime = LocalDateTime.parse(anuncios.get(i).getPrazo());
			if(dateTime.isAfter(LocalDateTime.now())) {
				anuncios.get(i).setPrazo(dateTime.toString());
				anunciosDisponiveis.add(anuncios.get(i));
			}
		}
		setAnunciosDisponiveis(anunciosDisponiveis);
	}
	
	public void filtrarAnunciosPorCategoria(String categoria) {
		filtrarAnunciosDisponiveis(anuncioServico.getAnunciosByCategoria(categoria));
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

	public List<Anuncio> getAnunciosDisponiveis() {
		return anunciosDisponiveis;
	}

	public void setAnunciosDisponiveis(List<Anuncio> anunciosDisponiveis) {
		this.anunciosDisponiveis = anunciosDisponiveis;
	}

	public Anuncio getAnuncioSelecionado() {
		return anuncioSelecionado;
	}

	public void setAnuncioSelecionado(Anuncio anuncioSelecionado) {
		this.anuncioSelecionado = anuncioSelecionado;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
}
