package entidades;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_CATEGORIA")
public class Categoria extends Entidade {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Nome da categoria não pode ser nulo")
    @Size(max = 100, message = "Caracteres a mais no nome da categoria")
    @Column(name = "NOME")
    private String nome;

	 @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 private List<Anuncio> anuncios;
	 
	 @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(name = "CATEGORIA_USUARIO", joinColumns = {
	        @JoinColumn(name = "ID_CATEGORIA")},
	            inverseJoinColumns = {@JoinColumn(name = "ID_USUARIO")}
	         )
	private List<Usuario> usuarios;
	 
	
	public Categoria() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

	public List<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(List<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}
	
}
