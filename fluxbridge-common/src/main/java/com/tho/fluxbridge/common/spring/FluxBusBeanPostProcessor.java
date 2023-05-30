package com.tho.fluxbridge.common.spring;

import com.google.gson.Gson;
import com.tho.fluxbridge.common.annotation.FluxBridgeListener;
import com.tho.fluxbridge.common.dto.DataSpec;
import com.tho.fluxbridge.common.dto.Message;
import io.rsocket.util.DefaultPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.Disposable;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FluxBusBeanPostProcessor implements DestructionAwareBeanPostProcessor {

    private final Map<String, Disposable> handlerMap = new HashMap<>();

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(
        6, 16, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>()
    );

    @Autowired
    private RSocketRequester rSocketRequester;

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        handlerMap.values().forEach(d -> d.dispose());
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        Object proxy = this.getTargetObject(bean);
        Class<?> klass = proxy.getClass();

        while (klass != Object.class) {
            for (final Method method : klass.getDeclaredMethods()) {

                if (method.isAnnotationPresent(FluxBridgeListener.class)) {

                    if (method.getParameterCount() > 2 || !DataSpec.class.isAssignableFrom(method.getParameterTypes()[0])) {
                        throw new IllegalArgumentException(
                                String.format("Method %s cannot applied to annotation EventHandler", method.getName()));
                    }

                    FluxBridgeListener annotInstance = method.getAnnotation(FluxBridgeListener.class);
                    processCommand(annotInstance.topic(), bean, method);
                }
            }
            klass = klass.getSuperclass();
        }
        return bean;
    }

    private void processCommand(final String topic, Object bean, Method handler) {
        // initial frame
        var d = rSocketRequester
                .route("subscribe")
                .data(topic)
                .retrieveFlux(DataSpec.class)
                .doOnNext(m -> {
                    try {
                        handler.invoke(bean, m);
                    } catch (Exception e) {
                        log.error("error processing fluxbridge listener", e);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
        handlerMap.put(topic, d);

    }

    private Object getTargetObject(Object proxy) throws BeansException {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            try {
                return ((Advised) proxy).getTargetSource().getTarget();
            } catch (Exception e) {
                throw new FatalBeanException("Error getting target of JDK proxy", e);
            }
        }
        return proxy;
    }

}
