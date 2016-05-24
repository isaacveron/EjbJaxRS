package py.pol.una.ii.pw.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.omg.CORBA.UnknownUserException;

/**
 * Created by fede on 11/10/15.
 */
@Entity
@XmlRootElement
@Table(name = "COMPRA")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Compra {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @OneToMany(mappedBy = "compra")
    private List<CompraDetalle> compraDetalles;

    @ManyToOne
    private Usuario usuario;

    public Compra() {
    }

    public Compra(Date fecha, Usuario usuario){
        this.fecha=new Date(fecha.getTime());
        this.usuario=usuario;
    }

    public Compra(Date fecha, List<CompraDetalle> compraDetalles, Usuario usuario) {
        this.fecha = new Date(fecha.getTime());
        this.compraDetalles = compraDetalles;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return (Date)fecha.clone();
    }

    public void setFecha(Date fecha) {
        this.fecha = new Date(fecha.getTime());
    }

    public List<CompraDetalle> getCompraDetalles() {
        return compraDetalles;
    }

    public void setCompraDetalles(List<CompraDetalle> compraDetalles) {
        this.compraDetalles = compraDetalles;
    }

    public Usuario getProvider() {
        return usuario;
    }

    public void setProvider(Usuario usuario) {
        this.usuario = usuario;
    }
}
