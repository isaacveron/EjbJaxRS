package py.pol.una.ii.pw.model;

/**
 * Created by isaacveron on 13/5/16.
 */

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table(name = "USUARIO")
public class Usuario implements Serializable {
    /** 242352345 **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 1, max = 25)
    private String name;

    @NotNull
    @Size(min = 1, max = 25)
    private String pass;

    @NotNull
    @Size(min = 1, max = 25)
    private String access_token;


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

    public String getPass (){ return pass;}

    public void setPass (String pass){ this.pass=pass; }

    public void setAccess_token(String access_token){ this.access_token=access_token;}

    public String getAccess_token (){ return access_token;}

    public Usuario() {
    }

    public Usuario(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }
}
