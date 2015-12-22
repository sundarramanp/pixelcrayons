/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.restws;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("update_customer_detail")
public class EditCustomerDetail {
    @Context
    private UriInfo context;

    /** Creates a new instance of EditCustomerDetail */
    public EditCustomerDetail() {
    }

    /**
     * Retrieves representation of an instance of com.restws.EditCustomerDetail
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        return "Edit Customer Detail Web Service";
    }

    /**
     * PUT method for updating or creating an instance of EditCustomerDetail
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
