/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucbcba.simplescheduling.resource;

import bo.edu.ucbcba.simplescheduling.model.MyClass;
import bo.edu.ucbcba.simplescheduling.response.ErrorResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.status;

/**
 * REST Web Service
 *
 * @author DELL
 */
@Path("v1/classes")
public class ClassesResource {

    @Context
    private UriInfo context;
    private final Gson gson = new Gson();

    /**
     * Creates a new instance of ClassesResource
     */
    public ClassesResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClasses() {
        List<MyClass> myClasses = new ArrayList<>(GenericResource.getClassMap().values());
        return Response.ok(gson.toJson(myClasses),MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("{classCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClass(@PathParam("classCode") String classCode) {
       MyClass  myClass = GenericResource.getClass(classCode);
       
        if (myClass != null) {
            return Response.ok(gson.toJson(myClass), 
                    MediaType.APPLICATION_JSON).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response MyClasses(String jsonString) {
        MyClass myClass = gson.fromJson(jsonString, MyClass.class);
        // TODO validate params
        String classCode = myClass.getCode();
        if (GenericResource.getClass(classCode) != null) { // ya existe
            ErrorResponse errorResponse = new ErrorResponse(UUID.randomUUID(), 
                    Response.Status.BAD_REQUEST, "ERR_002", "Creation failed.", 
                    "Class was not created", 
                    Arrays.asList("La clase ya existe"));
            return Response.ok(gson.toJson(errorResponse), MediaType.APPLICATION_JSON)
                    .status(Response.Status.BAD_REQUEST).build();
        }
        
        String title = myClass.getTitle();
        String description = myClass.getDescription();
        List<Integer> studentIds = myClass.getStudentIds();
        GenericResource.putClass(myClass);
        return Response.ok(gson.toJson(myClass), 
                MediaType.APPLICATION_JSON)
                .status(Response.Status.CREATED)
                .build();
    }
    
    @DELETE
    @Path("{classCode}")
    public Response deleteClass(@PathParam(value = "classCode")
    String classCode) {
        // search student
        MyClass myClass = GenericResource.getClass(classCode);
        if (myClass != null) {
            GenericResource.removeClass(classCode);
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    
    @PUT
    @Path("{classCode}")
    public Response editStudent(String jsonString) {
        MyClass myClass = gson.fromJson(jsonString, MyClass.class);
        
        String classCode = myClass.getCode();
        String  title = myClass.getDescription();
        String  description = myClass.getDescription();
        List<Integer> studentIds = myClass.getStudentIds();
        
        MyClass currentClass = GenericResource.getClass(classCode);
        if (currentClass != null) {
            if (!title.isEmpty()) {
                currentClass.setTitle(title);
            }
            if (!description.isEmpty()) {
                currentClass.setDescription(description);
            }
            currentClass.setStudentIds(studentIds);
            return Response.ok(gson.toJson(currentClass), 
                    MediaType.APPLICATION_JSON)
                    .status(Response.Status.CREATED)
                    .build();
        }   
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }        
    }
}
