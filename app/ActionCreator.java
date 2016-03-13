import play.Logger;
import play.i18n.Lang;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import java.util.concurrent.CompletionStage;

import java.lang.reflect.Method;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ActionCreator implements play.http.ActionCreator {
    @Override
    public Action createAction(Http.Request request, Method actionMethod) {
        return new Action.Simple() {
            @Override
            public CompletionStage<Result> call(Http.Context ctx) {
                Path path = Paths.get(ctx.request().path());
                String l = getLang(ctx, path);
                Logger.info("ActionCreator::createAction || Changing lang to: {}", l);

                /**
                 * This sets the cookie, but cookie is only read on next request.
                 */
                ctx.changeLang(l);

                /**
                 * This does not work.
                 */
                // ctx.setTransientLang(l);
                return delegate.call(ctx);
            }
        };
    }

    public String getLang(Http.Context ctx, Path p) {
        if (p.getNameCount() == 0) {
            return ctx.lang().code();
        }
        return p.getName(0).toString();
    }
}
