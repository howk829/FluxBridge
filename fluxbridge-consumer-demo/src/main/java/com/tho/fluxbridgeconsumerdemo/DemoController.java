package com.tho.fluxbridgeconsumerdemo;


import com.tho.fluxbridge.common.annotation.FluxBridgeListener;
import com.tho.fluxbridge.common.dto.DataSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class DemoController {

    @FluxBridgeListener(topic = "register")
    public void register(DataSpec dataSpec) {
        log.info("receiving dataSpec: {}", dataSpec);
    }

}
