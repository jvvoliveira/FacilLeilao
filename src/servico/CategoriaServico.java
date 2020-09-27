package servico;

import static javax.persistence.PersistenceContextType.TRANSACTION;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entidades.Anuncio;
import entidades.Categoria;

@Stateless
@LocalBean
public class CategoriaServico {

	@PersistenceContext(name = "dbLeilaoFacil", type = TRANSACTION)
	private EntityManager entityManager;
	
	public void salvarCategoria(Categoria categoria) {
		entityManager.persist(categoria);
	}
	
	public List<Categoria> getCategorias() {
		TypedQuery<Categoria> query = (TypedQuery<Categoria>) entityManager.createNamedQuery(Categoria.FIND_ALL);
		return query.getResultList();
	}
	
}
