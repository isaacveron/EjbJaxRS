package py.pol.una.ii.pw.mapper;
import java.util.List;
import py.pol.una.ii.pw.model.Client;
/**
 * Created by isaacveron on 28/4/16.
 */


public interface ClientMappers {

    public List<Client> findAllOrderedByName();

    public Client findById (long id);

    public void register (Client client);
}