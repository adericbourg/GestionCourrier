package core.controller;

import play.libs.Json;
import play.mvc.Action;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import core.exception.BusinessException;

/**
 * @author adericbourg
 */
public class CatchBusinessExceptionAction extends Action<CatchBusinessException> {

    private static class ErrorMessageResult {
        public final String type = "error";
        public final String msg;

        public ErrorMessageResult(String message) {
            msg = message;
        }
    }

    @Override
    public Result call(Http.Context ctx) throws Throwable {
        try {
            return delegate.call(ctx);
        } catch (BusinessException ex) {
            return Controller.badRequest(Json.toJson(new ErrorMessageResult(ex.getMessage())));
        }
    }
}
