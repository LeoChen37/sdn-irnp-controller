package net.floodlightcontroller.irnp;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import net.floodlightcontroller.restserver.RestletRoutable;

public class IRNPMessageProcessingWebRoutable implements RestletRoutable {
	
	public static final String MESSAGE = "MESSAGE";

	@Override
	public Restlet getRestlet(Context context) {
		Router router = new Router(context);
        router.attach("/message/{" + MESSAGE + "}", IRNPMessageProcessingResource.class);
        return router;
	}

	@Override
	public String basePath() {
		return "/irnp";
	}

}
