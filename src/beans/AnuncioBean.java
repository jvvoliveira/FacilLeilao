package beans;

import entidades.Anuncio;
import entidades.Categoria;
import entidades.Grupo;
import entidades.Usuario;
import servico.AnuncioServico;
import servico.CategoriaServico;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

@ManagedBean
@SessionScoped
public class AnuncioBean implements Serializable {

	@EJB
	private AnuncioServico anuncioServico;

	@EJB
	private CategoriaServico categoriaServico;

	private Anuncio anuncio;

	private Usuario usuario;

	private List<Categoria> categorias;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getCurrentInstance().getExternalContext().getSession(false);
		this.usuario = (Usuario) session.getAttribute("usuario");

		setCategorias(categoriaServico.getCategorias());

		this.anuncio = new Anuncio();

	}

	public void salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.anuncio.setUsuario(usuario);

			this.anuncio.setFinalizado(false);
			this.anuncio.setActive(true);

			anuncioServico.salvarAnuncio(this.anuncio);

			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Anúncio cadastrado com sucesso!", null));

			this.anuncio = new Anuncio();

		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro para cadastrar Anúncio!", null));
		}
	}

	public Anuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}

	public AnuncioBean() {
		// TODO Auto-generated constructor stub
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

}
