package py.pol.una.ii.pw.mapper;
import py.pol.una.ii.pw.model.Usuario;

/**
 * Created by German Garcia on 5/22/16.
 */
public interface UsuarioMappers {

    public Usuario findByTokenCompra(String token);

    public Usuario findByTokenVenta(String token);

    public void updateToken(Usuario user);

}