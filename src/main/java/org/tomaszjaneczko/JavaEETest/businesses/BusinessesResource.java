package org.tomaszjaneczko.JavaEETest.businesses;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Stateless
@Path("/businesses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BusinessesResource {

    @Inject
    private BusinessesRepository businessesRepository;

    @GET
    public List<Business> getBusinesses() {
        return businessesRepository.allBusinesses();
    }

    @GET
    @Path("{business_id}")
    public Business getBusiness(@PathParam("business_id") long businessId) {
        final Optional<Business> business = businessesRepository.findBusiness(businessId);
        if (business.isPresent()) {
            return business.get();
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @POST
    public Business createBusiness(Business business) {
        final Optional<Business> createdBusiness = businessesRepository.createBusiness(business);

        if (createdBusiness.isPresent()) {
            return createdBusiness.get();
        } else {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{business_id}")
    public void deleteBusiness(@PathParam("business_id") long id) {
        final boolean deleteSuccessful = businessesRepository.deleteBusiness(id);

        if (!deleteSuccessful) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
