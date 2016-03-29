package com.ccp.train;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketItemDTO extends BaseDO {

    private static final long serialVersionUID = 4565781238397942716L;
    
    private StationTrainDTO stationTrainDTO;
    
    private PassengerDTO passengerDTO;
    
    private String ticket_no;
    
    private String sequence_no;
    
    private String batch_no;
    
    private String train_date;
    
    private String coach_no;
    
    private String coach_name;
    private String seat_name;
    private String seat_no;
    
    private String seat_flag;
    
    private String seat_type_code;
    
    private String seat_type_name;
    
    private String ticket_type_code;
    
    private String ticket_type_name;
    
    private String reserve_time;
    
    private String limit_time;
    
    private String lose_time;
    
    private String pay_limit_time;
    
    private Double ticket_price;
    
    private String print_eticket_flag;
    
    private String resign_flag;
    
    private String confirm_flag;
    
    private String pay_mode_code;
    
    private String ticket_status_code;
    
    private String ticket_status_name;
    
    private String cancel_flag;
    
    private Integer amount_char;
    
    private String trade_mode;
    
    private String start_train_date_page;
    
    private String str_ticket_price_page;
    
    private String come_go_traveller_ticket_page;
    
    private String return_deliver_flag;
    
    private String deliver_fee_char;
    
    private Boolean is_need_alert_flag;
    
    private String is_deliver;
    
    private String dynamicProp;

}
