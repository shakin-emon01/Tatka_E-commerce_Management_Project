# E-Commerce Project UML Class Diagram (ASCII Format)

+------------------+          +------------------+
|   ECommerceApp   |          |   DatabaseUtil   |
|------------------|          |------------------|
| -primaryStage    |          | -CONFIG_FILE     |
| -currentUser     |          | -DEFAULT_DB_FILE |
|------------------|          | -properties      |
| +start()         |          |------------------|
| +getCurrentUser()|          | +getConnection() |
| +setCurrentUser()|          | +closeConnection()|
| +getPrimaryStage()|         | +getDatabaseUrl()|
+------------------+          | +initializeDB()  |
        |                     +------------------+
        v
+------------------+
|      User        |
|------------------|
| -id              |
| -email           |
| -password        |
| -firstName       |
| -lastName        |
| -phone           |
| -address         |
| -city            |
| -state           |
| -zipCode         |
| -country         |
| -role            |
| -createdAt       |
| -updatedAt       |
| -active          |
|------------------|
| +getFullName()   |
| +isAdmin()       |
| +isCustomer()    |
| +setAdmin()      |
+------------------+

+------------------+     +------------------+     +------------------+
|     Product      |     |     Category     |     |      Order       |
|------------------|     |------------------|     |------------------|
| -id              |     | -id              |     | -id              |
| -name            |     | -name            |     | -userId          |
| -description     |     | -description     |     | -totalAmount     |
| -price           |     | -imageUrl        |     | -status          |
| -stockQuantity   |     | -createdAt       |     | -shippingAddress |
| -categoryId      |     |------------------|     | -shippingCity    |
| -imageUrl        |     | +getName()       |     | -shippingState   |
| -isActive        |     | +getDescription()|     | -shippingZipCode |
| -createdAt       |     | +getImageUrl()   |     | -shippingCountry |
| -updatedAt       |     +------------------+     | -paymentMethod   |
| -categoryName    |                              | -paymentStatus   |
| -pricingUnit     |                              | -createdAt       |
|------------------|                              | -updatedAt       |
| +getPrice()      |                              | -customerName    |
| +isInStock()     |                              | -customerEmail   |
| +isLowStock()    |                              | -orderItems[]    |
| +getStockStatus()|                              |------------------|
| +getFormattedPrice()|                           | +getStatus()     |
| +getPricingUnitDisplay()|                       | +isPending()     |
+------------------+                              | +isConfirmed()   |
         |                                        | +isShipped()     |
         v                                        | +isDelivered()   |
+------------------+                              | +isCancelled()   |
|   OrderItem      |                              | +isPaid()        |
|------------------|                              +------------------+
| -id              |                                        |
| -orderId         |                                        |
| -productId       |                                        |
| -quantity        |                                        |
| -price           |                                        |
| -productName     |                                        |
| -productImageUrl |                                        |
|------------------|                                        |
| +getSubtotal()   |                                        |
| +getFormattedPrice()|                                     |
| +getFormattedSubtotal()|                                   |
+------------------+                                        |
                                                            |
                                                            v
+------------------+     +------------------+     +------------------+
|     UserDAO      |     |   ProductDAO     |     |    OrderDAO      |
|------------------|     |------------------|     |------------------|
| +findByEmail()   |     | +findAll()       |     | +findAll()       |
| +findById()      |     | +findByCategory()|     | +findByUserId()  |
| +create()        |     | +findById()      |     | +findRecent()    |
| +update()        |     | +searchByName()  |     | +findById()      |
| +delete()        |     | +save()          |     | +save()          |
| +findAll()       |     | +delete()        |     | +updateStatus()  |
| +findByRole()    |     | +updateStock()   |     | +updatePaymentStatus()|
| +searchByName()  |     | -insert()        |     | +getTotalRevenue()|
| -mapResultSetToUser()| | -update()        |     | +getOrderCount() |
+------------------+     | -mapResultSetToProduct()| -insert()        |
                         +------------------+     | -update()        |
                                                   | -mapResultSetToOrder()|
                                                   +------------------+
                                                           |
                                                           v
