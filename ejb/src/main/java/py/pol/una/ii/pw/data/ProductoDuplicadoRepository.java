package py.pol.una.ii.pw.data;

import py.pol.una.ii.pw.model.ProductoDuplicado;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by isaacveron on 16/3/16.
 */

@Stateless
public class ProductoDuplicadoRepository {

    @Inject
    private EntityManager em;


    public List<ProductoDuplicado> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductoDuplicado> criteria = cb.createQuery(ProductoDuplicado.class);
        Root<ProductoDuplicado> pd = criteria.from(ProductoDuplicado.class);
        criteria.select(pd).orderBy(cb.asc(pd.get("id")));

        return em.createQuery(criteria).getResultList();
    }

    public void register(ProductoDuplicado c){
        this.em.persist( c );
    }
}
