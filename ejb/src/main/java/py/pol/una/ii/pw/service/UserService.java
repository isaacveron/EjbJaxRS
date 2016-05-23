/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package py.pol.una.ii.pw.service;

import org.apache.ibatis.session.SqlSession;
import py.pol.una.ii.pw.mapper.UsuarioMappers;
import py.pol.una.ii.pw.model.Usuario;
import py.pol.una.ii.pw.util.MyBatisUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class UserService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;


    public String login(Usuario user) throws Exception {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        String token;
        try {
            Long tiempo = System.currentTimeMillis();
            token = tiempo.toString();
            user.setAccess_token(token);
            UsuarioMappers Mapper = sqlSession.getMapper(UsuarioMappers.class);
            Mapper.updateToken(user);
            sqlSession.commit();
            return token;
        } finally {
            sqlSession.close();
        }
    }

    public String logout(Usuario user) throws Exception {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        String token = "";
        try {
            user.setAccess_token(token);
            UsuarioMappers Mapper = sqlSession.getMapper(UsuarioMappers.class);
            Mapper.updateToken(user);
            sqlSession.commit();
            return token;
        } finally {
            sqlSession.close();
        }
    }
}
