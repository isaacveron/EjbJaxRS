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

import py.pol.una.ii.pw.data.ProviderRepository;
import py.pol.una.ii.pw.model.Provider;
import py.pol.una.ii.pw.service.ProviderMassiveRegistration;
import py.pol.una.ii.pw.service.ProviderRegistration;
import py.pol.una.ii.pw.service.ProviderRemove;
import py.pol.una.ii.pw.util.CSVFileReadingException;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

//import javax.transaction.Transactional;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the members table.
 */
//@Transactional
@Path("/providers")
@RequestScoped
public class ProviderResourceRESTService {
    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private ProviderRepository repository;

    @Inject
    ProviderRegistration registration;

    @Inject
    ProviderRemove providerRemove;

    @Inject
    private ProviderMassiveRegistration massiveRegistration;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Provider findById(@PathParam("id")long id){
        return repository.findById(id);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Provider> listAll() {
        // si position es 5 comenzara desde la posicion 5 a traer los elementos
        return repository.findAllOrderedByName();
    }


    @DELETE
    @Path("/{id}")
    public Response removeProvider(@PathParam("id")int id) {
        Response.ResponseBuilder builder = null;

        try {
            providerRemove.remove((long)id);
            builder = Response.ok();
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }


    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProvider(Provider provider) {
        Response.ResponseBuilder builder = null;
        try {
            registration.register(provider);
            // Create an "ok" response
            builder = Response.ok();
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    /*
    @POST
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response uploadFile(@MultipartForm  MultipartFormDataInput input) {

        String fileName = "/home/cesar/Escritorio/proveedores.csv";

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");

        for (InputPart inputPart : inputParts) {

            try {

                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class,null);


                OutputStream out = new FileOutputStream( new File(fileName) );
                int read;
                byte[] bytes = new byte[1024];

                while ( (read = inputStream.read(bytes) ) != -1) {
                    out.write(bytes, 0, read);
                }

                out.close();

            } catch (IOException e) {
                return Response.serverError().entity("Error : " + e.getMessage()).build();
            }

            try {
                FileReader fr = new FileReader( fileName );
                massiveRegistration.massiveRegistration( fr );
                fr.close();
            }catch (CSVFileReadingException e){
                return Response.status(400).entity("Archivo mal construido : \n" + e.getMessage()).build();

            }catch(Exception e){
                return Response.serverError().entity("Error : " + e.getMessage()).build();
            }
        }

        return Response.status(200).build();


    }*/

}
