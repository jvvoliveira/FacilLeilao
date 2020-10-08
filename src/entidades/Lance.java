package entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({ 
	@NamedQuery(
		name = "Lance.findLancesByAnuncio",
		query = "SELECT l FROM Lance l WHERE l.anuncio.id = :idAnuncio AND l.direto = 0")
})
@Table(name = "TB_LANCE")
public class Lance implements Serializable{
	
	@Transient
    public static final String FIND_LANCE_BY_ANUNCIO = "Lance.findLancesByAnuncio";
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
    
    @Column(name = "VALOR")
    @NotNull(message = "o valor dever ser preenchido")
    private float valor;
    
    @Column(name = "DIRETO")
    @NotNull(message = "O tipo de lance direto/indireto deve ser informado")
    private boolean direto;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ANUNCIO", referencedColumnName = "ID")
    private Anuncio anuncio;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    private Usuario usuario;
    
	public Lance() {
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public float getValor() {
		return valor;
	}



	public void setValor(float valor) {
		this.valor = valor;
	}



	public boolean getDireto() {
		return direto;
	}



	public void setDireto(boolean direto) {
		this.direto = direto;
	}


	public Anuncio getAnuncio() {
		return anuncio;
	}


	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anuncio other = (Anuncio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    
    
    
}
