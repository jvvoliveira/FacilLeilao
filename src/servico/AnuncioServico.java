package servico;

import static javax.persistence.PersistenceContextType.TRANSACTION;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Anuncio;;

@Stateless
@LocalBean
public class AnuncioServico {

	@PersistenceContext(name = "dbLeilaoFacil", type = TRANSACTION)
	private EntityManager entityManager;
	
	public void salvarAnuncio(Anuncio anuncio) {
		entityManager.persist(anuncio);
	}
	
}
