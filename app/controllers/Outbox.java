package controllers;

import core.controller.Timed;
import models.inbox.Mail;

import org.joda.time.LocalDate;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.MailService;
import core.controller.CatchBusinessException;

/**
 * @author alban
 */
@Timed
@CatchBusinessException
public class Outbox extends Controller {

    /**
     * Returns all mails.
     * 
     * @return All mails.
     */
    public static Result mails() {
        return ok(Json.toJson(MailService.findFromInbox()));
    }

    public static Result withdraw(long mailId) {
        Mail mail = MailService.byId(mailId);
        mail.withdrawalDate = LocalDate.now();
        mail.save();
        return ok();
    }
}
