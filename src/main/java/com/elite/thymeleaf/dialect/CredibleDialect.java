package com.elite.thymeleaf.dialect;

import org.thymeleaf.dialect.AbstractXHTMLEnabledDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class CredibleDialect extends AbstractXHTMLEnabledDialect {
    @Override
    public String getPrefix() {
        return "elite";
    }

    @Override
    public Set<IProcessor> getProcessors() {
        HashSet<IProcessor> iProcessors = new HashSet<>();
        iProcessors.add(new ConfirmButtonProcessor());
        return iProcessors;
    }
}
