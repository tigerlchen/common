package com.ccp.train;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringStyle;

/**
 * ToString����
 *
 * @author �г� [2015��4��15�� ����11:00:26]
 */
public class AliTripToStringStyle extends ToStringStyle {

    private static final String MAP_START_CHAR = "{";

    private static final String MAP_END_CHAR = "}";

    private static final String NULL_TEXT = "<null>";

    private static final String VALUE_SEPARATOR = "=";

    private static final String ITEM_SEP_CHAR = ",";

    private static final long serialVersionUID = 1L;

    private static final long MAX_BYTES_LENGTH = 1 * 1024 * 1024;

    @SuppressWarnings("rawtypes")
    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, Map map) {
        buffer.append(getStringFromMap(map));
    }

    private String getStringFromMap(Map<?, ?> map) {
        StringBuffer buffer = new StringBuffer();
        if (map == null) {
            return buffer.toString();
        }
        buffer.append(MAP_START_CHAR);
        for (Iterator<?> it = map.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            Object value = map.get(key);
            if (value == null || key == null) {
                buffer.append(key).append(VALUE_SEPARATOR).append(NULL_TEXT);
                buffer.append(ITEM_SEP_CHAR);
                continue;
            }
            buffer.append(key).append(VALUE_SEPARATOR);
            if (value instanceof Collection) {
                appendDetail(buffer, key.toString(), (Collection<?>) value);
            } else if (value instanceof Map) {
                appendDetail(buffer, key.toString(), (Map<?, ?>) value);
            } else if (value instanceof long[]) {
                appendDetail(buffer, key.toString(), (long[]) value);
            } else if (value instanceof int[]) {
                appendDetail(buffer, key.toString(), (int[]) value);
            } else if (value instanceof short[]) {
                appendDetail(buffer, key.toString(), (short[]) value);
            } else if (value instanceof byte[]) {
                if(((byte[])value).length <= MAX_BYTES_LENGTH ){
                    appendDetail(buffer, key.toString(), (byte[]) value);
                }else{
                    this.appendSummary(buffer,key.toString(),(byte[]) value);
                }
            } else if (value instanceof char[]) {
                appendDetail(buffer, key.toString(), (char[]) value);
            } else if (value instanceof double[]) {
                appendDetail(buffer, key.toString(), (double[]) value);
            } else if (value instanceof float[]) {
                appendDetail(buffer, key.toString(), (float[]) value);
            } else if (value instanceof boolean[]) {
                appendDetail(buffer, key.toString(), (boolean[]) value);
            } else if (value.getClass().isArray()) {
                appendDetail(buffer, key.toString(), (Object[]) value);
            } else {
                appendDetail(buffer, key.toString(), value);
            }
            buffer.append(ITEM_SEP_CHAR);
        }
        buffer.append(MAP_END_CHAR);
        return buffer.toString();
    }

}
