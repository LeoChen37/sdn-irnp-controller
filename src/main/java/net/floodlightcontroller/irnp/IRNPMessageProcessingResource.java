package net.floodlightcontroller.irnp;
 
import java.util.Base64;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import irnp.IRNPProtocolViolationException;



public class IRNPMessageProcessingResource extends ServerResource {
	protected static Logger log = LoggerFactory.getLogger(IRNPMessageProcessingResource.class);
	
	@Get("json")
    public String retrieve() {
		String messageString = (String) getRequestAttributes().get(IRNPMessageProcessingWebRoutable.MESSAGE);
		System.out.println(messageString);
		
		IRNPManagerService irnpService = 
	            (IRNPManagerService) getContext().getAttributes().
	                get(IRNPManagerService.class.getCanonicalName());
        
		if (log.isDebugEnabled())
            log.debug("Recieved IRNP message: " + messageString);
		
		byte[] messageBytes = Base64.getUrlDecoder().decode(messageString);
        
		String result = "no";
		try {
			result = irnpService.processMessage(messageBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result.equals("yes")) {
			return "IRNP message is recieved successfully.\n";
		} else {
			return "IRNP message is not recieved in a proper way.\n";
		}
		
    }
}
