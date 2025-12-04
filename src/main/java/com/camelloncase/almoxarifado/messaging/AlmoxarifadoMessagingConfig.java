package com.camelloncase.almoxarifado.messaging;

import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlmoxarifadoMessagingConfig {

    public AlmoxarifadoMessagingConfig(EventProcessingConfigurer configurer) {
        configurer.registerTrackingEventProcessor("almoxarifado");
    }
}
