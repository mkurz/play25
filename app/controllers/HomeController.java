package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    public Result index() {
        return redirect(routes.HomeController.langTest("en"));
    }

    public Result langTest(String lang) {
        Logger.info("HomeController::langTest({}) || hello -> {}", lang, ctx().messages().at("hello"));

        /**
         * Setting transient lang here does not work either
         */
        //ctx().setTransientLang(lang);

        return ok(index.render());
    }
}
