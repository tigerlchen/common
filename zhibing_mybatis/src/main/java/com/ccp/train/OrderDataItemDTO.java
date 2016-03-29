package com.ccp.train;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author �г� [2015��5��7�� ����2:31:40]
 */
@Getter
@Setter
public class OrderDataItemDTO extends BaseDO {

    private static final long serialVersionUID = -7872802564083292416L;
    
    private String sequence_no;
    
    private String order_date;
    
    private Integer ticket_totalnum;
    
    private Double ticket_price_all;
    
    private String cancel_flag;
    
    private String resign_flag;
    
    private String return_flag;
    
    private String print_eticket_flag;
    
    private String pay_flag;
    
    private String pay_resign_flag;
    
    private String confirm_flag;
    
    private List<TicketItemDTO> tickets = new ArrayList<TicketItemDTO>();
    
    private String reserve_flag_query;
    
    private String if_show_resigning_info;
    
    private String recordCount;
    
    private String isNeedSendMailAndMsg;
    
    private List<String> array_passser_name_page = new ArrayList<String>();
    
    private List<String> from_station_name_page = new ArrayList<String>();
    
    private List<String> to_station_name_page = new ArrayList<String>();

    private String start_train_date_page;
    
    private String start_time_page;
    
    private String arrive_time_page;
    
    private String train_code_page;
    
    private String ticket_total_price_page;
    
    private String come_go_traveller_order_page;
    
    private String canOffLinePay;
    
    private String if_deliver;
}
