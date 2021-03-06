package com.ccp.train;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO extends BaseDO {

    private static final long serialVersionUID = 9210787175912507702L;

    private String order_total_number;
    
    private List<OrderDataItemDTO> OrderDTODataList = new ArrayList<OrderDataItemDTO>();
    
}
