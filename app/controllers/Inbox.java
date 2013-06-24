package controllers;

import models.inbox.Mail;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import business.inbox.MandatoryForwardMailException;
import core.controller.CommonController;
import core.controller.WorkWrapper;

/**
 * @author adericbourg
 */
public class Inbox extends CommonController {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result registerMail() {
        return wrapVoid(new WorkWrapper<Void>() {
            @Override
            public Void execute() {
                JsonNode json = request().body().asJson();

                Mail mail = Json.fromJson(json, Mail.class);

                if (mail.recipient.isMailForwardMandatory()) {
                    throw new MandatoryForwardMailException();
                }

                mail.save();

                return null;
            }
        });
    }
}
