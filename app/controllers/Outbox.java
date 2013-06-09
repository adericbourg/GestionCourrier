package controllers;

import models.inbox.Mail;

import org.joda.time.LocalDate;

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

    public static Result withdraw(long mailId) {
        Mail mail = Mail.byId(mailId);
        mail.withdrawalDate = LocalDate.now();
        mail.save();
        return ok();
    }
}