+------------------+     +------------------+
|   CategoryDAO    |     |  OrderItemDAO    |
|------------------|     |------------------|
| +findAll()       |     | +findByOrderId() |
| +findById()      |     | +save()          |
| +save()          |     | +deleteByOrderId()|
| +delete()        |     | -insert()        |
| +getProductCount()|    | -mapResultSetToOrderItem()|
| -insert()        |     +------------------+
| -update()        |
| -mapResultSetToCategory()|
+------------------+

Controllers (UI Layer)
+----------------------+     +----------------------+     +----------------------+
|  LoginController     |     | RegisterController   |     | ProductDialogCtrl    |
|----------------------|     |----------------------|     |----------------------|
| -emailField          |     | -firstNameField      |     | -nameField           |
| -passwordField       |     | -lastNameField       |     | -descriptionField   |
| -rememberMeCheckBox  |     | -emailField          |     | -priceField          |
| -errorLabel          |     | -passwordField       |     | -stockField          |
| -userDAO             |     | -confirmPasswordField|     | -categoryComboBox    |
|----------------------|     | -phoneField          |     | -imageUrlField       |
| +initialize()        |     | -errorLabel          |     | -pricingUnitComboBox |
| +handleLogin()       |     | -userDAO             |     | -product             |
| +handleRegister()    |     |----------------------|     | -productDAO          |
| -showError()         |     | +initialize()        |     | -categoryDAO         |
| -isValidEmail()      |     | +handleRegister()    |     |----------------------|
| -navigateToDashboard()|    | +handleBackToLogin() |     | +initialize()        |
+----------------------+     | -showError()         |     | +setProduct()        |
                             | -isValidEmail()      |     | +handleSave()        |
                             | -isValidPassword()   |     | +handleCancel()      |
                             | -passwordsMatch()    |     | -loadCategories()    |
                             +----------------------+     | -validateInput()     |
                                                          | -showError()         |
                                                          +----------------------+

+----------------------+     +----------------------+     +----------------------+
| CategoryDialogCtrl   |     |   UserDialogCtrl     |     | CustomerDashboardCtrl|
|----------------------|     |----------------------|     |----------------------|
| -nameField           |     | -firstNameField      |     | -searchField         |
| -descriptionField    |     | -lastNameField       |     | -categoryComboBox    |
| -imageUrlField       |     | -emailField          |     | -productsGrid        |
| -category            |     | -phoneField          |     | -cartItemsContainer  |
| -categoryDAO         |     | -addressField        |     | -totalItemsLabel     |
|----------------------|     | -cityField           |     | -totalAmountLabel    |
| +initialize()        |     | -stateField          |     | -cartButton          |
| +setCategory()       |     | -zipCodeField        |     | -userMenuButton      |
| +handleSave()        |     | -countryField        |     | -mainTabPane         |
| +handleCancel()      |     | -roleComboBox        |     | -ordersTable         |
| -validateInput()     |     | -user                |     | -productDAO          |
| -showError()         |     | -userDAO             |     | -categoryDAO         |
+----------------------+     |----------------------|     | -orderDAO            |
                             | +initialize()        |     | -userDAO             |
                             | +setUser()           |     | -currentUser         |
                             | +handleSave()        |     | -cart                |
                             | +handleCancel()      |     | -orders              |
                             | -validateInput()     |     |----------------------|
                             | -showError()         |     | +initialize()        |
                             +----------------------+     | +setupUserMenu()     |
                                                          | +setupCategoryComboBox()|
                                                          | +setupOrdersTable()  |
                                                          | +loadProducts()      |
                                                          | +loadUserProfile()   |
                                                          | +loadUserOrders()    |
                                                          | +handleProductSearch()|
                                                          | +handleCategoryFilter()|
                                                          | +handleAddToCart()   |
                                                          | +handleRemoveFromCart()|
                                                          | +handleUpdateQuantity()|
                                                          | +handleCheckout()    |
                                                          | +handleViewOrderDetails()|
                                                          | +handleProfileUpdate()|
                                                          | +handleLogout()      |
                                                          | -showError()         |
                                                          | -showSuccess()       |
                                                          | -updateCartDisplay() |
                                                          | -calculateTotal()    |
                                                          | -refreshProducts()   |
                                                          +----------------------+

