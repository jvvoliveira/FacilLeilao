package servico;

import static javax.persistence.PersistenceContextType.TRANSACTION;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import entidades.Anuncio;
import entidades.Usuario;;

@Stateless
@LocalBean
public class AnuncioServico {

	@PersistenceContext(name = "dbLeilaoFacil", type = TRANSACTION)
	private EntityManager entityManager;
	
	public void salvarAnuncio(Anuncio anuncio) {
		entityManager.persist(anuncio);
	}
	
	 public List<Usuario> AnuncioPorUserID(@NotNull Usuario User) {
		 	
	        TypedQuery<Usuario> query = entityManager.createNamedQuery(Usuario.POR_ID, Usuario.class);
	        query.setParameter(1, User.getId());
	        return query.getResultList();
	    }
	
}
