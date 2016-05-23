package py.pol.una.ii.pw.util;

import py.pol.una.ii.pw.data.UsuarioRepository;
import py.pol.una.ii.pw.model.Usuario;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Created by German Garcia on 5/22/16.
 */
@ValidarVenta
@Interceptor
public class ValidarVentaInterceptor {

    @Inject
    UsuarioRepository servicio;

    @AroundInvoke
    public Object verificaLogin(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        String token = (String) parameters[0];
        System.out.println("Token recibido: "+token);
        try {
            Usuario user = servicio.findByTokenVenta(token);
            if(user != null){
                System.out.println("El usuario tiene permisos para realizar ventas");
                return ctx.proceed();
            }else{
                System.out.println("No se encuentra el token con el rol venta en la BD, se debe redireccionar al LOGIN");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error llamando a ctx.proceed en verificaLogin()");
            e.printStackTrace();
            return null;
        }
    }
}
