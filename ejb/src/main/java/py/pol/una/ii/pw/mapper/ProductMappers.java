package py.pol.una.ii.pw.mapper;

import py.pol.una.ii.pw.model.Product;
/**
 * Created by isaacveron on 3/5/16.
 */

public interface ProductMappers {

    public void register(Product p);
    public void merge (Product p);
    public Product findById(Long id);
}

