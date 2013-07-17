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
import business.residence.ResidenceAlreadyDefinedException;
import core.controller.CatchBusinessException;

/**
 * Residences controller.
 * 
 * @author adericbourg
 */
@CatchBusinessException
public class Residences extends Controller {

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

        // TODO Move this part of code into "business" package.
        for (Residence existingResidence : person.residences) {
            if (existingResidence.startDate.isBefore(residence.startDate) && existingResidence.endDate.isAfter(residence.startDate)
                    || existingResidence.startDate.isBefore(residence.endDate) && existingResidence.endDate.isAfter(residence.endDate)
                    || existingResidence.endDate.equals(residence.startDate)) {
                throw new ResidenceAlreadyDefinedException();
            }
        }

        person.residences.add(residence);
        person.save();
    }

    /**
     * List all current residents.
     * 
     * @return List of all current residents.
     */
    public static Result allResidents() {
        return ok(Json.toJson(Person.findAllResidents()));
    }

    /**
     * List all residenced persons whose residence is about to expire.
     * 
     * @return All residenced persons whose residence is about to expire.
     */
    public static Result endOfResidenceResidents() {
        return ok(Json.toJson(Person.findEndOfResidencePersons()));
    }

    /**
     * List all persons that are no longer residenced (or that never were).
     * 
     * @return All persons that are no longer residenced (or that never were).
     */
    public static Result noResidencePersons() {
        return ok(Json.toJson(Person.findNoResidencePersons()));
    }
}
