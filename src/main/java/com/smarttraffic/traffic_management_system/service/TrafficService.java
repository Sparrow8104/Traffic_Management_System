package com.smarttraffic.traffic_management_system.service;

import com.smarttraffic.traffic_management_system.repository.SignalEventRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TrafficService {

    private final SignalEventRepository signalEventRepository;
    private int activeSignalId=-1;
    private int activeLaneId=-1;
    private int greenTimeRemaining=0;

    private final int EMERGENCY_EXTENSION=6;
    private final int MAX_WAIT_TIME=90;

    private Map<Integer,Integer> laneWaitTime=new HashMap<>();

    public TrafficService(SignalEventRepository signalEventRepository) {
        this.signalEventRepository = signalEventRepository;
    }

}
