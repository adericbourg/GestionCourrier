package controllers;

import static core.util.Collections.first;
import models.person.Person;
import models.residence.Residence;

import org.codehaus.jackson.JsonNode;
import org.joda.time.LocalDate;

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
     * Add new residence (POST) for specified person. Json body expected.
     * 
     * @param personId Person id.
     * @return 200 when success.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result addResidence(long personId) {
        JsonNode json = request().body().asJson();

        Residence residence = Json.fromJson(json, Residence.class);
        saveResidence(personId, residence);
        return ok();
    }

    /**
     * Renew residence for specified person.
     * <ul>
     * <li>If the latest residence already expired, starts new residence on current day.</li>
     * <li>If the latest residence ends in the future, starts the new residence the following day the previous one ends.</li>
     * </ul>
     * 
     * @param personId Person id.
     * @return 200 when success, 400 when no person exists for specified id.
     */
    public static Result renewResidence(long personId) {
        Residence latestResidence = first(Person.byId(personId).residences);
        if (latestResidence == null) {
            return badRequest();
        }

        Residence renewedResidence = latestResidence.copy();

        if (latestResidence.endDate.isAfter(LocalDate.now())) {
            renewedResidence.startDate = latestResidence.endDate.plusDays(1);
        } else {
            renewedResidence.startDate = LocalDate.now();
        }

        saveResidence(personId, renewedResidence);
        return ok();
    }

    private static void saveResidence(long personId, Residence residence) {
        // Auto-fill the end date.
        residence.endDate = residence.startDate.plusYears(1).minusDays(1);

        Person person = Person.byId(personId);
        person.residences.add(residence);
        person.save();
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
