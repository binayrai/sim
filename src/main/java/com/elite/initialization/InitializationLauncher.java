package com.elite.initialization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitializationLauncher implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger LOG = LoggerFactory.getLogger(InitializationLauncher.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<InitializationService> values = new ArrayList<>(
                event.getApplicationContext().getBeansOfType(InitializationService.class).values());
        OrderComparator.sort(values);
        for (InitializationService importService : values) {
            try {
                importService.doImport();
            } catch (Exception ex) {
                LOG.error("Error on running initialization", ex);
            }
        }
    }
}