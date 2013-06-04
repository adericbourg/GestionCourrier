package controllers;

import static core.util.Collections.first;

import java.util.ArrayList;
import java.util.List;

import models.residence.*;

import org.codehaus.jackson.JsonNode;
import org.joda.time.LocalDate;

import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import controllers.model.Item;

/**
 * @author adericbourg
 */
public class Residents extends Controller {

    public static Result allResidents() {
        return ok(Json.toJson(Resident.findAll()));
    }

    public static Result resident(Long id) {
        return ok(Json.toJson(Resident.byId(id)));
    }

    public static Result residences(Long residentId) {
        return ok(Json.toJson(Resident.byId(residentId).residences));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        JsonNode json = request().body().asJson();
        Resident resident = Json.fromJson(json, Resident.class);
        resident.save();
        return ok();
    }

    public static Result departments() {
        List<Item<Department, String>> deparments = new ArrayList<Item<Department, String>>();
        for (Department department : Department.values()) {
            deparments.add(new Item<Department, String>(department, department.getMeaning()));
        }
        return ok(Json.toJson(deparments));
    }

    public static Result residenceTypes() {
        List<Item<ResidenceType, String>> deparments = new ArrayList<Item<ResidenceType, String>>();
        for (ResidenceType residenteType : ResidenceType.values()) {
            deparments.add(new Item<ResidenceType, String>(residenteType, residenteType.getMeaning()));
        }
        return ok(Json.toJson(deparments));
    }

    public static Result sexes() {
        List<Item<Sex, String>> sexes = new ArrayList<Item<Sex, String>>();
        for (Sex sex : Sex.values()) {
            sexes.add(new Item<Sex, String>(sex, sex.getMeaning()));
        }
        return ok(Json.toJson(sexes));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result addResidence(long residentId) {
        JsonNode json = request().body().asJson();

        Residence residence = Json.fromJson(json, Residence.class);
        saveResidence(residentId, residence);
        return ok();
    }

    public static Result renewResidence(long residentId) {
        Residence latestResidence = first(Resident.byId(residentId).residences);

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

}
