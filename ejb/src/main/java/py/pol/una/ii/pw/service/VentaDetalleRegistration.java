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
import py.pol.una.ii.pw.mapper.VentaMappers;
import py.pol.una.ii.pw.model.VentaDetalle;
import py.pol.una.ii.pw.util.MyBatisUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER )
public class VentaDetalleRegistration {


    @Inject
    private EntityManager em;

    @Inject
    private Validator validator;

    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void register(VentaDetalle vd) throws ValidationException{

        this.validateVentaDetalle( vd );
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try
        {
            VentaMappers Mapper = sqlSession.getMapper(VentaMappers.class);
            Mapper.registerDetalle(vd);
            sqlSession.commit();
        } finally
        {
            sqlSession.close();
        }
    }

    private void validateVentaDetalle(VentaDetalle detalle) throws  ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<VentaDetalle>> violations = validator.validate(detalle);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }
}
