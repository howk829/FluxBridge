package com.tho.fluxbridgeconsumerdemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import reactor.core.Disposable;

import java.util.HashMap;
import java.util.Map;

public class FluxBusBeanPostProcessor implements DestructionAwareBeanPostProcessor {


    private Map<String, Disposable> handlerMap = new HashMap<>();


    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        handlerMap.values().forEach(d -> d.dispose());
    }





}
