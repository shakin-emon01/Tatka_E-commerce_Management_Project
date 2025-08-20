# E-Commerce Project UML Class Diagram

```mermaid
classDiagram
    %% Main Application Class
    class ECommerceApp {
        -Stage primaryStage
        -User currentUser
        +start(Stage stage) void
        -initializeDatabaseTables() void
        -testDatabaseConnection() boolean
        -showDatabaseError() void
        +getCurrentUser() User
        +setCurrentUser(User user) void
        +getPrimaryStage() Stage
    }

    %% Model Classes
    class User {
        -int id
        -String email
        -String password
        -String firstName
        -String lastName
        -String phone
        -String address
        -String city
        -String state
        -String zipCode
        -String country
        -String role
        -Timestamp createdAt
        -Timestamp updatedAt
        -boolean active
        +User()
        +User(int id, String email, String password, String firstName, String lastName, String phone, String address, String city, String state, String zipCode, String country, String role, Timestamp createdAt, Timestamp updatedAt)
        +User(String email, String password, String firstName, String lastName, String phone)
        +getId() int
        +setId(int id) void
        +getEmail() String
        +setEmail(String email) void
        +getPassword() String
        +setPassword(String password) void
        +getFirstName() String
        +setFirstName(String firstName) void
        +getLastName() String
        +setLastName(String lastName) void
        +getPhone() String
        +setPhone(String phone) void
        +getAddress() String
        +setAddress(String address) void
        +getCity() String
        +setCity(String city) void
        +getState() String
        +setState(String state) void
        +getZipCode() String
        +setZipCode(String zipCode) void
        +getCountry() String
        +setCountry(String country) void
        +getRole() String
        +setRole(String role) void
        +getCreatedAt() Timestamp
        +setCreatedAt(Timestamp createdAt) void
        +getUpdatedAt() Timestamp
        +setUpdatedAt(Timestamp updatedAt) void
        +isActive() boolean
        +setActive(boolean active) void
        +getFullName() String
        +isAdmin() boolean
        +isCustomer() boolean
        +setAdmin(boolean isAdmin) void
        +toString() String
    }

    class Product {
        -int id
        -String name
        -String description
        -BigDecimal price
        -int stockQuantity
        -int categoryId
        -String imageUrl
        -boolean isActive
        -Timestamp createdAt
        -Timestamp updatedAt
        -String categoryName
        -String pricingUnit
        +Product()
        +Product(int id, String name, String description, BigDecimal price, int stockQuantity, int categoryId, String imageUrl, boolean isActive, Timestamp createdAt, Timestamp updatedAt, String pricingUnit)
        +Product(String name, String description, BigDecimal price, int stockQuantity, int categoryId, String imageUrl, String pricingUnit)
        +Product(String name, String description, BigDecimal price, int stockQuantity, int categoryId, String imageUrl)
        +getId() int
        +setId(int id) void
        +getName() String
        +setName(String name) void
        +getDescription() String
        +setDescription(String description) void
        +getPrice() BigDecimal
        +setPrice(BigDecimal price) void
        +getStockQuantity() int
        +setStockQuantity(int stockQuantity) void
        +getStock() int
        +getCategoryId() int
        +setCategoryId(int categoryId) void
        +getImageUrl() String
        +setImageUrl(String imageUrl) void
        +isActive() boolean
        +setActive(boolean active) void
        +getCreatedAt() Timestamp
        +setCreatedAt(Timestamp createdAt) void
        +getUpdatedAt() Timestamp
        +setUpdatedAt(Timestamp updatedAt) void
        +getCategoryName() String
        +setCategoryName(String categoryName) void
        +getPricingUnit() String
        +setPricingUnit(String pricingUnit) void
        +getPricingUnitDisplay() String
        +isInStock() boolean
        +isLowStock() boolean
        +getFormattedPrice() String
        +getShortDescription() String
        +getStockStatus() String
        +toString() String
    }

    class Category {
        -int id
        -String name
        -String description
        -String imageUrl
        -Timestamp createdAt
        +Category()
        +Category(int id, String name, String description, String imageUrl, Timestamp createdAt)
        +Category(String name, String description, String imageUrl)
        +getId() int
        +setId(int id) void
        +getName() String
        +setName(String name) void
        +getDescription() String
        +setDescription(String description) void
        +getImageUrl() String
        +setImageUrl(String imageUrl) void
        +getCreatedAt() Timestamp
        +setCreatedAt(Timestamp createdAt) void
        +toString() String
    }

    class Order {
        -int id
        -int userId
        -BigDecimal totalAmount
        -String status
        -String shippingAddress
        -String shippingCity
        -String shippingState
        -String shippingZipCode
        -String shippingCountry
        -String paymentMethod
        -String paymentStatus
        -Timestamp createdAt
        -Timestamp updatedAt
        -String customerName
        -String customerEmail
        -List~OrderItem~ orderItems
        +Order()
        +Order(int id, int userId, BigDecimal totalAmount, String status, String shippingAddress, String shippingCity, String shippingState, String shippingZipCode, String shippingCountry, String paymentMethod, String paymentStatus, Timestamp createdAt, Timestamp updatedAt)
        +Order(int userId, BigDecimal totalAmount, String shippingAddress, String shippingCity, String shippingState, String shippingZipCode, String shippingCountry, String paymentMethod)
        +getId() int
        +setId(int id) void
        +getUserId() int
        +setUserId(int userId) void
        +getTotalAmount() BigDecimal
        +setTotalAmount(BigDecimal totalAmount) void
        +getStatus() String
        +setStatus(String status) void
        +getShippingAddress() String
        +setShippingAddress(String shippingAddress) void
        +getShippingCity() String
        +setShippingCity(String shippingCity) void
        +getShippingState() String
        +setShippingState(String shippingState) void
        +getShippingZipCode() String
        +setShippingZipCode(String shippingZipCode) void
        +getShippingCountry() String
        +setShippingCountry(String shippingCountry) void
        +getPaymentMethod() String
        +setPaymentMethod(String paymentMethod) void
        +getPaymentStatus() String
        +setPaymentStatus(String paymentStatus) void
        +getCreatedAt() Timestamp
        +setCreatedAt(Timestamp createdAt) void
        +getUpdatedAt() Timestamp
        +setUpdatedAt(Timestamp updatedAt) void
        +getCustomerName() String
        +setCustomerName(String customerName) void
        +getCustomerEmail() String
        +setCustomerEmail(String customerEmail) void
        +getOrderItems() List~OrderItem~
        +setOrderItems(List~OrderItem~ orderItems) void
        +getFormattedTotalAmount() String
        +getFullShippingAddress() String
        +getStatusBadgeClass() String
        +getPaymentStatusBadgeClass() String
        +isPending() boolean
        +isConfirmed() boolean
        +isShipped() boolean
        +isDelivered() boolean
        +isCancelled() boolean
        +isPaid() boolean
        +toString() String
    }

    class OrderItem {
        -int id
        -int orderId
        -int productId
        -int quantity
        -BigDecimal price
        -String productName
        -String productImageUrl
        +OrderItem()
        +OrderItem(int id, int orderId, int productId, int quantity, BigDecimal price)
        +OrderItem(int orderId, int productId, int quantity, BigDecimal price)
        +getId() int
        +setId(int id) void
        +getOrderId() int
        +setOrderId(int orderId) void
        +getProductId() int
        +setProductId(int productId) void
        +getQuantity() int
        +setQuantity(int quantity) void
        +getPrice() BigDecimal
        +setPrice(BigDecimal price) void
        +getProductName() String
        +setProductName(String productName) void
        +getProductImageUrl() String
        +setProductImageUrl(String productImageUrl) void
        +getSubtotal() BigDecimal
        +getFormattedPrice() String
        +getFormattedSubtotal() String
        +toString() String
    }

    %% DAO Classes
    class UserDAO {
        +findByEmail(String email) User
        +findById(int id) User
        +create(User user) boolean
        +update(User user) boolean
        +delete(int id) boolean
        +findAll() List~User~
        +findByRole(String role) List~User~
        +searchByName(String name) List~User~
        -mapResultSetToUser(ResultSet rs) User
    }

    class ProductDAO {
        +findAll() List~Product~
        +findByCategory(int categoryId) List~Product~
        +findById(int id) Product
        +searchByName(String name) List~Product~
        +save(Product product) boolean
        +delete(int id) boolean
        +updateStock(int productId, int quantity) boolean
        -insert(Product product) boolean
        -update(Product product) boolean
        -mapResultSetToProduct(ResultSet rs) Product
    }

    class CategoryDAO {
        +findAll() List~Category~
        +findById(int id) Category
        +save(Category category) boolean
        +delete(int id) boolean
        +getProductCount(int categoryId) int
        -insert(Category category) boolean
        -update(Category category) boolean
        -mapResultSetToCategory(ResultSet rs) Category
    }

    class OrderDAO {
        +findAll() List~Order~
        +findByUserId(int userId) List~Order~
        +findRecent(int limit) List~Order~
        +findLatestByUserId(int userId) Order
        +findById(int id) Order
        +save(Order order) boolean
        +updateStatus(int orderId, String status) boolean
        +updatePaymentStatus(int orderId, String paymentStatus) boolean
        +getTotalRevenue() BigDecimal
        +getOrderCount() int
        -insert(Order order) boolean
        -update(Order order) boolean
        -mapResultSetToOrder(ResultSet rs) Order
    }

    class OrderItemDAO {
        +findByOrderId(int orderId) List~OrderItem~
        +save(OrderItem orderItem) boolean
        +deleteByOrderId(int orderId) boolean
        -insert(OrderItem orderItem) boolean
        -mapResultSetToOrderItem(ResultSet rs) OrderItem
    }

    %% Controller Classes
    class LoginController {
        -TextField emailField
        -PasswordField passwordField
        -CheckBox rememberMeCheckBox
        -Label errorLabel
        -UserDAO userDAO
        +initialize() void
        +handleLogin() void
        +handleRegister() void
        -showError(String message) void
        -isValidEmail(String email) boolean
        -navigateToDashboard(User user) void
    }

    class RegisterController {
        -TextField firstNameField
        -TextField lastNameField
        -TextField emailField
        -PasswordField passwordField
        -PasswordField confirmPasswordField
        -TextField phoneField
        -Label errorLabel
        -UserDAO userDAO
        +initialize() void
        +handleRegister() void
        +handleBackToLogin() void
        -showError(String message) void
        -isValidEmail(String email) boolean
        -isValidPassword(String password) boolean
        -passwordsMatch(String password, String confirmPassword) boolean
    }

    class AdminDashboardController {
        -TabPane mainTabPane
        -MenuButton userMenuButton
        -Label statusLabel
        -Label totalProductsLabel
        -Label totalOrdersLabel
        -Label totalUsersLabel
        -Label totalRevenueLabel
        -TableView~Order~ recentOrdersTable
        -TableView~Product~ productsTable
        -TableView~Order~ ordersTable
        -TableView~User~ usersTable
        -TableView~Category~ categoriesTable
        -ProductDAO productDAO
        -CategoryDAO categoryDAO
        -OrderDAO orderDAO
        -UserDAO userDAO
        -User currentUser
        -ObservableList~Product~ products
        -ObservableList~Order~ orders
        -ObservableList~User~ users
        -ObservableList~Category~ categories
        +initialize() void
        +setupDashboard() void
        +setupProductsTable() void
        +setupOrdersTable() void
        +setupUsersTable() void
        +setupCategoriesTable() void
        +loadProducts() void
        +loadOrders() void
        +loadUsers() void
        +loadCategories() void
        +handleProductSearch() void
        +handleOrderStatusFilter() void
        +handleUserSearch() void
        +handleAddProduct() void
        +handleEditProduct(Product product) void
        +handleDeleteProduct(Product product) void
        +handleAddCategory() void
        +handleEditCategory(Category category) void
        +handleDeleteCategory(Category category) void
        +handleUpdateOrderStatus(Order order) void
        +handleViewOrderDetails(Order order) void
        +handleEditUser(User user) void
        +handleToggleUserStatus(User user) void
        +handleLogout() void
        +handleProfileUpdate() void
        -showError(String message) void
        -showSuccess(String message) void
        -refreshData() void
    }

    class CustomerDashboardController {
        -TextField searchField
        -ComboBox~Category~ categoryComboBox
        -GridPane productsGrid
        -VBox cartItemsContainer
        -Label totalItemsLabel
        -Label totalAmountLabel
        -Button cartButton
        -MenuButton userMenuButton
        -TabPane mainTabPane
        -TableView~Order~ ordersTable
        -ProductDAO productDAO
        -CategoryDAO categoryDAO
        -OrderDAO orderDAO
        -UserDAO userDAO
        -User currentUser
        -Map~Integer, Integer~ cart
        -ObservableList~Order~ orders
        +initialize() void
        +setupUserMenu() void
        +setupCategoryComboBox() void
        +setupOrdersTable() void
        +loadProducts() void
        +loadUserProfile() void
        +loadUserOrders() void
        +handleProductSearch() void
        +handleCategoryFilter() void
        +handleAddToCart(Product product) void
        +handleRemoveFromCart(int productId) void
        +handleUpdateQuantity(int productId, int quantity) void
        +handleCheckout() void
        +handleViewOrderDetails(Order order) void
        +handleProfileUpdate() void
        +handleLogout() void
        -showError(String message) void
        -showSuccess(String message) void
        -updateCartDisplay() void
        -calculateTotal() BigDecimal
        -refreshProducts() void
    }

    class ProductDialogController {
        -TextField nameField
        -TextArea descriptionField
        -TextField priceField
        -TextField stockField
        -ComboBox~Category~ categoryComboBox
        -TextField imageUrlField
        -ComboBox~String~ pricingUnitComboBox
        -Product product
        -ProductDAO productDAO
        -CategoryDAO categoryDAO
        +initialize() void
        +setProduct(Product product) void
        +handleSave() void
        +handleCancel() void
        -loadCategories() void
        -validateInput() boolean
        -showError(String message) void
    }

    class CategoryDialogController {
        -TextField nameField
        -TextArea descriptionField
        -TextField imageUrlField
        -Category category
        -CategoryDAO categoryDAO
        +initialize() void
        +setCategory(Category category) void
        +handleSave() void
        +handleCancel() void
        -validateInput() boolean
        -showError(String message) void
    }

    class UserDialogController {
        -TextField firstNameField
        -TextField lastNameField
        -TextField emailField
        -TextField phoneField
        -TextArea addressField
        -TextField cityField
        -TextField stateField
        -TextField zipCodeField
        -TextField countryField
        -ComboBox~String~ roleComboBox
        -User user
        -UserDAO userDAO
        +initialize() void
        +setUser(User user) void
        +handleSave() void
        +handleCancel() void
        -validateInput() boolean
        -showError(String message) void
    }

    %% Utility Classes
    class DatabaseUtil {
        -String CONFIG_FILE
        -String DEFAULT_DB_FILE
        -Properties properties
        +getConnection() Connection
        +closeConnection(Connection connection) void
        +closeAutoCloseable(AutoCloseable closeable) void
        +getDatabaseUrl() String
        +getDatabaseUsername() String
        +getDatabasePassword() String
        +getDatabaseFile() String
        +databaseExists() boolean
        +initializeDatabase() void
        -loadProperties() void
    }

    %% Relationships
    ECommerceApp --> User : manages current user
    ECommerceApp --> DatabaseUtil : uses

    %% Model Relationships
    Order ||--o{ OrderItem : contains
    Order }o--|| User : belongs to
    Product }o--|| Category : belongs to

    %% DAO Relationships
    UserDAO --> User : manages
    ProductDAO --> Product : manages
    CategoryDAO --> Category : manages
    OrderDAO --> Order : manages
    OrderItemDAO --> OrderItem : manages

    %% Controller Relationships
    LoginController --> UserDAO : uses
    LoginController --> User : manages
    RegisterController --> UserDAO : uses
    RegisterController --> User : creates
    AdminDashboardController --> ProductDAO : uses
    AdminDashboardController --> CategoryDAO : uses
    AdminDashboardController --> OrderDAO : uses
    AdminDashboardController --> UserDAO : uses
    AdminDashboardController --> Product : manages
    AdminDashboardController --> Category : manages
    AdminDashboardController --> Order : manages
    AdminDashboardController --> User : manages
    CustomerDashboardController --> ProductDAO : uses
    CustomerDashboardController --> CategoryDAO : uses
    CustomerDashboardController --> OrderDAO : uses
    CustomerDashboardController --> UserDAO : uses
    CustomerDashboardController --> Product : displays
    CustomerDashboardController --> Category : filters by
    CustomerDashboardController --> Order : manages
    CustomerDashboardController --> User : manages
    ProductDialogController --> ProductDAO : uses
    ProductDialogController --> CategoryDAO : uses
    ProductDialogController --> Product : edits
    ProductDialogController --> Category : selects
    CategoryDialogController --> CategoryDAO : uses
    CategoryDialogController --> Category : edits
    UserDialogController --> UserDAO : uses
    UserDialogController --> User : edits

    %% DAO to DatabaseUtil
    UserDAO --> DatabaseUtil : uses
    ProductDAO --> DatabaseUtil : uses
    CategoryDAO --> DatabaseUtil : uses
    OrderDAO --> DatabaseUtil : uses
    OrderItemDAO --> DatabaseUtil : uses
```

