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
}
