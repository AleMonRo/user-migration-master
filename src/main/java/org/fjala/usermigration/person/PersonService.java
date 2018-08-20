/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fjala.usermigration.person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//imports for posting
import static java.util.Arrays.asList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

/**
 *
 * @author ubuntu
 */
@Component
public class PersonService implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) {
        List<Object[]> peopleList = entityManager.createNativeQuery("SELECT name, lastName, "
                + "secondLastName, ci, email, image, birthdate FROM PERSON").getResultList();

        List<Person> people = new ArrayList<>();
        peopleList.forEach((person) -> {
            people.add(new Person(
                    (String) person[0],
                    (String) person[1],
                    (String) person[2],
                    (String) person[3],
                    (String) person[4],
                    (byte[]) person[5],
                    (Date) person[6]));
        });

        people.forEach(person -> System.out.println(person.getName() + " "
                + person.getUsername()));

        Keycloak kc = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8180/auth")
                .realm("Fjala")
                .username("softure06")
                .password("softure06")
                .clientId("snowtiger")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        //credential.setValue("test123");
        credential.setTemporary(false);

        Map<String, List<String>> attributes = new LinkedHashMap<>();

        UserRepresentation user = new UserRepresentation();
        for (Person person : people) {
            user.setUsername(person.getUsername());
            user.setFirstName(person.getName());
            user.setLastName(person.getLastName());
            user.setEmail(person.getEmail());

            //New password is username like Fisrtname.Lastname
            credential.setValue(person.getUsername());
            user.setCredentials(asList(credential));
            user.setEnabled(true);
            //Need to get roles from DB and add them to the realm
            user.setRealmRoles(asList("admin"));

            attributes.put("secondLastName", asList(person.getSecondLastName()));
            attributes.put("ci", asList(person.getCi()));
            attributes.put("image", asList(person.getImage().toString()));
            attributes.put("birthdate", asList(person.getBirthdate().toString()));
            user.setAttributes(attributes);

            Response result = kc.realm("rest-example").users().create(user);
            //TODO: delete logger
            if (result.getStatus() != 201) {
                System.err.println("Couldn't create user for " +  person.getUsername());
                System.exit(0);
            }
        }

//        Keycloak keycloak = Keycloak.getInstance(
//                "http://localhost:8180/auth",
//                "Fjala",
//                "softure06",
//                "softure06",
//                "snowtiger");
//        RealmRepresentation realm = keycloak.realm("Fjala").toRepresentation();
//        people.forEach((person) -> {
//            RestTemplate restTemplate = new RestTemplate();
//            String fooResourceUrl = "http://localhost:8180/auth/realms/Fjala/users";
//            HttpEntity<Person> request = new HttpEntity<>(person);
//            restTemplate.postForObject(fooResourceUrl, request, Person.class);
//        });
    }
}
