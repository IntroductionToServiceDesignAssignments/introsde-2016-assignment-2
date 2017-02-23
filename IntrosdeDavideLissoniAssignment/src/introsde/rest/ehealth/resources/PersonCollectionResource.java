package introsde.rest.ehealth.resources;
import introsde.rest.ehealth.model.HealthMeasureHistory;
import introsde.rest.ehealth.model.HealthProfile;
import introsde.rest.ehealth.model.MeasureDefinition;
import introsde.rest.ehealth.model.Person;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


@Path("/person")
public class PersonCollectionResource {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    

  
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<Person> getPersonsBrowser() {
        System.out.println("Getting list of people...");
        List<Person> people = Person.getAll();
        return people;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("count")
    public String getCount() {
        System.out.println("Getting count...");
        List<Person> people = Person.getAll();
        int count = people.size();
        return String.valueOf(count);
    }

    @POST
    @Produces({MediaType.APPLICATION_XML,  MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,  MediaType.APPLICATION_JSON})
    public Person newPerson(Person person) throws IOException {
        System.out.println("Creating new person...");            
        return Person.savePerson(person);
    }
   
  
    @Path("{personId}")
    public PersonResource getPerson(@PathParam("personId") int id) {
        return new PersonResource(uriInfo, request, id);
    }
    
    @GET
    @Path("{personId}/{measureType}")
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<HealthMeasureHistory> getMeasureHistory(@PathParam("personId") int personid,@PathParam("measureType") String measureType) {
    int idMeasure=MeasureDefinition.getIDByMeasureName(measureType);   
    List<HealthMeasureHistory> History = HealthMeasureHistory.getHistoryByIDPersonIdMeasure(personid, idMeasure); 
	return History;
    }
    
    
    @GET
    @Path("{personId}/{measureType}/{mid}")
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public HealthMeasureHistory getHealthProfileMid(@PathParam("personId") int personid,@PathParam("measureType") String measureType,@PathParam("mid") int mid) {
    int idMeasure=MeasureDefinition.getIDByMeasureName(measureType);
    HealthMeasureHistory Health = HealthMeasureHistory.getHealthMeasureHistoryByIDPersonIdMeasureMid(personid, idMeasure,mid); 
	return Health;
    }
    
    
    @POST
    @Path("{personId}/{measureType}")
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    @Consumes({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public HealthProfile updateHealth(@PathParam("personId") int personid,@PathParam("measureType") String measureType,HealthMeasureHistory hist) throws IOException {

    	int idMeasure=MeasureDefinition.getIDByMeasureName(measureType);
    	HealthProfile health= HealthProfile.getHealthProfileByIDs(personid,idMeasure);
    	Person p=Person.getPersonById(personid);
    	MeasureDefinition m=MeasureDefinition.getMeasureDefinitionById(idMeasure);
    	HealthProfile healthProfile=new HealthProfile();
    	if (health==null){
    		healthProfile.setMeasureDefinition(m);
    		healthProfile.setValue(hist.getValue());
    		healthProfile.setPerson(p);
    		HealthProfile.saveLifeStatus(healthProfile);

    	}else{
    	
    	String Oldvalue= health.getValue();
    	HealthMeasureHistory history = new HealthMeasureHistory();
    	history.setPerson(p);
    	history.setMeasureDefinition(m);
    	history.setValue(Oldvalue);
    	history.setTimestamp(hist.getTimestamp());
    	HealthMeasureHistory.saveHealthMeasureHistory(history);
    	HealthProfile.removeLifeStatus(health);
    	healthProfile.setValue(hist.getValue());
    	healthProfile.setPerson(p);
    	healthProfile.setMeasureDefinition(m);
    	HealthProfile.saveLifeStatus(healthProfile);
    	}
    	
    	
    	
        return healthProfile;
    } 
    
    
    
    
}