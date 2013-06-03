package controllers;

import java.util.ArrayList;
import java.util.List;

import models.residence.Department;
import models.residence.Residence;
import models.residence.ResidenceType;
import models.residence.Resident;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
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

    public static Result addResidence(long residentId) {
        JsonNode json = request().body().asJson();
        Residence residence = Json.fromJson(json, Residence.class);

        Resident resident = Resident.byId(residentId);
        resident.residences.add(residence);
        resident.save();

        return ok();
    }

    public static Result renewResidence(long residentId) {
        return play.mvc.Results.TODO;
    }
}
