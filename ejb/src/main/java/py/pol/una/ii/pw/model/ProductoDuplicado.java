package py.pol.una.ii.pw.model;

/**
 * Created by isaacveron on 15/3/16.
 */

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;


@Entity
@XmlRootElement
@Table(name = "PRODUCTO_DUPLICADO")
public class ProductoDuplicado implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="PRODUCTO_DUPLICADO_ID",insertable=true, updatable=true,nullable=true,unique=true)
    private Product productoDuplicado;

    private long cantidad;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }
    public void setProductoDuplicado(Product producto_duplicado){this.productoDuplicado = producto_duplicado; }

    public Product getProductoDuplicado(){return  productoDuplicado;}

    public ProductoDuplicado(long cantidad) {
        this.cantidad = cantidad;
    }

    public ProductoDuplicado() {
    }
}
