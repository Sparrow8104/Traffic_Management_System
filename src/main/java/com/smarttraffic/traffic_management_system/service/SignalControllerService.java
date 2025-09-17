package com.smarttraffic.traffic_management_system.service;

import com.smarttraffic.traffic_management_system.entity.SignalEvent;
import sun.misc.Signal;

public class SignalControllerService {


    public void applyDecision(SignalEvent event,int remainingGreen){
        if(event==null){
            return;
        }
        System.out.printf("[SignalController] APPLY -> Signal %d Lane %d : duration %d s (remaining %d s)%n",
                event.getSignalId(), event.getLaneId(), event.getPossibleTime(), remainingGreen);

    }
    public void deactivate(int signalId,int laneId){
        System.out.printf("[SignalController] DEACTIVATE -> Signal %d Lane %d%n", signalId, laneId);
    }
}
