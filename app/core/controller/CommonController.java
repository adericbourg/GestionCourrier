package core.controller;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import core.exception.BusinessException;

/**
 * @author adericbourg
 */
public class CommonController extends Controller {

    private static class ErrorMessageResult {
        public final String type = "error";
        public final String msg;

        public ErrorMessageResult(String message) {
            msg = message;
        }
    }

    protected static Result wrapJson(WorkWrapper<JsonNode> workWrapper) {
        try {
            return Controller.ok(workWrapper.execute());
        } catch (BusinessException e) {
            return Controller.badRequest(Json.toJson(new ErrorMessageResult(e.getMessage())));
        }
    }

    protected static Result wrapVoid(WorkWrapper<Void> workWrapper) {
        try {
            workWrapper.execute();
            return Controller.ok();
        } catch (BusinessException e) {
            return Controller.badRequest(Json.toJson(new ErrorMessageResult(e.getMessage())));
        }
    }
}
