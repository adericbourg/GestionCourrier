package controllers;

import core.controller.Timed;
import models.inbox.Mail;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import business.inbox.MandatoryForwardMailException;

import com.fasterxml.jackson.databind.JsonNode;
import core.controller.CatchBusinessException;

/**
 * @author adericbourg
 */
@Timed
@CatchBusinessException
public class Inbox extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result registerMail() {
        JsonNode json = request().body().asJson();

        Mail mail = Json.fromJson(json, Mail.class);

        if (mail.recipient.isMailForwardMandatory()) {
            throw new MandatoryForwardMailException();
        }

        mail.save();

        return ok();
    }
}
