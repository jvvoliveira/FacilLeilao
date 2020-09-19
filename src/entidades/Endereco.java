package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_ENDERECO")
public class Endereco implements Serializable{

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@NotNull(message = "Nome da rua não pode ser nulo")
    @Size(max = 50, message = "Caracteres a mais no nome da rua")
	@Column(name = "RUA")
	private String rua;
	
	@NotNull(message = "Nome da cidade não pode ser nulo")
    @Size(max = 50, message = "Caracteres a mais no nome da cidade")
	@Column(name = "CIDADE")
	private String cidade;
	
	@NotNull(message = "Número do endereço não pode ser nulo")
	@Column(name = "NUMERO")
	private int numero;
	
	@NotNull(message = "CEP não pode ser nulo")
    @Size(min = 8, max = 9, message = "Caracteres incorretos no CEP")
	@Column(name = "CEP")
	private String cep;
	
    @Size(max = 200, message = "Caracteres a mais no complemento de endereço")
	@Column(name = "COMPLEMENTO")
	private String complemento;
	
    @NotNull(message = "Estado não pode ser nulo")
    @Size(max = 50, message = "Caracteres a mais no Estado")
	@Column(name = "ESTADO")
	private String estado;
	
    @NotNull(message = "Is active não pode ser nulo")
	@Column(name = "IS_ACTIVE")
	private boolean isActive;

	public Endereco() {
		super();
	}
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getRua() {
		return rua;
	}



	public void setRua(String rua) {
		this.rua = rua;
	}



	public String getCidade() {
		return cidade;
	}



	public void setCidade(String cidade) {
		this.cidade = cidade;
	}



	public int getNumero() {
		return numero;
	}



	public void setNumero(int numero) {
		this.numero = numero;
	}



	public String getCep() {
		return cep;
	}



	public void setCep(String cep) {
		this.cep = cep;
	}



	public String getComplemento() {
		return complemento;
	}



	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public boolean isActive() {
		return isActive;
	}



	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
