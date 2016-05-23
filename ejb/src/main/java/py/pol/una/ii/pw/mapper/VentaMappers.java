package py.pol.una.ii.pw.mapper;


import py.pol.una.ii.pw.model.Venta;
import py.pol.una.ii.pw.model.VentaDetalle;

/**
 * Created by isaacveron on 3/5/16.
 */

public interface VentaMappers {

    public void register(Venta c);
    public void registerDetalle (VentaDetalle c);
    public Venta findById(Long id);
}

