package entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_USUARIO")
public class Usuario implements Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@NotNull(message = "Email do usuário não pode ser nulo")
    @Size(max = 100, message = "Caracteres a mais no email do usuário")
    @Column(name = "EMAIL")
    private String email;
    
	@NotNull(message = "Nome do usuário não pode ser nulo")
	@Size(max = 100, message = "Caracteres a mais no nome do usuário")
    @Column(name = "NOME")
    private String nome;
    
	@NotNull(message = "Telefone do usuário não pode ser nulo")
    @Size(min = 8, max = 13 ,message = "Quantidade incorreta de dígitos no telefone do usuário")
    @Column(name = "TELEFONE")
    private String telefone;
    
	@NotNull(message = "Is Active do usuário não pode ser nulo")
    @Column(name = "IS_ACTIVE")
    private boolean isActive;
    
	@NotNull(message = "Senha do usuário não pode ser nula")
	@Size(min = 6, max = 20, message = "Quantidade incorreta de dígitos na senha do usuário")
    @Column(name = "SENHA")
    private String senha;
    
	

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
}
