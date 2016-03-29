package com.ccp.train;

/**
 * Created by zhangjun.zyk on 2015/1/12.
 *
 * ���л��������,�������л�����Ӧ���м̳�
 */

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class BaseDO implements Serializable{

    private static final long serialVersionUID = 7497524947065819618L;

    @Override
    public String toString(){
        return ReflectionToStringBuilder.toString(this, new AliTripToStringStyle());
    }
}