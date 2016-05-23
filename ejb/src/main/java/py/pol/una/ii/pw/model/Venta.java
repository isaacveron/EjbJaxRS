package py.pol.una.ii.pw.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by fede on 11/10/15.
 */
@Entity
@XmlRootElement
@Table(name = "VENTA")
public class Venta {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @OneToMany(mappedBy = "venta")
    private List<VentaDetalle> ventaDetalles;

    @ManyToOne
    private Usuario usuario;

    public Venta() {
    }

    public Venta(Usuario usuario, Date fecha, List<VentaDetalle> ventaDetalles) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.ventaDetalles = ventaDetalles;
    }

    public Venta(Date fecha, Usuario usuario) {
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<VentaDetalle> getVentaDetalles() {
        return ventaDetalles;
    }

    public void setVentaDetalles(List<VentaDetalle> ventaDetalles) {
        this.ventaDetalles = ventaDetalles;
    }

    public Usuario getClient() {
        return usuario;
    }

    public void setClient(Usuario usuario) {
        this.usuario = usuario;
    }


}
