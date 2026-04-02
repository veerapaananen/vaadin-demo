package com.vaadin.demo.ui.component;

import com.vaadin.demo.ui.util.Aura;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Header;

public class ViewHeader extends Header {

    public ViewHeader(Component... children) {
        super(children);
        addClassName(Aura.VIEW_HEADER);
    }
}
