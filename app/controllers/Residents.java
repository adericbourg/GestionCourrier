package controllers;

import static core.util.Collections.first;
import models.residence.Residence;
import models.residence.Resident;

import org.codehaus.jackson.JsonNode;
import org.joda.time.LocalDate;

import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Resident controller. Handles all I/O for residents.
 * 
 * @author adericbourg
 */
public class Residents extends Controller {

    public static Result allResidents() {
        return ok(Json.toJson(Resident.findAll()));
    }

    /**
     * Fetches resident detail for supplied resident id.
     * 
     * @param residentId Resident id.
     * @return Resident detail.
     */
    public static Result resident(Long residentId) {
        return ok(Json.toJson(Resident.byId(residentId)));
    }

    /**
     * Create new resident (POST). Json body expected.
     * 
     * @return 200 when success.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        JsonNode json = request().body().asJson();
        Resident resident = Json.fromJson(json, Resident.class);
        resident.save();
        return ok();
    }

    /**
     * Update resident (POST) for specified id. Json body expected.
     * 
     * @param residentId Resident id.
     * @return 200 when success.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateResident(long residentId) {
        JsonNode json = request().body().asJson();
        Resident updatedResident = Json.fromJson(json, Resident.class);

        Resident baseResident = Resident.byId(residentId);
        baseResident.gender = updatedResident.gender;
        baseResident.firstName = updatedResident.firstName;
        baseResident.lastName = updatedResident.lastName;
        baseResident.maidenName = updatedResident.maidenName;
        baseResident.birthDate = updatedResident.birthDate;
        baseResident.followedBy = updatedResident.followedBy;
        baseResident.followingDepartment = updatedResident.followingDepartment;
        baseResident.save();

        return ok();
    }

    /**
     * Add new residence (POST) for specified resident. Json body expected.
     * 
     * @param residentId Resident id.
     * @return 200 when success.
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result addResidence(long residentId) {
        JsonNode json = request().body().asJson();

        Residence residence = Json.fromJson(json, Residence.class);
        saveResidence(residentId, residence);
        return ok();
    }

    /**
     * Renew residence for specified resident.
     * <ul>
     * <li>If the latest residence already expired, starts new residence on current day.</li>
     * <li>If the latest residence ends in the future, starts the new residence the following day the previous one ends.</li>
     * </ul>
     * 
     * @param residentId Resident id.
     * @return 200 when success, 400 when no residente exists for specified user.
     */
    public static Result renewResidence(long residentId) {
        Residence latestResidence = first(Resident.byId(residentId).residences);
        if (latestResidence == null) {
            return badRequest();
        }

        Residence renewedResidence = latestResidence.copy();

        if (latestResidence.endDate.isAfter(LocalDate.now())) {
            renewedResidence.startDate = latestResidence.endDate.plusDays(1);
        } else {
            renewedResidence.startDate = LocalDate.now();
        }

        saveResidence(residentId, renewedResidence);
        return ok();
    }

    private static void saveResidence(long residentId, Residence residence) {
        // Auto-fill the end date.
        residence.endDate = residence.startDate.plusYears(1).minusDays(1);

        Resident resident = Resident.byId(residentId);
        resident.residences.add(residence);
        resident.save();
    }

    /**
     * Search resident by full text criteria.
     * 
     * @return Residents matching search criteria.
     */
    public static Result search(String query) {
        return ok(Json.toJson(Resident.find(query)));
    }
}
