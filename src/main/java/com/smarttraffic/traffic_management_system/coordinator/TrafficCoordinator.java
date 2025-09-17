package com.smarttraffic.traffic_management_system.coordinator;

import com.smarttraffic.traffic_management_system.entity.SignalEvent;
import com.smarttraffic.traffic_management_system.repository.SignalEventRepository;
import com.smarttraffic.traffic_management_system.service.SignalControllerService;
import com.smarttraffic.traffic_management_system.service.TrafficService;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrafficCoordinator {

    private final SignalEventRepository signalEventRepository;
    private final TrafficService trafficService;
    private final SignalControllerService signalControllerService;

    public TrafficCoordinator(SignalEventRepository signalEventRepository, TrafficService trafficService, SignalControllerService signalControllerService) {
        this.signalEventRepository = signalEventRepository;
        this.trafficService = trafficService;
        this.signalControllerService = signalControllerService;
    }

    public void handleNewEvents(List<SignalEvent> events){
        if(events==null||events.isEmpty()) return;

        SignalEvent decision=trafficService.processSignalEvents(events);

    }


}
