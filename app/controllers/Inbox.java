package controllers;

import models.inbox.Mail;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author alban
 */
public class Inbox extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result registerMail() {
        JsonNode json = request().body().asJson();

        Mail mail = Json.fromJson(json, Mail.class);
        mail.save();

        return ok();
    }
}
