package py.pol.una.ii.pw.service;

/**
 * Created by isaacveron on 16/3/16.
 */
import py.pol.una.ii.pw.data.ProductoDuplicadoRepository;
import py.pol.una.ii.pw.model.Product;
import py.pol.una.ii.pw.model.ProductoDuplicado;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;


@Stateless
public class ProductoDuplicadoRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private  ProductoDuplicado pd;

    @Inject
    private  ProductoDuplicadoRepository pdr;


    public void registrarProductoDuplicado(Product product){

        Boolean a = false;
        List<ProductoDuplicado> productoDuplicadoList = pdr.findAllOrderedByName();

        for(ProductoDuplicado productoDuplicado: productoDuplicadoList)
        {
            if (productoDuplicado.getProductoDuplicado().equals(product)) {
                productoDuplicado.setCantidad(productoDuplicado.getCantidad()+1);
                em.persist(productoDuplicado);
                a = true;
            }
        }

        if(!a){
            pd.setCantidad(1);
            pd.setProductoDuplicado(product);
            em.merge(pd);
        }
    }
}
