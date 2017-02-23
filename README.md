# introsde-2016-assignment-2

Introsde-2016-assignment-2 report

Davide Lissoni Mat.179878

22/11/2016

The project client refers to Daniele Dellagiacoma server.

Daniele Dellagiacoma server url:https://dellagiacomaintrosde2.herokuapp.com  application path:/sdelab

Link to Daniele Dellagiacoma repository:https://github.com/DanieleDellagiacoma/introsde-2016-assignment-2

Personal server url: https://lissonidavideapp.herokuapp.com  application path:/assignment2


##Client structure (ClientIntroSdeAss2):
The Client is composed by a singolar package "requests".

The package contains the following classes:

1. R1.java is the main class. The main() make the call to all the other classes. The request #1 to the server is implemented in this class  
2. R2.java: execute the request #2 to the server
3. R3.java: execute the request #3 to the server
4. R4.java: execute the request #4 to the server
5. R5.java: execute the request #5 to the server
6. R6.java: execute the request #6 to the server
7. R7.java: execute the request #7 to the server
8. R8.java: execute the request #8 to the server
9. R9.java: execute the request #9 to the server

The request are executed in order to satisfy the assignment requirements.

The program logs the result of each request executed into two files: client-server-json.log and client-server-xml.log.

**Additional notes:**

Extra requests #10, Request #11 and Request #12 has not been implemented since both the servers doesn't provide these requests.

The xml and json body of the requests and the parsing of the responses have been implemented according to Daniele Dellagiacoma's server.


##Server structure (IntrosdeDavideLissoniAssignment):

The server is structured in 4 different packages:

**introsde.rest.health** containing:

1. **App.java** containing the main used to run the application in local;
2. **MyApplicationConfig.java** used to define the Application path.

**introsde.rest.health.dao** containing:

1. **LifeCoachDao.java** used to connect the project model to the database.

**introsde.rest.health.model** containing the entity classes:

1. **HealthMisureHistory.java** ;
2. **HealthProfile.java**;
3. **MeasureDefinition.java**;
4. **Person.java**.

**introsde.rest.health.resources** containing the resources that are exposed throught the RESTful APi:

1. **MeasureResource.java** used for /measureTypes requests;
2. **PersonCorrelationResource** used for /person/* requests;
3. **PersonResource** used as extension of PersonCorrelationResource for some /person/id requests.


The server keeps the xml and json structure used for the lab07 because this implementation appeared to be more complete.
For this reason the #4 and #8 bodys request must follow the xml and json project structure as showed in the examples exponed below:

**example xml of request #4 that insert a person containing 2 different health profiles:**
```xml
<person>
<birthdate>1945-01-012</birthdate>
<firstname>Chuck</firstname><lastname>Norris</lastname>
<HealthProfile>
   <measure>
	<measureDefinition>
          <idMeasureDef>1</idMeasureDef>
	  <measureName>weight</measureName>
          <measureType>double</measureType>
	 </measureDefinition>
   <value>78.9</value>
   </measure>
   <measure>
    <measureDefinition>
      <idMeasureDef>2</idMeasureDef>
      <measureName>height</measureName>
      <measureType>double</measureType>
     </measureDefinition>
    <value>172</value>
    </measure>
  </HealthProfile>
</person>
```
**example xml of request #4 that insert a person containing 2 different health profiles:**
```json
 {"lastname": "Norris",
  "firstname": "Chuck",
  "birthdate": "1945-01-01"
	"measure": [{"value":"78.9",
  "measureDefinition": {
				"idMeasureDef": 1,
        "measureName": "weight",
        "measureType": "double" }},
	   {"value": "172",
     "measureDefinition": {
        "idMeasureDef": 2,
				"measureName": "height",
        "measureType": "double"}}]}
```
**example xml of request #8 that update a health profile measure and insert a new history using the old health profile measure value that has been modified:**
```xml
<healthMeasureHistory>
  <timestamp>2011-12-09</timestamp>
  <value>72</value>
</healthMeasureHistory>
```
**example json of request #8 that update a health profile measure and insert a new history using the old health profile measure value that has been modified:**
```json
{"value": "72", "timestamp":2011-12-09 00:00:00"}
```
**Additional notes:**

Health Profile build dynamically.

Measure available:

1. 1 weight
2. 2 height
3. 3 steps
4. 4 bloodPressure
5. 5 heartRate
6. 6 bmi

Extra request #10,#11 and #12 has not been implemented in the server.

##Deployment:

In order to deploy the client clone the repository project in local:
```sh
$ git clone https://github.com/DavideLissoni/introsde-2016-assignment-2.git
```

Then go to the project folder just cloned and run the command ant execute.client

```sh
$ cd ClientIntroSdeAss2
$ ant execute.client
```

The server has been updated just in order to show its code.
In any case in order to create the war file make sure to be located on the repository directory and type:

```sh
$ cd IntrosdeDavideLissoniAssignment
$ ant create.war
```
 
