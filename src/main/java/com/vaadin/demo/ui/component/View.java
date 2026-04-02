package com.vaadin.demo.ui.component;

import com.vaadin.demo.ui.util.Aura;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Main;

public class View extends Main {

    public View(Component... components) {
        super(components);
        addClassName(Aura.VIEW);
    }
}
