package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;
import introsde.rest.ehealth.model.MeasureDefinition;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.OneToOne;
import javax.persistence.Query;

/**
 * The persistent class for the "LifeStatus" database table.
 * 
 */
@Entity
@Table(name = "HealthProfile")
@NamedQuery(name = "HealthProfile.findAll", query = "SELECT h FROM HealthProfile h")
@XmlRootElement(name="Measure")
public class HealthProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_healthprofile")
	@TableGenerator(name="sqlite_healthprofile", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="HealthProfile")
	@Column(name = "idMeasure")
	private int idMeasure;
	@Column(name = "idMeasureDef", insertable = false, updatable = false)
	private int idMeasureDef;
	@Column(name = "idPerson", insertable = false, updatable = false)
	private int idPerson;
	@Column(name = "value")
	private String value;
	
	@OneToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef", insertable = true, updatable = true)
	private MeasureDefinition measureDefinition;
	
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;

	public HealthProfile() {
	}

	public int getIdMeasure() {
		return this.idMeasure;
	}

	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public MeasureDefinition getMeasureDefinition() {
		return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
		this.measureDefinition = param;
	}

	
	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	// Database operations
	public static HealthProfile getLifeStatusById(int healthprofileId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthProfile p = em.find(HealthProfile.class, healthprofileId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<HealthProfile> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<HealthProfile> list = em.createNamedQuery("HealthProfile.findAll", HealthProfile.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static HealthProfile saveLifeStatus(HealthProfile p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static HealthProfile updateLifeStatus(HealthProfile p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeLifeStatus(HealthProfile p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
	
	public static HealthProfile getHealthProfileByIDs(int idperson,int idmeasure){

		EntityManager em = LifeCoachDao.instance.createEntityManager();
		   try{ 
		Query q= em.createQuery("SELECT h FROM HealthProfile h WHERE h.idMeasureDef=:measure AND h.idPerson=:idperson", HealthProfile.class);
		  q.setParameter("measure", idmeasure);
		  q.setParameter("idperson", idperson);
		  HealthProfile health= (HealthProfile) q.getSingleResult();
		   LifeCoachDao.instance.closeConnections(em);
		   return health;
		   }catch(Exception e){System.out.println("The database doesn't contain a HealthProfile for the person choosen with the parameters required, please cheack the parammeters");
		   
		   LifeCoachDao.instance.closeConnections(em);
		    return null;}
	}
	
	
	
	
	 
		
	
	
	
	
	
	
	
	
	
	
	
	
}
