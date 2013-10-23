package controllers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import play.mvc.Controller;
import play.mvc.Result;

import com.codahale.metrics.ConsoleReporter;
import controllers.metrics.Metrics;

/**
 *
 */
public class MetricsController extends Controller {

    public static Result index() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (PrintStream ps = new PrintStream(os)) {
            ConsoleReporter consoleReporter = ConsoleReporter
                    .forRegistry(Metrics.getRegistry())
                    .outputTo(ps)
                    .build();
            consoleReporter.report();

        }
        return ok(os.toByteArray()).as("text/plain");
    }
}
