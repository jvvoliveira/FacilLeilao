package entidades;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({ 
	
		@NamedQuery(
			name = "Usuario.findByEmailSenha",
			query = "SELECT c FROM Usuario c WHERE c.email = :email AND c.senha = :senha"),
		@NamedQuery(
				name = "Usuario.PorId",
				query = "SELECT c FROM Usuario c WHERE c.id = :id"),
})

@Table(name = "TB_USUARIO")
public class Usuario extends Entidade{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
    public static final String FIND_BY_EMAIL_SENHA = "Usuario.findByEmailSenha";
	@Transient
    public static final String POR_ID = "Usuario.PorId";


	
	@NotNull(message = "Email do usuário não pode ser nulo")
    @Size(max = 100, message = "Caracteres a mais no email do usuário")
	@Email(message = "Email inválido")
    @Column(name = "EMAIL")
    private String email;
    
	@NotNull(message = "Nome do usuário não pode ser nulo")
	@Size(max = 100, message = "Caracteres a mais no nome do usuário")
    @Column(name = "NOME")
    private String nome;
    
	@NotNull(message = "Telefone do usuário não pode ser nulo")
    @Size(min = 8, max = 16, message = "Quantidade incorreta de dígitos no telefone do usuário")
    @Column(name = "TELEFONE")
    private String telefone;
    
	@NotNull(message = "Senha do usuário não pode ser nula")
	@Size(min = 6, message = "Quantidade incorreta de dígitos na senha do usuário")
    @Column(name = "SENHA")
    private String senha;
    
	 @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 private List<Lance> lances;
	 
	 @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 private List<Anuncio> anuncios;
	 
	 @ManyToMany(mappedBy = "usuarios")
	    private List<Categoria> categorias;
	 
	 
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
	
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
