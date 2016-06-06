package net.floodlightcontroller.irnp;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.restserver.IRestApiService;
import irnp.IRNPProtocolViolationException;
import irnp.processingElements.*;

public class IRNPManager implements IFloodlightModule, IRNPManagerService {
	
	protected static Logger logger;
	protected IRNPProcessor processor;
	protected IRestApiService restApiService;

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
	    l.add(IRNPManagerService.class);
	    return l;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		Map<Class<? extends IFloodlightService>, IFloodlightService> m = new HashMap<Class<? extends IFloodlightService>, IFloodlightService>();
	    m.put(IRNPManagerService.class, this);
	    return m;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		Collection<Class<? extends IFloodlightService>> l =
		        new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IRestApiService.class);
		return l;
	}

	@Override
	public void init(FloodlightModuleContext context) throws FloodlightModuleException {
		logger = LoggerFactory.getLogger(IRNPManager.class);
		restApiService = context.getServiceImpl(IRestApiService.class);
		try {
			// CGX:ASNumber needs to be set up here.
			processor = new IRNPProcessor(0);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void startUp(FloodlightModuleContext context) throws FloodlightModuleException {
		restApiService.addRestletRoutable(new IRNPMessageProcessingWebRoutable());
	}
	
	@Override
	public String processMessage(byte[] messageBytes) {
	    try {
			processor.processMessage(messageBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "no";
		} catch (IRNPProtocolViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "no";
		}
	    return "yes";
	}

}
