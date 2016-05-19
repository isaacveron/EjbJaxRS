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
import py.pol.una.ii.pw.data.ProductRepository;
import py.pol.una.ii.pw.mapper.ProducMappers;
import py.pol.una.ii.pw.model.Product;
import py.pol.una.ii.pw.util.MyBatisUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Validator;


// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class ProductRegistration {

    @Inject
    private EntityManager em;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductRepository repository;

    @Inject
    private Validator validator;


    public void register(Product p){

        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try
        {
            ProducMappers Mapper = sqlSession.getMapper(ProducMappers.class);
            Mapper.register(p);
            sqlSession.commit();
        } finally
        {
            sqlSession.close();
        }
    }

    public void merge(Product p){
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try
        {
            ProducMappers Mapper = sqlSession.getMapper(ProducMappers.class);
            Mapper.merge(p);
            sqlSession.commit();
        } finally
        {
            sqlSession.close();
        }
    }



}
