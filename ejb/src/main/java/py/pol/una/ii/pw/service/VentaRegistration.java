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
import py.pol.una.ii.pw.data.ComprasRepository;
import py.pol.una.ii.pw.data.ProductRepository;
import py.pol.una.ii.pw.data.VentaRepository;
import py.pol.una.ii.pw.mapper.VentaMappers;
import py.pol.una.ii.pw.model.Product;
import py.pol.una.ii.pw.model.Venta;
import py.pol.una.ii.pw.model.VentaDetalle;
import py.pol.una.ii.pw.util.InsuficientStockException;
import py.pol.una.ii.pw.util.MyBatisUtil;

import javax.ejb.Stateless;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class VentaRegistration {


    @Inject
    private EntityManager em;

    @Inject
    private VentaDetalleRegistration ventaDetalleRegistration;

    @Inject
    private ProductRegistration productRegistration;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private Validator validator;

    @Inject
    private VentaRepository ventaRepository;

    @Resource
    private SessionContext context;

    @TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
    public void register(Venta venta, List<VentaDetalle> detalles) throws InsuficientStockException,ValidationException{

        try {

            validateVenta(venta);
            SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            try {
                VentaMappers Mapper = sqlSession.getMapper(VentaMappers.class);
                Mapper.register(venta);
                sqlSession.commit();
            } finally {
                sqlSession.close();
            }

            for (VentaDetalle detalle : detalles) {

                Product p = productRepository.findById(detalle.getProduct().getId());
                if (p.getCantidad() - detalle.getCantidad() >= 0) {
                    p.setCantidad(p.getCantidad() - detalle.getCantidad());
                    this.productRegistration.merge(p);
                } else {
                    context.setRollbackOnly();
                    throw new InsuficientStockException("Producto con id " + p.getId() + " con stock insuficiente");
                }

                detalle.setVenta(venta);

                ventaDetalleRegistration.register(detalle);
            }

        }catch (ConstraintViolationException e){
            this.context.setRollbackOnly();
            throw e;
        } catch (ValidationException e) {
            this.context.setRollbackOnly();
            throw e;
        }
    }

    private void validateVenta(Venta venta) throws  ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Venta>> violations = validator.validate(venta);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

}
