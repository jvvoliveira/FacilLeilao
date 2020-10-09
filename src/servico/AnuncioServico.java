package servico;

import static javax.persistence.PersistenceContextType.TRANSACTION;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entidades.Anuncio;
import entidades.Usuario;

@Stateless
@LocalBean
public class AnuncioServico {

	@PersistenceContext(name = "dbLeilaoFacil", type = TRANSACTION)
	private EntityManager entityManager;
	
	public void salvarAnuncio(Anuncio anuncio) {
		entityManager.persist(anuncio);
	}
	
	public List<Anuncio> getAnuncios() {
		TypedQuery<Anuncio> query = (TypedQuery<Anuncio>) entityManager.createNamedQuery(Anuncio.FIND_ALL_OPEN);
		return query.getResultList();
	}
	
	public List<Anuncio> getAnunciosByCategoria(String categoria) {
		TypedQuery<Anuncio> query = (TypedQuery<Anuncio>) entityManager.createNamedQuery(Anuncio.FIND_BY_CATEGORIA);
		query.setParameter("categoria", categoria);
		return query.getResultList();
	}
	
	public List<Anuncio> getAnunciosByUsuarioDeuLance(Long UsuarioId) {
		TypedQuery<Anuncio> query = (TypedQuery<Anuncio>) entityManager.createNamedQuery(Anuncio.FIND_BY_USUARIO_COMPRADOR);
		query.setParameter("usuarioid", UsuarioId);
		return query.getResultList();
	}
	
	public List<Anuncio> getAnunciosByUsuarioVendedor(Long UsuarioId) {
		TypedQuery<Anuncio> query = (TypedQuery<Anuncio>) entityManager.createNamedQuery(Anuncio.FIND_BY_USUARIO_VENDEDOR);
		query.setParameter("usuarioid", UsuarioId);
		return query.getResultList();
	}
	
	public boolean updateAnuncio(Anuncio anuncio) {
		try {
			entityManager.merge(anuncio);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
