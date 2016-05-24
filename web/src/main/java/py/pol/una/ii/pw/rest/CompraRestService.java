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
package py.pol.una.ii.pw.rest;

import py.pol.una.ii.pw.data.ComprasRepository;
import py.pol.una.ii.pw.data.ProductRepository;
import py.pol.una.ii.pw.data.ProviderRepository;
import py.pol.una.ii.pw.model.Compra;
import py.pol.una.ii.pw.model.CompraDetalle;
import py.pol.una.ii.pw.model.Provider;
import py.pol.una.ii.pw.service.CompraRegistration;
import py.pol.una.ii.pw.util.ValidarCompra;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Logger;
import java.util.Date;
/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the members table.
 */
@Path("/compras")
@RequestScoped
@ValidarCompra
public class CompraRestService {
    @Inject
    private Logger log;

    @Inject
    private Validator validator;


    @Inject
    private ComprasRepository repository;

    @Inject
    CompraRegistration registration;

    @Inject
    ProviderRepository providerRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    Compra compra;

    @Inject
    CompraDetalle compraDetalle;


    @GET
    @Path("/ordenBy/{by_attribute}/{mode}/{position:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Compra> listAllBy(@HeaderParam("token") String token, @PathParam("position") int position,@PathParam("mode") String mode, @PathParam("by_attribute") String attribute) {
        return repository.findAllOrderedBy(position,mode,attribute);
    }


    @GET
    @Path("/{position:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Compra> listAllCompras(@HeaderParam("token") String token, @PathParam("position") int position) {
        // si position es 5 comenzara desde la posicion 5 a traer los elementos
        return repository.findAllOrderedByDate(position);
    }

    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createCompra(@HeaderParam("token") String token, List<CompraDetalle> detalles) {
        Response.ResponseBuilder builder = null;

        registration.register(token, detalles);
        /*try {
            registration.register(newCompra, compraDetalleList);
            builder = Response.ok();
        } catch (Throwable e) {
            // Handle generic exceptions
            log.info(">>>>>>>>>>>>>   "+e.getCause());
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", "No existen todos los elementos para la compra");
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);

        }

        return builder.build();*/
    }
}
