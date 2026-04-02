package com.vaadin.demo.ui.component;

import com.vaadin.demo.ui.util.Aura;
import com.vaadin.flow.component.html.Div;

public class ViewHeading extends Div {

    public ViewHeading(String text) {
        super(text);
        addClassName(Aura.VIEW_HEADING);
    }
}
