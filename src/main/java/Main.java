import frontController.FrontController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/ers", Location.CLASSPATH);
        }).start(9000);
        new FrontController(app);
    }
}
