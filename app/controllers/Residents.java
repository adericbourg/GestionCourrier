package controllers;

import models.residence.Resident;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author adericbourg
 */
public class Residents extends Controller {

    public static Result allResidents() {
        return ok(Json.toJson(Resident.findAll()));
    }

    public static Result create() {
        JsonNode json = request().body().asJson();
        Resident resident = Json.fromJson(json, Resident.class);
        resident.save();
        return ok();
    }
}
