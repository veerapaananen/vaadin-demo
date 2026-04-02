package com.vaadin.demo.ui.component;

import com.vaadin.demo.ui.util.Aura;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Footer;

public class ViewFooter extends Footer {

    public ViewFooter(Component... children) {
        super(children);
        addClassName(Aura.VIEW_FOOTER);
    }
}
