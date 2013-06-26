package controllers;

import models.person.Person;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Person controller. Handles all I/O for persons.
 * 
 * @author adericbourg
 */
public class Persons extends Controller {

    public static Result allPersons() {
        return ok(Json.toJson(Person.findAll()));
    }

    /**
     * Fetches person detail for supplied person id.
     * 
     * @param personId Person id.
     * @return Person detail.
     */
    public static Result person(Long personId) {
        return ok(Json.toJson(Person.byId(personId)));
    }

    /**
     * Create new person (POST). Json body expected.
     * 
     * @return 200 when success.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        JsonNode json = request().body().asJson();
        Person person = Json.fromJson(json, Person.class);
        person.save();
        return ok();
    }

    /**
     * Update person (POST) for specified id. Json body expected.
     * 
     * @param personId Person id.
     * @return 200 when success.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(long personId) {
        JsonNode json = request().body().asJson();
        Person updatedPerson = Json.fromJson(json, Person.class);

        Person basePerson = Person.byId(personId);
        basePerson.gender = updatedPerson.gender;
        basePerson.firstName = updatedPerson.firstName;
        basePerson.lastName = updatedPerson.lastName;
        basePerson.maidenName = updatedPerson.maidenName;
        basePerson.birthDate = updatedPerson.birthDate;
        basePerson.orientation = updatedPerson.orientation;
        basePerson.followedBy = updatedPerson.followedBy;
        basePerson.followingDepartment = updatedPerson.followingDepartment;
        basePerson.forwardAddress = updatedPerson.forwardAddress;
        basePerson.forwardAddressActive = updatedPerson.forwardAddressActive;
        basePerson.save();

        return ok();
    }

    /**
     * Stop following person.
     * 
     * @param personId Person id.
     * @return Ok.
     */
    public static Result follow(long personId) {
        return setFollowing(personId, true);
    }

    /**
     * Start following person.
     * 
     * @param personId Person id.
     * @return Ok.
     */
    public static Result unfollow(long personId) {
        return setFollowing(personId, false);
    }

    private static Result setFollowing(long personId, boolean following) {
        Person person = Person.byId(personId);
        person.isFollowed = following;
        person.save();

        return ok();
    }

    /**
     * Search person by full text criteria.
     * 
     * @return Persons matching search criteria.
     */
    public static Result search(String query) {
        return ok(Json.toJson(Person.find(query)));
    }
}
