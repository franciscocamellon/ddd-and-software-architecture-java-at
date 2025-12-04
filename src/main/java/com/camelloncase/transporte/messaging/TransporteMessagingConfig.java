package com.camelloncase.transporte.messaging;

import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransporteMessagingConfig {

    public TransporteMessagingConfig(EventProcessingConfigurer configurer) {
        configurer.registerTrackingEventProcessor("transporte");
    }
}
