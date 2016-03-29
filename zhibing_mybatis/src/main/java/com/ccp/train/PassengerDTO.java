package com.ccp.train;

import lombok.Getter;
import lombok.Setter;

/**
 * @author �г� [2015��10��2�� ����3:51:07]
 */
@Getter
@Setter
public class PassengerDTO extends BaseDO {

    private static final long serialVersionUID = -573402489066504647L;
    
    private String passenger_name;
    
    private String passenger_id_type_code;
    
    private String passenger_id_type_name;
    
    private String passenger_id_no;
    
    private String total_times;

}
