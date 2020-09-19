package entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_ANUNCIO")
public class Anuncio implements Serializable{
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
    
    @Column(name = "NOME")
    @NotNull
    private String nome;
    
    @Column(name = "DESCRICAO")
    @NotNull
    private String descricao;
    
    @Column(name = "VALOR_BASE")
    @NotNull
    private float valorBase;
    
    @Column(name = "IS_ACTIVE")
    @NotNull
    private boolean isActive;
    
    @Column(name = "PRAZO")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date prazo;
    
    @Column(name = "FINALIZADO")
    @NotNull
    private String finalizado;

    
    
	public Anuncio() {
	}

	public Anuncio(Long id, String nome, String descricao, float valorBase, boolean isActive, Date prazo,
			String finalizado) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.valorBase = valorBase;
		this.isActive = isActive;
		this.prazo = prazo;
		this.finalizado = finalizado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getValorBase() {
		return valorBase;
	}

	public void setValorBase(float valorBase) {
		this.valorBase = valorBase;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getPrazo() {
		return prazo;
	}

	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}

	public String getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(String finalizado) {
		this.finalizado = finalizado;
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
