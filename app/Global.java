import play.Application;
import play.GlobalSettings;
import play.Play;
import dev.DevDataFactory;

/**
 * @author adericbourg
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        super.onStart(app);
        if (Play.isDev()) {
            // Create test data.
            DevDataFactory.createData();
        }
    }
}
