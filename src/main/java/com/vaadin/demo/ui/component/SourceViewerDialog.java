package com.vaadin.demo.ui.component;

import com.vaadin.demo.service.SourceService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class SourceViewerDialog extends Dialog {

    public SourceViewerDialog(Class<?> viewClass, SourceService sourceService) {
        setHeaderTitle(viewClass.getSimpleName() + ".java");
        setWidth("900px");
        setMaxWidth("95vw");
        setHeight("82vh");
        setCloseOnEsc(true);

        String source = sourceService.getSource(viewClass);
        String githubUrl = sourceService.getGitHubUrl(viewClass);

        // Close button in header top-right
        Button closeButton = new Button(VaadinIcon.CLOSE.create(), e -> close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);
        closeButton.setAriaLabel("Close");
        getHeader().add(closeButton);

        // Code block — Prism.js highlights it after render
        String escapedSource = source
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");

        Div codeBlock = new Div();
        codeBlock.getElement().setProperty("innerHTML",
                "<pre class=\"source-code-pre language-java\"><code class=\"language-java\">"
                        + escapedSource + "</code></pre>");
        codeBlock.getStyle()
                .set("overflow", "auto")
                .set("flex", "1")
                .set("min-height", "0");

        VerticalLayout content = new VerticalLayout(codeBlock);
        content.setSizeFull();
        content.setPadding(false);
        content.setSpacing(false);
        add(content);

        // Trigger Prism highlighting after dialog renders
        addOpenedChangeListener(e -> {
            if (e.isOpened()) {
                UI.getCurrent().getPage().executeJs(
                        "setTimeout(() => { if(window.Prism) Prism.highlightAll(); }, 50);");
            }
        });

        // Footer: GitHub link with icon
        Button gitHub = new Button("View on GitHub", VaadinIcon.EXTERNAL_LINK.create(), e -> UI.getCurrent().getPage().executeJs("window.open($0,'_blank')", githubUrl));
        gitHub.addThemeVariants(ButtonVariant.PRIMARY);
        getFooter().add(gitHub);
    }
}
