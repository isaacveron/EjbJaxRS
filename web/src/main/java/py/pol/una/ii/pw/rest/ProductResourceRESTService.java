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

import py.pol.una.ii.pw.data.ProductRepository;
import py.pol.una.ii.pw.model.Product;
import py.pol.una.ii.pw.service.ProductRegistration;
import py.pol.una.ii.pw.service.ProductRemove;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Logger;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the members table.
 */
@Path("/products")
@RequestScoped
public class ProductResourceRESTService {
    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private ProductRepository repository;

    @Inject
    private ProductRemove productRemove;

    @Inject
    ProductRegistration registration;


    @GET
    @Path("/{position:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> listAllProduct(@PathParam("position") int position) {
        // si position es 5 comenzara desde la posicion 5 a traer los elementos
        return repository.findAllOrderedByName(position);
    }

    @GET
    @Path("/ordenBy/{by_attribute}/{mode}/{position:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> listAllBy(@PathParam("position") int position,@PathParam("mode") String mode, @PathParam("by_attribute") String attribute) {
        return repository.findAllOrderedBy(position,mode,attribute);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProducts (List <Product> products) {
        Response.ResponseBuilder builder = null;
        for (Product product : products) {
            try {
                validateProduct(product);
                registration.register(product);
                // Create an "ok" response
                builder = Response.ok();
            }
            catch(Exception e){
                // Handle generic exceptions
                Map<String, String> responseObj = new HashMap<String, String>();
                responseObj.put("error", e.getMessage());
                builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
            }
        }
        return builder.build();
    }

    /*
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(Product product) {
        Response.ResponseBuilder builder = null;
        try {
            registration.register(product);
            // Create an "ok" response
            builder = Response.ok();
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

    return builder.build();
    }*/


    @DELETE
    @Path("/{id}")
    public Response removeProduct(@PathParam("id")int id) {
        Response.ResponseBuilder builder = null;

        try {
            productRemove.remove((long)id);
            builder = Response.ok();
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    private void validateProduct(Product product) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        if (nameAlreadyExists(product.getNameProduct())) {
            throw new ValidationException("Unique product Violation");
        }

    }

    //Esta funcion verifica si existe un producto con el mismo nombre si es asi lo guarda en la tabla producto_duplicado
    public boolean nameAlreadyExists(String name) {
        boolean a = repository.findByName(name);
        return a;
    }
}


