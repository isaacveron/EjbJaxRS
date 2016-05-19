package py.pol.una.ii.pw.model;

/**
 * Created by isaacveron on 7/3/16.
 */
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Entity
@XmlRootElement
@Table(name = "CLIENT")
public class Client implements Serializable {
    /** 242352345 **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 1, max = 25)
    private String name;

    @OneToMany(mappedBy = "client")
    private List<Venta> venta;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Client() {
    }

    public Client(String name, List<Venta> venta) {
        this.name = name;
        this.venta = venta;
    }

    public Client(String name) {
        this.name = name;
    }
}