# Vaadin Demo Application

A demo application built with Vaadin 25 and Spring Boot 4, showcasing the Aura theme and common UI patterns.

## Views

- **Dashboard** -- KPI stat cards and a recent orders grid
- **Components** -- Interactive gallery of Vaadin UI components
- **Products** -- Filterable product grid with edit dialog
- **Users** -- User management grid with avatars and role filtering
- **Reports** -- Chart placeholders (coming soon)
- **Settings** -- Tabbed settings with general, notifications, security, and integrations

## Requirements

- Java 21+
- Maven 3.9+ (or use the included `mvnw` wrapper)

## Running

```
./mvnw spring-boot:run
```

The app starts at [http://localhost:8080](http://localhost:8080).

The first run takes longer as Vaadin downloads frontend dependencies and builds the dev bundle.

## License

[Unlicense](LICENSE.md) -- public domain.
