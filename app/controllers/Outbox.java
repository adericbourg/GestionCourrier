package controllers;

import models.inbox.Mail;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author alban
 */
public class Outbox extends Controller {

    /**
     * Returns all mails.
     * 
     * @return All mails.
     */
    public static Result mails() {
        return ok(Json.toJson(Mail.findFromInbox()));
    }
}
