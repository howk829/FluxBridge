package com.tho.fluxbridge.common.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface FluxBridgeListener {
    String topic();
}
