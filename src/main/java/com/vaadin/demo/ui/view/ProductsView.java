package com.vaadin.demo.ui.view;

import com.vaadin.demo.data.SampleData;
import com.vaadin.demo.service.SourceService;
import com.vaadin.demo.ui.component.SourceViewerDialog;
import com.vaadin.demo.ui.component.View;
import com.vaadin.demo.ui.component.ViewHeader;
import com.vaadin.demo.ui.util.Lucide;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.badge.Badge;
import com.vaadin.flow.component.badge.BadgeVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import static com.vaadin.demo.ui.util.Tailwind.*;

@Route("products")
@PageTitle("Products — Vaadin Demo")
public class ProductsView extends View {

    public ProductsView(SourceService sourceService) {
        add(createHeader(sourceService));

        Div content = new Div(createProductsCard());
        content.addClassNames(Display.FLEX, Overflow.HIDDEN, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
        add(content);
    }

    /**
     * Page header with drawer toggle, title, add product button, and source viewer button.
     */
    private ViewHeader createHeader(SourceService sourceService) {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addThemeVariants(ButtonVariant.TERTIARY);

        H1 title = new H1("Products");

        Button addProduct = new Button("Add Product", VaadinIcon.PLUS.create());
        addProduct.addThemeVariants(ButtonVariant.PRIMARY);
        addProduct.addClickListener(e -> openProductDialog(null));

        Button viewSource = new Button(Lucide.CODE.create(), e -> new SourceViewerDialog(ProductsView.class, sourceService).open());
        viewSource.addThemeVariants(ButtonVariant.TERTIARY);
        viewSource.setAriaLabel("View source");
        viewSource.setTooltipText("View source");

        return new ViewHeader(toggle, title, addProduct, viewSource);
    }

    /**
     * Card containing the products grid.
     */
    private Card createProductsCard() {
        Card card = new Card();
        card.add(createProductsGrid());
        card.addClassNames(Width.FULL);
        return card;
    }

    /**
     * Grid listing products with name, category, price, stock, status, and actions columns.
     */
    private Grid<SampleData.Product> createProductsGrid() {
        Grid<SampleData.Product> grid = new Grid<>(SampleData.Product.class, false);
        grid.addThemeVariants(GridVariant.NO_BORDER);

        var nameCol = grid.addColumn(SampleData.Product::name)
                .setHeader("Name").setWidth("200px").setFlexGrow(2).setResizable(true).setSortable(true);
        var categoryCol = grid.addColumn(SampleData.Product::category)
                .setHeader("Category").setWidth("140px").setFlexGrow(1).setSortable(true);
        var priceCol = grid.addColumn(SampleData.Product::price)
                .setHeader("Price").setAutoWidth(true).setFlexGrow(0).setSortable(true);
        var stockCol = grid.addColumn(SampleData.Product::stock)
                .setHeader("Stock").setAutoWidth(true).setFlexGrow(0).setSortable(true);
        var statusCol = grid.addComponentColumn(p -> createStatusBadge(p.status()))
                .setHeader("Status").setAutoWidth(true).setFlexGrow(0);
        var actionsCol = grid.addComponentColumn(product -> {
            Button edit = new Button(Lucide.SQUARE_PEN.create(), e -> openProductDialog(product));
            edit.addThemeVariants(ButtonVariant.TERTIARY);
            edit.setAriaLabel("Edit");
            edit.setTooltipText("Edit");
            return edit;
        }).setAutoWidth(true).setFlexGrow(0);

        var dataView = grid.setItems(SampleData.products());
        grid.setHeightFull();

        // Header row filters
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField nameFilter = new TextField();
        nameFilter.setClearButtonVisible(true);
        nameFilter.setPlaceholder("Filter name...");
        nameFilter.setWidthFull();
        filterRow.getCell(nameCol).setComponent(nameFilter);

        Select<String> categoryFilter = new Select<>();
        categoryFilter.setItems("", "Software", "Infrastructure", "Marketing", "Sales", "Support",
                "Developer", "Security", "Data", "Compliance", "Enterprise", "Tools");
        categoryFilter.setPlaceholder("All");
        categoryFilter.setWidthFull();
        filterRow.getCell(categoryCol).setComponent(categoryFilter);

        Select<String> statusFilter = new Select<>();
        statusFilter.setItems("", "Active", "Beta", "Deprecated", "Inactive");
        statusFilter.setPlaceholder("All");
        statusFilter.setWidthFull();
        filterRow.getCell(statusCol).setComponent(statusFilter);

        filterRow.getCell(priceCol).setText("");
        filterRow.getCell(stockCol).setText("");
        filterRow.getCell(actionsCol).setText("");

        Runnable applyFilter = () -> dataView.setFilter(p ->
                (nameFilter.getValue().isBlank() || p.name().toLowerCase().contains(nameFilter.getValue().toLowerCase())) &&
                        (categoryFilter.getValue() == null || categoryFilter.getValue().isBlank() || categoryFilter.getValue().equals(p.category())) &&
                        (statusFilter.getValue() == null || statusFilter.getValue().isBlank() || statusFilter.getValue().equals(p.status())));

        nameFilter.addValueChangeListener(e -> applyFilter.run());
        categoryFilter.addValueChangeListener(e -> applyFilter.run());
        statusFilter.addValueChangeListener(e -> applyFilter.run());

        return grid;
    }

    /**
     * Badge indicating the status of a product.
     */
    private Badge createStatusBadge(String status) {
        Badge badge = new Badge(status);
        switch (status) {
            case "Active" -> badge.addThemeVariants(BadgeVariant.SUCCESS);
            case "Beta" -> badge.addThemeVariants(BadgeVariant.CONTRAST);
            case "Deprecated" -> badge.addThemeVariants(BadgeVariant.ERROR);
        }
        return badge;
    }

    /**
     * Opens a dialog to add a new product or edit an existing one.
     */
    private void openProductDialog(SampleData.Product product) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(product == null ? "Add Product" : "Edit Product");
        dialog.setWidth("400px");

        FormLayout form = new FormLayout();

        TextField name = new TextField("Name");
        name.setValue(product != null ? product.name() : "");

        ComboBox<String> category = new ComboBox<>("Category");
        category.setItems("Software", "Infrastructure", "Marketing", "Sales", "Support",
                "Developer", "Security", "Data", "Compliance", "Enterprise", "Tools");
        category.setValue(product != null ? product.category() : null);

        TextField price = new TextField("Price");
        price.setValue(product != null ? product.price() : "");

        NumberField stock = new NumberField("Stock");
        stock.setValue(product != null ? (double) product.stock() : 0.0);
        stock.setStep(1);
        stock.setMin(0);

        Select<String> status = new Select<>("Status");
        status.setItems("Active", "Beta", "Deprecated", "Inactive");
        status.setValue(product != null ? product.status() : "Active");

        form.add(name, category, price, stock, status);
        dialog.add(form);

        Button save = new Button("Save", e -> {
            dialog.close();
            notify(product == null ? "Product created successfully" : "Product updated successfully", NotificationVariant.SUCCESS);
        });
        save.addThemeVariants(ButtonVariant.PRIMARY);

        Button cancel = new Button("Cancel", e -> dialog.close());
        cancel.addThemeVariants(ButtonVariant.TERTIARY);

        dialog.getFooter().add(cancel, save);
        dialog.open();
    }

    /**
     * Shows a toast notification at the bottom-end of the screen.
     */
    private void notify(String message, NotificationVariant... variants) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(variants);
    }
}
