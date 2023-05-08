package com.tho.fluxbridgeconsumerdemo.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message<T> implements java.io.Serializable {

//    protected Type type;
    protected String topic;
    protected T data;
//    protected long index;

//    public enum Type {
//        DELETE,
//        END_OF_SNAPSHOT,
//        INSERT,
//        REPLACE,
//        SNAPSHOT,
//        UPDATE,
//    }
}