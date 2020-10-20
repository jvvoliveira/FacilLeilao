package beans;

import java.time.LocalDateTime;
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
	private List<Lance> lancesByAnuncio; 
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
	
	public List<Lance> getLancesByAnuncio(float idAnuncio){
		lancesByAnuncio = lanceServico.getLancesByAnuncio(idAnuncio);
		return lancesByAnuncio;
		
	}

	public void todosLancesDireto() {
		List<Anuncio> anunciosProprios = anuncioServico.getAnunciosByUsuarioVendedor(this.usuario.getId());
		List<Lance> lancesDireto = new ArrayList<Lance>();
		for (int index = 0; index < anunciosProprios.size(); index++) {
			Anuncio anuncio = anunciosProprios.get(index);
			LocalDateTime dateTime = LocalDateTime.parse(anuncio.getPrazo());
			if (anuncio.getLanceDiretoVencedor() == null && dateTime.isAfter(LocalDateTime.now())) {
				List<Lance> lancesDiretoAnuncio = lanceServico.getLancesDiretoByAnuncio(anuncio.getId());
				for (int i = 0; i < lancesDiretoAnuncio.size(); i++) {
					lancesDireto.add(lancesDiretoAnuncio.get(i));
				}
			}
		}
		setLancesDiretoRecebidos(lancesDireto);
	}

	public void aceitarLanceDireto(Lance lance) {
		FacesContext context = FacesContext.getCurrentInstance();
		LocalDateTime dateTime = LocalDateTime.parse(lance.getAnuncio().getPrazo().replace('/', '-').replace(' ', 'T'));
		if(dateTime.isBefore(LocalDateTime.now()) || lance.getAnuncio().getFinalizado()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Leilão de "+lance.getAnuncio().getNome()+" já foi finalizado", null));
			return;
		}

		Anuncio anuncio = lance.getAnuncio();
		anuncio.setLanceDiretoVencedor(lance);
		anuncio.setFinalizado(true);

		boolean result = anuncioServico.updateAnuncio(anuncio);

		if (result) {
			List<Lance> lancesDiretoAnuncio = lanceServico.getLancesDiretoByAnuncio(anuncio.getId());
			for (int i = 0; i < lancesDiretoAnuncio.size(); i++) {
				if (lancesDiretoAnuncio.get(i).getId() != lance.getId()) {
					lanceServico.excluirLance(lancesDiretoAnuncio.get(i).getId());
				}
			}
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lance direto de "
					+ lance.getUsuario().getNome() + " no valor de R$" + lance.getValor() + " foi aceito!", null));
			todosLancesDireto();
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Algo de errado aconteceu", null));
		}
	}

	public void apagarLanceDireto(Lance lance) {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean result = this.lanceServico.excluirLance(lance.getId());
		if (result) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lance direto de "
					+ lance.getUsuario().getNome() + " no valor de R$" + lance.getValor() + " foi recusado", null));
			todosLancesDireto();
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Algo de errado aconteceu", null));
		}
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
