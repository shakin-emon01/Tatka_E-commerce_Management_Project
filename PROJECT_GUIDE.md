# E-Commerce Project Guide

## Overview

This project contains both a JavaFX desktop client (primary) and a minimal JSP web front-end. The JavaFX app uses SQLite for local storage. Recent updates include checkout success handling, an order details dialog, and a localized profile form with District and default Country = Bangladesh.

## Features

### Customer Features
- ✅ User registration and login
- ✅ Product catalog with categories
- ✅ Product search and filtering
- ✅ Shopping cart functionality
- ✅ Checkout with success confirmation and auto-refresh of orders
- ✅ Order history with Order Details dialog (items, qty, price, subtotal)
- ✅ User profile management (District instead of State; default Country = Bangladesh)

### Admin Features
- ✅ Product management (CRUD operations)
- ✅ Category management
- ✅ Order management
- ✅ User management
- ✅ Sales reports

## Technology Stack

- **Desktop App**: JavaFX 17+, FXML, CSS
- **Web Front**: JSP/Bootstrap (optional pages)
- **Database**: SQLite 3 (default). MySQL scripts are included for web variant.
- **Build Tool**: Maven
- **Java Version**: JDK 21 recommended

## Project Structure

```
e-commerce/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ecommerce/
│   │   │       ├── controller/     # Servlet controllers
│   │   │       ├── dao/           # Data Access Objects
│   │   │       ├── model/         # Java model classes
│   │   │       ├── service/       # Business logic
│   │   │       └── util/          # Utility classes
│   │   ├── webapp/
│   │   │   ├── WEB-INF/          # Web configuration
│   │   │   ├── css/              # Custom stylesheets
│   │   │   ├── js/               # JavaScript files
│   │   │   ├── images/           # Static images
│   │   │   └── pages/            # JSP pages
│   │   └── resources/
│   │       └── database.properties
│   └── test/                     # Unit tests
├── database/
│   ├── schema.sql               # Database schema
│   └── sample_data.sql          # Sample data
│   └── ecommerce.db             # SQLite database file
├── docs/                        # Documentation
├── pom.xml                      # Maven configuration
├── README.md                    # Project overview
├── PROJECT_GUIDE.md            # This file
└── setup.bat                   # Setup script
```

## Prerequisites

Before running this project, ensure you have the following installed:

1. **Java JDK 8 or higher**
   - Download from: https://adoptium.net/
   - Set JAVA_HOME environment variable

2. **Apache Maven 3.6 or higher**
   - Download from: https://maven.apache.org/download.cgi
   - Add to PATH environment variable

3. **MySQL 8.0 or higher**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Create a database user with appropriate permissions

4. **Apache Tomcat 9.0 or higher** (optional, Maven plugin can handle this)
   - Download from: https://tomcat.apache.org/download-90.cgi

## Installation & Setup

### Step 1: Clone/Download the Project

```bash
# If using git
git clone <repository-url>
cd e-commerce

# Or download and extract the ZIP file
```

### Step 2: Database Setup (SQLite default)

SQLite is auto-configured via `src/main/resources/database.properties`. The database file is created on first run at `database/ecommerce.db`. Sample data scripts are available under `database/`.

For the web (MySQL) variant, see the original MySQL instructions below in the appendix.

### Step 3: Build the Project

```bash
# Navigate to project directory
cd e-commerce

# Clean and build
mvn clean install
```

### Step 4: Run the Application (JavaFX)
```bash
mvn javafx:run
```

### Step 5: Access the Application

Open your web browser and navigate to:
```
http://localhost:8080/ecommerce
```

## Credentials

Demo credentials are no longer shown in the UI. Create accounts via Register or seed users using the database scripts.

## Desktop UX Highlights
- Order placement shows "Order Successfull" confirmation and refreshes orders
- Order details dialog lists items with quantities and totals
- Profile form uses District instead of State; default country Bangladesh
- 2025 copyright updated across views

## Development Guide

### Adding New Features

1. **Create Model Class** (if needed)
   - Add to `src/main/java/com/ecommerce/model/`
   - Include getters, setters, and helper methods

2. **Create DAO Class** (if needed)
   - Add to `src/main/java/com/ecommerce/dao/`
   - Implement database operations

3. **Create Servlet Controller**
   - Add to `src/main/java/com/ecommerce/controller/`
   - Handle HTTP requests and responses

4. **Create JSP Pages**
   - Add to `src/main/webapp/pages/`
   - Use Bootstrap for styling

5. **Add JavaScript** (if needed)
   - Add to `src/main/webapp/js/`
   - Handle client-side interactions

### Database Modifications

1. **Update Schema**
   - Modify `database/schema.sql`
   - Add migration scripts if needed

2. **Update Model Classes**
   - Add new fields and methods
   - Update DAO classes accordingly

3. **Test Changes**
   - Run unit tests
   - Test manually in browser

### Styling Guidelines

- Use Bootstrap v5.3 classes for layout and components
- Custom CSS in `src/main/webapp/css/style.css`
- Follow responsive design principles
- Use CSS variables for consistent theming

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Check MySQL service is running
   - Verify database credentials in `database.properties`
   - Ensure database `ecommerce_db` exists

2. **Build Failures**
   - Check Java and Maven versions
   - Clean and rebuild: `mvn clean install`
   - Check for compilation errors

3. **404 Errors**
   - Verify servlet mappings in `web.xml`
   - Check file paths and names
   - Ensure Tomcat is running

4. **Session Issues**
   - Check session timeout in `web.xml`
   - Verify session management in servlets

### Debug Mode

To enable debug logging, add to `log4j.properties`:
```properties
log4j.rootLogger=DEBUG, stdout
log4j.logger.com.ecommerce=DEBUG
```

## Testing

### Unit Tests
```bash
mvn test
```

### Manual Testing Checklist

- [ ] User registration and login
- [ ] Product browsing and search
- [ ] Shopping cart functionality
- [ ] Checkout process
- [ ] Order management
- [ ] Admin panel access
- [ ] Product management (CRUD)
- [ ] Order status updates

## Deployment

For the desktop JavaFX app, package with Maven or run directly via `mvn javafx:run`. For the optional web variant, see Appendix: Web (MySQL/Tomcat) instructions.

### Appendix: Web Variant (MySQL/Tomcat)
Original servlet/JSP instructions, endpoints, and Tomcat deployment steps apply if you choose to run the web module.

## Contributing

1. Follow Java coding conventions
2. Add comments for complex logic
3. Write unit tests for new features
4. Update documentation
5. Test thoroughly before submitting

## License

This project is for educational purposes. Feel free to use and modify as needed.

## Support

For issues and questions:
1. Check the troubleshooting section
2. Review the code comments
3. Check the README.md file
4. Create an issue in the repository

---

**Happy Coding! 🚀** 