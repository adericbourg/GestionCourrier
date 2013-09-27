package core.controller;

import play.libs.Akka;
import play.libs.F;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;
import core.exception.BusinessException;

import java.util.concurrent.Callable;

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
    public F.Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
        try {
            return delegate.call(ctx);
        } catch (final BusinessException ex) {
            return Akka.future(
                    new Callable<SimpleResult>() {
                        @Override
                        public SimpleResult call() throws Exception {
                            return badRequest(Json.toJson(new ErrorMessageResult(ex.getMessage())));
                        }
                    }
            );
        }
    }
}
