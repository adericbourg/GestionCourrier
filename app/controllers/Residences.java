package controllers;

import models.person.Person;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Residences controller.
 * 
 * @author adericbourg
 */
public class Residences extends Controller {

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
