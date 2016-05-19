package py.pol.una.ii.pw.mapper;


import py.pol.una.ii.pw.model.Compra;
import py.pol.una.ii.pw.model.CompraDetalle;

/**
 * Created by isaacveron on 3/5/16.
 */

public interface CompraMappers {

        public void register(Compra c);
        public void registerDetalle (CompraDetalle c);
}

