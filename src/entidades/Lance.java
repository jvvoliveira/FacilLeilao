package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_LANCE")
public class Lance extends Entidade {
	
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "VALOR")
    @NotNull(message = "o valor dever ser preenchido")
    private float valor;
    
    @Column(name = "DIRETO")
    @NotNull(message = "O tipo de lance direto/indireto deve ser informado")
    private int direto;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ANUNCIO", referencedColumnName = "ID")
    private Anuncio anuncio;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    private Usuario usuario;
    
	public Lance() {
	}



	public float getValor() {
		return valor;
	}



	public void setValor(float valor) {
		this.valor = valor;
	}



	public int getDireto() {
		return direto;
	}



	public void setDireto(int direto) {
		this.direto = direto;
	}
    
    
}
