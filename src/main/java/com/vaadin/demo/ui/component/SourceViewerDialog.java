package com.vaadin.demo.ui.component;

import com.vaadin.demo.service.SourceService;
import com.vaadin.demo.ui.util.Lucide;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Code;
import com.vaadin.flow.component.html.Pre;

import static com.vaadin.demo.ui.util.Tailwind.*;

public class SourceViewerDialog extends Dialog {

    public SourceViewerDialog(Class<?> viewClass, SourceService sourceService) {
        setCloseOnEsc(true);
        setHeaderTitle(viewClass.getSimpleName() + ".java");
        setHeight("80vh");
        setMaxWidth("90vw");
        setWidth("900px");

        String source = sourceService.getSource(viewClass);
        String githubUrl = sourceService.getGitHubUrl(viewClass);

        // Close button in header top-right
        Button closeButton = new Button(Lucide.X.create(), e -> close());
        closeButton.addThemeVariants(ButtonVariant.SMALL, ButtonVariant.TERTIARY);
        closeButton.setAriaLabel("Close");
        closeButton.setTooltipText("Close");
        getHeader().add(closeButton);

        // Code block — Prism.js highlights it after render
        String escapedSource = source
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");

        Code code = new Code(escapedSource);
        Pre pre = new Pre(code);
        add(pre);

        // Trigger Prism highlighting after dialog renders
        addOpenedChangeListener(e -> {
            if (e.isOpened()) {
                UI.getCurrent().getPage().executeJs(
                        "setTimeout(() => { if(window.Prism) Prism.highlightAll(); }, 50);");
            }
        });

        // Footer: GitHub link with icon
        Anchor gitHub = new Anchor(githubUrl, "View on GitHub");
        gitHub.add(Lucide.SQUARE_ARROW_OUT_UP_RIGHT.create());
        gitHub.addClassNames(AlignItems.CENTER, Background.ACCENT, Border.ALL, BorderColor.SECONDARY,
                BorderRadius.MEDIUM, BoxShadow.SMALL, Color.ACCENT_CONTRAST, Display.FLEX, FontWeight.MEDIUM,
                Gap.SMALL, Padding.Horizontal.MEDIUM, Padding.End.SMALL, Padding.Vertical.CONTAINER,
                TextDecoration.NONE);
        gitHub.setTarget("_blank");
        getFooter().add(gitHub);
    }
}