## Class Diagram Description

This UML Class Diagram represents the complete architecture of the E-Commerce JavaFX application with the following key components:

### **Core Architecture Layers:**

1. **Presentation Layer (Controllers)**
   - JavaFX controllers handling UI interactions
   - Separate controllers for login, registration, admin dashboard, and customer dashboard
   - Dialog controllers for CRUD operations

2. **Business Logic Layer (DAOs)**
   - Data Access Objects for database operations
   - CRUD operations for all entities
   - Search and filtering capabilities

3. **Data Layer (Models)**
   - Entity classes representing database tables
   - Rich domain models with business logic methods
   - Relationships between entities

4. **Utility Layer**
   - Database connection management
   - Configuration handling

### **Key Features:**

- **User Management**: Registration, login, role-based access (Admin/Customer)
- **Product Management**: CRUD operations, category filtering, search
- **Order Management**: Order creation, status tracking, order items
- **Category Management**: Product categorization
- **Shopping Cart**: Add/remove items, quantity management
- **Profile Management**: User profile updates

### **Technology Stack:**
- **Frontend**: JavaFX with FXML
- **Database**: SQLite (with MySQL support)
- **Build Tool**: Maven
- **Architecture**: MVC pattern with DAO layer

The diagram shows clear separation of concerns and follows object-oriented design principles with proper encapsulation, inheritance, and relationships between classes.
