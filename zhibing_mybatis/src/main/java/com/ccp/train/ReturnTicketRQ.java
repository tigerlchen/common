package com.ccp.train;

import lombok.Getter;
import lombok.Setter;

/**
 * @author �г� [2015��10��2�� ����4:09:49]
 */
@Getter
@Setter
public class ReturnTicketRQ extends BaseDO{

    private static final long serialVersionUID = 4215406148283371111L;
    
    private String sequence_no;
    private String batch_no;
    private String coach_no;
    private String seat_no;
    private String start_train_date_page;
    private String train_code;
    private String coach_name;
    private String seat_name;
    private String seat_type_name;
    private String train_date;
    private String from_station_name;
    private String to_station_name;
    private String start_time;
    private String passenger_name;
    
}
