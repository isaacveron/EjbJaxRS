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

import py.pol.una.ii.pw.data.ComprasRepository;
import py.pol.una.ii.pw.model.Compra;
import py.pol.una.ii.pw.model.CompraDetalle;
import py.pol.una.ii.pw.model.Product;

import py.pol.una.ii.pw.util.MyBatisUtil;
import py.pol.una.ii.pw.mapper.CompraMappers;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class CompraRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private ProductRegistration productRegistration;

    @Inject
    private CompraDetalleRegistration compraDetalleRegistration;

    @Inject
    private ComprasRepository comprasRepository;
    @Inject
    private Validator validator;

    @Resource
    private SessionContext context;



    //@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
    public void register(Compra c, List<CompraDetalle> detalles) throws ValidationException{

        validateCompra(c);

        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            CompraMappers Mapper = sqlSession.getMapper(CompraMappers.class);
            Mapper.register(c);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }

        for (CompraDetalle detalle : detalles) {

            Product p = detalle.getProduct();

            p.setCantidad(p.getCantidad() + detalle.getCantidad());

            this.productRegistration.merge(p);

            //detalle.setProduct( p );
            Compra compra = comprasRepository.findById(c.getId());
            detalle.setCompra(compra);
            System.out.println("***********compraid:" + c.getId());
            this.compraDetalleRegistration.register(detalle);
        }


    }

    private void validateCompra(Compra compra) throws ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Compra>> violations = validator.validate(compra);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }
}


