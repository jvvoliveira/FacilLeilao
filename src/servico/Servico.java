package servico;

import static javax.persistence.PersistenceContextType.TRANSACTION;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import entidades.Entidade;;


@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class Servico<T> extends Entidade {

    @PersistenceContext(name = "dbLeilaoFacil", type = TRANSACTION)
    protected EntityManager entityManager;
    protected Class<T> classe;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    protected void setClasse(@NotNull Class<T> classe) {
        this.classe = classe;
    }
	
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public abstract T criar();

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public boolean existe(@NotNull T entidade) {
        return true;
    }

    public void persistir(@Valid T entidade) {
        if (!existe(entidade)) {
            entityManager.persist(entidade);
        }
    }

    public T atualizar(@Valid T entidade) {
        if (existe(entidade)) {
            entidade = entityManager.merge(entidade);
            entityManager.flush();
        }
        
        return entidade;
    }
    
    
}
