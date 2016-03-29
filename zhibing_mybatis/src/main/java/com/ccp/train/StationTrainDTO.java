package com.ccp.train;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationTrainDTO extends BaseDO {

    private static final long serialVersionUID = 4565781238397942716L;
    
    private TrainDTO trainDTO;
    
    private String station_train_code;
    
    private String from_station_telecode;
    
    private String from_station_name;
    
    private String start_time;
    
    private String to_station_telecode;
    
    private String to_station_name;
    
    private String arrive_time;
    
    private String distance;
    
}
