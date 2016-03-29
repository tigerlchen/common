package com.ccp.train;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO extends BaseDO {

    private static final long serialVersionUID = -6802126006394322255L;

    private String validateMessagesShowId;
    
    private Boolean status;
    
    private Integer httpstatus;
    
    private OrderItemDTO data;
    
    private List<String> messages = new ArrayList<String>();
    
    private String validateMessages;
    
}
