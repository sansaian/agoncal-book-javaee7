package org.agoncal.book.javaee7.chapter22.ex12;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         APress Book - Beginning Java EE 7 with Glassfish 4
 *         http://www.apress.com/
 *         http://www.antoniogoncalves.org
 *         --
 */
@Path("/12/customer")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class CustomerRestService12 {

  // ======================================
  // =             Attributes             =
  // ======================================

  @Context
  private UriInfo uriInfo;

  @PersistenceContext(unitName = "chapter22PU")
  private EntityManager em;

  // ======================================
  // =           Public Methods           =
  // ======================================

  @GET
  public Response getListOfCustomers() {
    Customers12 customers = new Customers12();
    customers.add(new Customer12("John", "Smith", "jsmith@gmail.com", "1234565"));
    customers.add(new Customer12("John", "Smith", "jsmith@gmail.com", "1234565"));
    return Response.ok(customers).build();
  }

  @GET
  @Path("{customerId}")
  public Response getCustomer(@PathParam("customerId") String customerId) {

    if(customerId == null || customerId.trim().length() == 0) {
      return Response.serverError().entity("Customer Id cannot be blank").build();
    }

    Customer12 customer = em.find(Customer12.class, customerId);
    if(customer == null) {
      return Response.status(Response.Status.NOT_FOUND).entity("Customer not found for id: " + customerId).build();
    }
    return Response.ok(new Customer12("John", "Smith", "jsmith@gmail.com", "1234565"), MediaType.APPLICATION_JSON).build();
  }

  @POST
  public Response createCustomer(Customer12 customer) {
    URI bookUri = uriInfo.getAbsolutePathBuilder().path(customer.getId().toString()).build();
    return Response.created(bookUri).build();
  }

  @PUT
  @Path("{customerId}")
  public Response updateCustomer(@PathParam("customerId") String customerId, Customer12 customer) {
    return Response.ok().build();
  }

  @DELETE
  @Path("{customerId}")
  public Response deleteCustomer(@PathParam("customerId") String customerId) {
    return Response.noContent().build();
  }
}
