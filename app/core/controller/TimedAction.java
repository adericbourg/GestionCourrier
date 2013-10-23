package core.controller;

import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;

import com.codahale.metrics.Timer;
import controllers.metrics.Metrics;

/**
 *
 */
public class TimedAction extends Action<Timed> {

    @Override
    public F.Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
        Timer.Context context = Metrics.getRegistry()
                .timer(ctx._requestHeader().method() + " " + ctx.args.get("ROUTE_PATTERN"))
                .time();
        try {
            return delegate.call(ctx);
        } finally {
            context.stop();
        }
    }
}
