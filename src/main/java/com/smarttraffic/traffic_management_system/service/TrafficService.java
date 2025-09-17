package com.smarttraffic.traffic_management_system.service;

import com.smarttraffic.traffic_management_system.entity.SignalEvent;
import com.smarttraffic.traffic_management_system.repository.SignalEventRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    public void processSignalEvents(List<SignalEvent> events){
        signalEventRepository.clear();;
        events.forEach(signalEventRepository::save);

        for(SignalEvent e:events){
            laneWaitTime.putIfAbsent(e.getLaneId(),0);
            if(e.getLaneId()==activeLaneId){
                laneWaitTime.put(e.getLaneId(),0);
            }else{
                laneWaitTime.put(e.getLaneId(),laneWaitTime.get(e.getLaneId())+1);
            }
        }

        SignalEvent nextEvent=determineNextSignal(events);
        activateSignal(nextEvent);
    }

    private SignalEvent determineNextSignal(List<SignalEvent> events){
        for(SignalEvent e:events){
            if(e.isEmergencyVehicle()&&e.getLaneId()==activeLaneId){
                greenTimeRemaining+=EMERGENCY_EXTENSION;
                return e;
            }
        }

        for(SignalEvent e:events){
            if(e.isEmergencyVehicle()){
                return e;
            }
        }

        for(SignalEvent e:events){
            if(laneWaitTime.get(e.getLaneId())>=MAX_WAIT_TIME) {
                return e;
            }
        }

        return events.stream()
                .max((e1,e2)->Integer.compare(e1.getQueueLength()+e1.getVehicleCount()
                        ,e2.getQueueLength()+e2.getVehicleCount()))
                .orElse(events.get(0));
    }

    private void activateSignal(SignalEvent event){
        if(activeLaneId!=event.getLaneId()||activeSignalId!= event.getSignalId()){
            activeSignalId=event.getSignalId();
            activeLaneId=event.getLaneId();
            greenTimeRemaining=event.getPossibleTime();
            laneWaitTime.put(event.getLaneId(),0);
        }
    }

    public void tick(){
        if(greenTimeRemaining>0) greenTimeRemaining--;
        if(greenTimeRemaining==0&&activeLaneId!=-1){
            activeLaneId=-1;
            activeSignalId=-1;
        }
    }

    public List<SignalEvent> getAllSignalStatuses(){
        List<SignalEvent> events=signalEventRepository.findAll();
        for(SignalEvent e:events){
            if(e.getLaneId()==activeLaneId){
                e.setPossibleTime(greenTimeRemaining);
            }
        }
        return events;
    }

    public int getActiveSignalId(){ return activeSignalId;}
    public int getActiveLaneId(){return activeLaneId;}
    public int getGreenTimeRemaining(){return greenTimeRemaining;}




}
