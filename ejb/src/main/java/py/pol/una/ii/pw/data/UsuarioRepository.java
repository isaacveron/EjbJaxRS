package py.pol.una.ii.pw.data;

import org.apache.ibatis.session.SqlSession;
import py.pol.una.ii.pw.mapper.UsuarioMappers;
import py.pol.una.ii.pw.model.Usuario;
import py.pol.una.ii.pw.util.MyBatisUtil;

/**
 * Created by German Garcia on 5/22/16.
 */
public class UsuarioRepository {

    public Usuario findByTokenCompra(String token) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try
        {
            UsuarioMappers Mapper = sqlSession.getMapper(UsuarioMappers.class);
            return Mapper.findByTokenCompra(token);
        } finally
        {
            sqlSession.close();
        }
    }

    public Usuario findByTokenVenta(String token) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try
        {
            UsuarioMappers Mapper = sqlSession.getMapper(UsuarioMappers.class);
            return Mapper.findByTokenVenta(token);
        } finally
        {
            sqlSession.close();
        }
    }
}
