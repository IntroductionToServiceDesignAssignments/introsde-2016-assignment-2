package introsde.rest.ehealth.resources;

import java.util.List;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElementWrapper;

import introsde.rest.ehealth.model.MeasureDefinition;

@Path("/measureTypes")
public class MeasureResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
		
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    @XmlElementWrapper(name = "MeasureTypes")
    public List<MeasureDefinition> getMeasuresBrowser() {
        System.out.println("Getting list of measures...");
        List<MeasureDefinition> measures = MeasureDefinition.getAll();
        return measures;
    }
}