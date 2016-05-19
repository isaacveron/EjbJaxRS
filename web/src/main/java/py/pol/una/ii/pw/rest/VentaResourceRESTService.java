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

import py.pol.una.ii.pw.data.ClientRepository;
import py.pol.una.ii.pw.data.ProductRepository;
import py.pol.una.ii.pw.data.VentaRepository;
import py.pol.una.ii.pw.model.Client;
import py.pol.una.ii.pw.model.Venta;
import py.pol.una.ii.pw.model.VentaDetalle;
import py.pol.una.ii.pw.service.VentaRegistration;
import py.pol.una.ii.pw.service.VentaRemove;
import py.pol.una.ii.pw.util.InsuficientStockException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
@Path("/ventas")
@RequestScoped
public class VentaResourceRESTService {
    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private VentaRemove ventaRemove;

    @Inject
    private VentaRepository repository;

    @Inject
    VentaRegistration registration;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ClientRepository clientRepository;

    private Venta newVenta;
    private Client newClient;
    private VentaDetalle newVentaDetalle;
    private  VentaDetalle newVentaDetalle2;


    @GET
    @Path("/{position:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Venta> listAllVenta(@PathParam("position") int position) {
        // si position es 5 comenzara desde la posicion 5 a traer los elementos
        return repository.findAllOrderedByDate(position);
    }

    @GET
    @Path("/ordenBy/{by_attribute}/{mode}/{position:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Venta> listAllBy(@PathParam("position") int position,@PathParam("mode") String mode, @PathParam("by_attribute") String attribute) {
        return repository.findAllOrderedBy(position,mode,attribute);
    }


    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createVenta() throws InsuficientStockException {
        Response.ResponseBuilder builder = null;

        Date fecha = new Date();
        this.newVenta = new Venta();
        this.newClient = new Client();
        this.newVentaDetalle = new VentaDetalle();
        this.newVentaDetalle2 = new VentaDetalle();
        List<VentaDetalle> ventaDetalleList = new ArrayList<VentaDetalle>();

        newVenta.setClient(clientRepository.findById((long) 1));
        newVenta.setFecha(fecha);

        System.out.println(newVenta.getClient().getName());
        System.out.println(newVenta.getFecha());

        newVentaDetalle.setCantidad(10);
        newVentaDetalle.setProduct(productRepository.findById((long) 1));
        ventaDetalleList.add(newVentaDetalle);

        newVentaDetalle2.setCantidad(10);
        newVentaDetalle2.setProduct(productRepository.findById((long)2));
        ventaDetalleList.add(newVentaDetalle2);

        registration.register(newVenta, ventaDetalleList);


        /*try {
            registration.register(venta, );
            builder = Response.ok();
        } catch (Exception e) {
            // Handle generic exceptions
            log.info(">>>>>>>>>>>>>   "+e.getCause());

            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", "No existen todos los elementos para la venta");
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();*/
    }
}
