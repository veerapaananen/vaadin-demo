package com.vaadin.demo.ui.component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * Dark/light theme toggle button for the Aura theme.
 * Persists the choice to {@code localStorage}.
 */
public class ThemeToggle extends Button {

    public ThemeToggle() {
        Icon sunIcon = VaadinIcon.SUN_O.create();
        Icon moonIcon = VaadinIcon.MOON.create();
        moonIcon.setVisible(false);

        addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);
        setAriaLabel("Toggle dark/light theme");
        getElement().appendChild(moonIcon.getElement());
        getElement().appendChild(sunIcon.getElement());

        addClickListener(e -> {
            UI.getCurrent().getPage().executeJs(
                "const isDark = document.documentElement.getAttribute('theme')==='dark';" +
                "if(isDark){" +
                "  document.documentElement.removeAttribute('theme');" +
                "  document.documentElement.style.removeProperty('color-scheme');" +
                "  localStorage.setItem('aura:theme-scheme','light');" +
                "  return false;" +
                "} else {" +
                "  document.documentElement.setAttribute('theme','dark');" +
                "  document.documentElement.style.setProperty('color-scheme','dark');" +
                "  localStorage.setItem('aura:theme-scheme','dark');" +
                "  return true;" +
                "}")
            .then(Boolean.class, dark -> {
                sunIcon.setVisible(dark);
                moonIcon.setVisible(!dark);
            });
        });

        // Restore saved preference and sync icon state on attach
        addAttachListener(event -> UI.getCurrent().getPage().executeJs(
            "const scheme = localStorage.getItem('aura:theme-scheme');" +
            "if(scheme === 'light') {" +
            "  document.documentElement.removeAttribute('theme');" +
            "  document.documentElement.style.removeProperty('color-scheme');" +
            "  return false;" +
            "} else {" +
            "  document.documentElement.setAttribute('theme','dark');" +
            "  document.documentElement.style.setProperty('color-scheme','dark');" +
            "  return true;" +
            "}")
            .then(Boolean.class, dark -> {
                sunIcon.setVisible(dark);
                moonIcon.setVisible(!dark);
            }));
    }
}
