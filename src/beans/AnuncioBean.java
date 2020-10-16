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
import org.omg.CORBA.Current;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import java.time.LocalDate;
import java.time.Month;

@ManagedBean
@SessionScoped
public class AnuncioBean implements Serializable {

	@EJB
	private AnuncioServico anuncioServico;

	@EJB
	private CategoriaServico categoriaServico;

	private Anuncio anuncio;

	private Usuario usuario;

	private String data;
	private String hora;

	private Boolean dataValida, horaValida;
	private boolean anuncioCondicional;

	private List<Categoria> categorias;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getCurrentInstance().getExternalContext().getSession(false);
		this.usuario = (Usuario) session.getAttribute("usuario");

		setCategorias(categoriaServico.getCategorias());

		this.anuncio = new Anuncio();
		this.anuncioCondicional = false;
	}

	public void salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.anuncio.setUsuario(usuario);

			FormatarDataHora();
			if (dataValida == true && horaValida == true) {
				this.anuncio.setFinalizado(false);
				this.anuncio.setActive(true);
				
				if(this.anuncioCondicional) {
					this.anuncio.setValorBase(null);
				}

				anuncioServico.salvarAnuncio(this.anuncio);

				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Anúncio cadastrado com sucesso!", null));

				this.anuncio = new Anuncio();
				this.data = new String();
				this.hora = new String();
			}

		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro para cadastrar Anúncio!", null));
		}
	}

	private void FormatarDataHora() {
		String prazoFormatado;
		prazoFormatado = ValidarData() + "T" + ValidarHora();
		this.anuncio.setPrazo(prazoFormatado);
	}

	private String ValidarData() {
		FacesContext context = FacesContext.getCurrentInstance();
		String dataFormatada;
		int ano;
		int mes;
		int dia;
		ano = Integer.parseInt(this.data.substring(6, 10));
		mes = Integer.parseInt(this.data.substring(3, 5));
		dia = Integer.parseInt(this.data.substring(0, 2));
		LocalDate currentdate = LocalDate.now();
		dataValida = true;
		if (ano < currentdate.getYear()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ano inválido! Por favor cadastre uma data futura.", null));
			dataValida = false;
		} else if (mes < currentdate.getMonthValue() && ano == currentdate.getYear()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Mês inválido! Por favor cadastre uma data futura.", null));
			dataValida = false;
		} else if (dia < currentdate.getDayOfMonth() && mes == currentdate.getMonthValue()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Dia inválido! Por favor cadastre uma data futura.", null));
			dataValida = false;
		} else if (ano + currentdate.getYear() > 2 * currentdate.getYear() + 1) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Anúncios não podem se estender por tanto tempo assim.", null));
			dataValida = false;
		}
		dataFormatada = String.valueOf(ano) + "-" + String.valueOf(mes) + "-" + String.valueOf(dia);

		return dataFormatada;
	}

	private String ValidarHora() {
		FacesContext context = FacesContext.getCurrentInstance();
		Integer horaF;
		Integer minutos;
		String horaFormatada;
		horaF = Integer.parseInt(this.hora.substring(0, 2));
		minutos = Integer.parseInt(this.hora.substring(3, 5));
		horaValida = true;
		if (horaF > 24 || horaF < 0) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Hora inválida! Por favor cadastre uma hora no sistema horário de 24 horas.", null));
			horaValida = false;
		} else if (minutos > 60 || minutos < 0) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Minutos inválido! Por favor cadastre os minutos até 59.", null));
			horaValida = false;
		} else if (horaF == 24 && minutos > 0) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor cadastre uma hora no sistema horário de 24 horas.", null));
			horaValida = false;
		}
		// Formatar a hora para formato 00:00:00 para não ficar 00:0:00
		String aux = String.valueOf(minutos);
		if (minutos < 10) {
			aux = "0"+aux;
		}
		String horaAux = String.valueOf(horaF);
		if (horaF < 10) {
			horaAux = "0"+horaAux;
		}

		horaFormatada = horaAux + ":" + aux + ":00";
		return horaFormatada;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public boolean isAnuncioCondicional() {
		return anuncioCondicional;
	}

	public void setAnuncioCondicional(boolean anuncioCondicional) {
		this.anuncioCondicional = anuncioCondicional;
	}
	
	

}