+----------------------+
| AdminDashboardCtrl   |
|----------------------|
| -mainTabPane         |
| -userMenuButton      |
| -statusLabel         |
| -totalProductsLabel  |
| -totalOrdersLabel    |
| -totalUsersLabel     |
| -totalRevenueLabel   |
| -recentOrdersTable   |
| -productsTable       |
| -ordersTable         |
| -usersTable          |
| -categoriesTable     |
| -productDAO          |
| -categoryDAO         |
| -orderDAO            |
| -userDAO             |
| -currentUser         |
| -products            |
| -orders              |
| -users               |
| -categories          |
|----------------------|
| +initialize()        |
| +setupDashboard()    |
| +setupProductsTable()|
| +setupOrdersTable()  |
| +setupUsersTable()   |
| +setupCategoriesTable()|
| +loadProducts()      |
| +loadOrders()        |
| +loadUsers()         |
| +loadCategories()    |
| +handleProductSearch()|
| +handleOrderStatusFilter()|
| +handleUserSearch()  |
| +handleAddProduct()  |
| +handleEditProduct() |
| +handleDeleteProduct()|
| +handleAddCategory() |
| +handleEditCategory()|
| +handleDeleteCategory()|
| +handleUpdateOrderStatus()|
| +handleViewOrderDetails()|
| +handleEditUser()    |
| +handleToggleUserStatus()|
| +handleLogout()      |
| +handleProfileUpdate()|
| -showError()         |
| -showSuccess()       |
| -refreshData()       |
+----------------------+

Relationships:
ECommerceApp --> User (manages current user)
ECommerceApp --> DatabaseUtil (uses)

UserDAO --> User (manages)
ProductDAO --> Product (manages)
CategoryDAO --> Category (manages)
OrderDAO --> Order (manages)
OrderItemDAO --> OrderItem (manages)

LoginController --> UserDAO (uses)
LoginController --> User (manages)
RegisterController --> UserDAO (uses)
RegisterController --> User (creates)

AdminDashboardController --> ProductDAO (uses)
AdminDashboardController --> CategoryDAO (uses)
AdminDashboardController --> OrderDAO (uses)
AdminDashboardController --> UserDAO (uses)
AdminDashboardController --> Product (manages)
AdminDashboardController --> Category (manages)
AdminDashboardController --> Order (manages)
AdminDashboardController --> User (manages)

CustomerDashboardController --> ProductDAO (uses)
CustomerDashboardController --> CategoryDAO (uses)
CustomerDashboardController --> OrderDAO (uses)
CustomerDashboardController --> UserDAO (uses)
CustomerDashboardController --> Product (displays)
CustomerDashboardController --> Category (filters by)
CustomerDashboardController --> Order (manages)
CustomerDashboardController --> User (manages)

ProductDialogController --> ProductDAO (uses)
ProductDialogController --> CategoryDAO (uses)
ProductDialogController --> Product (edits)
ProductDialogController --> Category (selects)

CategoryDialogController --> CategoryDAO (uses)
CategoryDialogController --> Category (edits)

UserDialogController --> UserDAO (uses)
UserDialogController --> User (edits)

UserDAO --> DatabaseUtil (uses)
ProductDAO --> DatabaseUtil (uses)
CategoryDAO --> DatabaseUtil (uses)
OrderDAO --> DatabaseUtil (uses)
OrderItemDAO --> DatabaseUtil (uses)



# The diagram shows clear separation of concerns and follows object-oriented design principles with proper encapsulation, inheritance, and relationships between classes.
