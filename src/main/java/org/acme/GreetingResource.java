package org.acme;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
public class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserName> getAllUsers() {
        return UserName.listAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id) {
        UserName user = UserName.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response createUser(UserName userName) {
        userName.persist();
        return Response.ok("User created with name: " + userName.name).build();
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response updateUser(@PathParam("id") Long id, UserName updatedUser) {
        UserName user = UserName.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        user.name = updatedUser.name;
        user.persist();
        return Response.ok("User updated with name: " + user.name).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {
        UserName user = UserName.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        user.delete();
        return Response.ok("User deleted with name: " + user.name).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/personalized/{name}")
    public String helloPersonalized(@PathParam("name") String name) {
        return "Hello " + name;
    }

    // Updated method to store name in the database and greet
    @POST
    @Path("/personalized/{name}")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public Response createAndGreetUser(@PathParam("name") String name) {
        // Create a new UserName instance and persist it
        UserName userName = new UserName(name);
        userName.persist();

        // Return a greeting message
        String message = "Hello " + name + "! Your name has been saved to the database.";
        return Response.ok(message).build();
    }

    public static class Person {
        private String first;
        private String last;

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }
    }
}
