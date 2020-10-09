package beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import entidades.Anuncio;
import entidades.Categoria;
import entidades.Lance;
import entidades.Usuario;
import servico.AnuncioServico;
import servico.CategoriaServico;
import servico.LanceServico;

@ManagedBean
@SessionScoped
public class LeilaoBean implements Serializable{
	
	@EJB
	private AnuncioServico anuncioServico;
	
	@EJB
	private CategoriaServico categoriaServico;
	
	@EJB
	private LanceServico lanceServico;
	
	private List<Anuncio> anunciosDisponiveis;
	
	private List<Anuncio> leiloesVencidos;
	
	private List<Categoria> categorias;
	
	private Anuncio anuncioSelecionado;
	private boolean lanceDireto;
	private float valorLanceDireto;
	private Lance maiorLanceAnuncioSelecionado;
	
	
	private Usuario usuario;
	
	@PostConstruct
    public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getCurrentInstance().getExternalContext().getSession(false);
		this.usuario = (Usuario) session.getAttribute("usuario");
		
		todosAnunciosDisponiveis();
		setCategorias(categoriaServico.getCategorias());
		filtrarLeiloesVencidos();
		
		this.lanceDireto = false;
    }
	
	public void todosAnunciosDisponiveis() {
		filtrarAnunciosDisponiveis(anuncioServico.getAnuncios());
	}
	
	private void filtrarAnunciosDisponiveis(List<Anuncio> anuncios) {
		List<Anuncio> anunciosDisponiveis = new ArrayList<Anuncio>();
		
		for(int i = 0; i < anuncios.size(); i++) {
			Anuncio anuncio = anuncios.get(i);
			LocalDateTime dateTime = LocalDateTime.parse(anuncios.get(i).getPrazo());
			if(dateTime.isAfter(LocalDateTime.now())) {
				String textoPrazo = dateTime.toString().replace('-', '/');
				textoPrazo = textoPrazo.replace('T', ' ');
				anuncio.setPrazo(textoPrazo);
				anunciosDisponiveis.add(anuncio);
			}
		}
		setAnunciosDisponiveis(anunciosDisponiveis);
	}
	
	
	private List<Anuncio> filtrarAnunciosFinalizados(List<Anuncio> anuncios) {
		List<Anuncio> anunciosFinalizados = new ArrayList<Anuncio>();
		
		for(int i = 0; i < anuncios.size(); i++) {
			Anuncio anuncio = anuncios.get(i);
			LocalDateTime dateTime = LocalDateTime.parse(anuncios.get(i).getPrazo());
			if(dateTime.isBefore(LocalDateTime.now()) || anuncio.getFinalizado()) {
				String textoPrazo = dateTime.toString().replace('-', '/');
				textoPrazo = textoPrazo.replace('T', ' ');
				anuncio.setPrazo(textoPrazo);
				anunciosFinalizados.add(anuncio);
			}
		}
		return anunciosFinalizados;
	}
	
	public void filtrarAnunciosPorCategoria(String categoria) {
		filtrarAnunciosDisponiveis(anuncioServico.getAnunciosByCategoria(categoria));
	}
	
	public Lance getMaiorLance(Anuncio anuncio) {
		Lance maiorLance = new Lance();
		float maiorValor = 0;
		try {
			List<Lance> lances = lanceServico.getLancesByAnuncio(anuncio.getId());
			if(lances.isEmpty()) {
				maiorLance.setValor(anuncio.getValorBase());
			}else {
				for(int i = 0; i < lances.size(); i++) {
					if(lances.get(i).getValor() > maiorValor) {
						maiorValor = lances.get(i).getValor();
						maiorLance = lances.get(i); 
					}
				}				
			}
			return maiorLance;			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void filtrarLeiloesVencidos() {
		Lance maiorLance = new Lance();
		try {
			List<Anuncio> anunciosVencidos = new ArrayList<Anuncio>();
			List<Anuncio> anuncios = filtrarAnunciosFinalizados(anuncioServico.getAnunciosByUsuarioDeuLance(usuario.getId()));
			for(int i = 0; i < anuncios.size(); i++) {
				if(anuncios.get(i).getFinalizado()) {
					if(anuncios.get(i).getLanceDiretoVencedor().getUsuario().getId() == usuario.getId()) {
						anunciosVencidos.add(anuncios.get(i));
					}
				}else {
					maiorLance = getMaiorLance(anuncios.get(i));
					if(maiorLance.getUsuario() != null) {
						if(maiorLance.getUsuario().getId() == usuario.getId()) {
							anunciosVencidos.add(anuncios.get(i));
						}											
					}
				}
			}
			setLeiloesVencidos(anunciosVencidos);		
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void darLance(float valor) {
		FacesContext context = FacesContext.getCurrentInstance();
		LocalDateTime dateTime = LocalDateTime.parse(this.anuncioSelecionado.getPrazo().replace('/', '-').replace(' ', 'T'));
		if(dateTime.isBefore(LocalDateTime.now()) && !this.anuncioSelecionado.getFinalizado()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Leilão de "+this.anuncioSelecionado.getNome()+" já foi finalizado", null));
		}else {
			valor = Math.round(valor);
			float maiorLance = getMaiorLance(this.anuncioSelecionado).getValor();
			if(valor > maiorLance) {
				Lance lance = new Lance();
				lance.setAnuncio(this.anuncioSelecionado);
				lance.setUsuario(this.usuario);
				lance.setValor(valor);
				lance.setDireto(lanceDireto);
				
				lanceServico.salvarLance(lance);
				if(lanceDireto) {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lance direto de R$"+valor+" efetuado em "+this.anuncioSelecionado.getNome()+" aguarde pela decisão do vendedor", null));								
				}else {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lance de R$"+valor+" efetuado em "+this.anuncioSelecionado.getNome(), null));								
				}
			}else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seu lance não pode ser menor que o lance atual de R$"+maiorLance, null));
			}			
		}
	}
	
	

	public Lance getMaiorLanceAnuncioSelecionado() {
		return maiorLanceAnuncioSelecionado;
	}

	public void setMaiorLanceAnuncioSelecionado(Lance maiorLanceAnuncioSelecionado) {
		this.maiorLanceAnuncioSelecionado = maiorLanceAnuncioSelecionado;
	}

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
	
	

	public void setLeiloesVencidos(List<Anuncio> leiloesVencidos) {
		this.leiloesVencidos = leiloesVencidos;
	}
	
	public List<Anuncio> getLeiloesVencidos() {
		return leiloesVencidos;
	}

	public Anuncio getAnuncioSelecionado() {
		return anuncioSelecionado;
	}

	public void setAnuncioSelecionado(Anuncio anuncioSelecionado) {
		Lance lance = getMaiorLance(anuncioSelecionado);
		setMaiorLanceAnuncioSelecionado(lance);
		this.anuncioSelecionado = anuncioSelecionado;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public boolean getLanceDireto() {
		return lanceDireto;
	}

	public void setLanceDireto(boolean lanceDireto) {
		this.lanceDireto = lanceDireto;
	}

	public float getValorLanceDireto() {
		return valorLanceDireto;
	}

	public void setValorLanceDireto(float valorLanceDireto) {
		this.valorLanceDireto = valorLanceDireto;
	}
	
	
	
}
