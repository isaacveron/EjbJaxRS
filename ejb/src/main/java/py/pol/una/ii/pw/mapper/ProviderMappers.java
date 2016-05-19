package py.pol.una.ii.pw.mapper;
import java.util.List;
import py.pol.una.ii.pw.model.Provider;
/**
 * Created by isaacveron on 28/4/16.
 */


public interface ProviderMappers {

    public List<Provider> findAllOrderedByName();

    public Provider findById (long id);

    public void register (Provider client);
}