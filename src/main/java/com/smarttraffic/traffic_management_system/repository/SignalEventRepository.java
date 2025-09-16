package com.smarttraffic.traffic_management_system.repository;

import com.smarttraffic.traffic_management_system.entity.SignalEvent;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SignalEventRepository {

    private final List<SignalEvent> events=new ArrayList<>();

    public void save(SignalEvent event){
        events.add(event);
    }

    public List<SignalEvent> findAll(){
        return new ArrayList<>(events);
    }
    public void clear(){
        events.clear();
    }
}
