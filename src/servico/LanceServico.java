package servico;

import static javax.persistence.PersistenceContextType.TRANSACTION;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Lance;

@Stateless
@LocalBean
public class LanceServico {

	@PersistenceContext(name = "dbLeilaoFacil", type = TRANSACTION)
	private EntityManager entityManager;
	
	public void salvarLance(Lance lance) {
		entityManager.persist(lance);
	}
	
}
