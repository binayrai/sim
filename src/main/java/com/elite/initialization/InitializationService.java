package com.elite.initialization;

import org.springframework.core.Ordered;

public interface InitializationService extends Ordered {
    void doImport() throws Exception;
}
