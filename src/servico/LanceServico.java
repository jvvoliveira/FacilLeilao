package servico;

import static javax.persistence.PersistenceContextType.TRANSACTION;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entidades.Lance;

@Stateless
@LocalBean
public class LanceServico {

	@PersistenceContext(name = "dbLeilaoFacil", type = TRANSACTION)
	private EntityManager entityManager;
	
	public void salvarLance(Lance lance) {
		entityManager.persist(lance);
	}
	
	public List<Lance> getLancesByAnuncio(float idAnuncio) {
		TypedQuery<Lance> query = (TypedQuery<Lance>) entityManager.createNamedQuery(Lance.FIND_LANCE_BY_ANUNCIO);
		query.setParameter("idAnuncio", idAnuncio);
		return query.getResultList();
	}
	
	public List<Lance> getLancesDiretoByAnuncio(float idAnuncio) {
		TypedQuery<Lance> query = (TypedQuery<Lance>) entityManager.createNamedQuery(Lance.FIND_LANCE_DIRETO_BY_ANUNCIO);
		query.setParameter("idAnuncio", idAnuncio);
		return query.getResultList();
	}
	
}
