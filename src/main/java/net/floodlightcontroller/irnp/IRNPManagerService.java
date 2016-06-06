package net.floodlightcontroller.irnp;

import net.floodlightcontroller.core.module.IFloodlightService;

public interface IRNPManagerService extends IFloodlightService {
	/**
     * Adds a REST API
     * @param IRNP message in bytes
     */
	public String processMessage(byte[] messageBytes);
	
//	/**
//     * Adds a REST API
//     * @param path
//     */
//	public void reserveForPath();
}
