package com.ecommerce.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.ecommerce.dao.*;
import com.ecommerce.model.*;
import com.ecommerce.ECommerceApp;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CustomerDashboardController {

    @FXML private TextField searchField;
    @FXML private ComboBox<Category> categoryComboBox;
    @FXML private GridPane productsGrid;
    @FXML private VBox cartItemsContainer;
    @FXML private Label totalItemsLabel;
    @FXML private Label totalAmountLabel;
    @FXML private Button cartButton;
    @FXML private MenuButton userMenuButton;
    @FXML private TabPane mainTabPane;
    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Integer> orderIdColumn;
    @FXML private TableColumn<Order, String> orderDateColumn;
    @FXML private TableColumn<Order, BigDecimal> orderTotalColumn;
    @FXML private TableColumn<Order, String> orderStatusColumn;
    @FXML private TableColumn<Order, Void> orderActionsColumn;
    @FXML private Label statusLabel;
    
    // Profile fields
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextArea addressField;
    @FXML private TextField cityField;
    @FXML private TextField districtField;
    @FXML private TextField zipCodeField;
    @FXML private TextField countryField;

    @FXML private VBox categoriesContainer;

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private User currentUser;
    private Map<Integer, Integer> cart; // productId -> quantity
    private ObservableList<Order> orders;

    @FXML
    public void initialize() {
        try {
            System.out.println("Starting dashboard initialization...");
            
            currentUser = ECommerceApp.getCurrentUser();
            if (currentUser == null) {
                showError("No user session found. Please login again.");
                return;
            }
            System.out.println("Current user: " + currentUser.getEmail());
            
            cart = new HashMap<>();
            
            // Initialize DAOs
            System.out.println("Initializing DAOs...");
            productDAO = new ProductDAO();
            categoryDAO = new CategoryDAO();
            orderDAO = new OrderDAO();
            userDAO = new UserDAO();
            System.out.println("DAOs initialized successfully");
            
            // Setup UI
            System.out.println("Setting up user menu...");
            setupUserMenu();
            System.out.println("Setting up category combo box...");
            setupCategoryComboBox();
            System.out.println("Setting up orders table...");
            setupOrdersTable();
            System.out.println("Loading products...");
            loadProducts();
            System.out.println("Loading user profile...");
            loadUserProfile();
            System.out.println("Loading user orders...");
            loadUserOrders();
            
            // Update cart button
            System.out.println("Updating cart button...");
            updateCartButton();
            
            System.out.println("Dashboard initialization completed successfully");
            
        } catch (Exception e) {
            System.err.println("Dashboard initialization failed: " + e.getMessage());
            e.printStackTrace();
            showError("Failed to initialize dashboard: " + e.getMessage());
        }
    }

    private void setupUserMenu() {
        if (currentUser != null) {
            userMenuButton.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        } else {
            userMenuButton.setText("User");
        }
        
        // Add menu items programmatically to ensure they work
        if (userMenuButton.getItems().isEmpty()) {
            MenuItem profile = new MenuItem("My Profile");
            profile.setOnAction(e -> showProfile());
            MenuItem orders = new MenuItem("My Orders");
            orders.setOnAction(e -> showOrders());
            MenuItem logout = new MenuItem("Logout");
            logout.setOnAction(e -> handleLogout());
            userMenuButton.getItems().addAll(profile, orders, new SeparatorMenuItem(), logout);
        }
        
        // Ensure MenuButton is properly configured
        userMenuButton.setDisable(false);
        userMenuButton.setVisible(true);
        
        // Add debug information
        System.out.println("Customer MenuButton setup completed:");
        System.out.println("  - Text: " + userMenuButton.getText());
        System.out.println("  - Style classes: " + userMenuButton.getStyleClass());
        System.out.println("  - Items count: " + userMenuButton.getItems().size());
        System.out.println("  - Is showing: " + userMenuButton.isShowing());
        System.out.println("  - Is disabled: " + userMenuButton.isDisabled());
        System.out.println("  - Is visible: " + userMenuButton.isVisible());
        
        // Add a test event handler to verify the MenuButton is working
        userMenuButton.setOnShowing(e -> {
            System.out.println("Customer MenuButton dropdown is showing!");
        });
        
        userMenuButton.setOnHidden(e -> {
            System.out.println("Customer MenuButton dropdown is hidden!");
        });
    }

    private void setupCategoryComboBox() {
        try {
            List<Category> categories = categoryDAO.findAll();
            categoryComboBox.setItems(FXCollections.observableArrayList(categories));
        } catch (Exception e) {
            System.err.println("Error setting up category combo box: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void setupOrdersTable() {
        orders = FXCollections.observableArrayList();
        ordersTable.setItems(orders);
        
        orderIdColumn.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        orderDateColumn.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getCreatedAt().toString()));
        orderTotalColumn.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getTotalAmount()));
        orderStatusColumn.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        
        // Add view details button to actions column
        orderActionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button("View Details");
            {
                viewButton.setOnAction(event -> {
                    Order order = getTableView().getItems().get(getIndex());
                    viewOrderDetails(order);
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewButton);
                }
            }
        });
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            List<Product> products = productDAO.searchByName(searchTerm);
            displayProducts(products);
            statusLabel.setText("Search results for: " + searchTerm);
        }
    }

    @FXML
    private void handleCategoryFilter() {
        Category selectedCategory = categoryComboBox.getValue();
        if (selectedCategory != null) {
            List<Product> products = productDAO.findByCategory(selectedCategory.getId());
            displayProducts(products);
            statusLabel.setText("Filtered by category: " + selectedCategory.getName());
        }
    }

    @FXML
    private void clearFilters() {
        categoryComboBox.setValue(null);
        searchField.clear();
        loadProducts();
        statusLabel.setText("Filters cleared");
    }

    private void loadProducts() {
        try {
            List<Product> products = productDAO.findAll();
            displayProducts(products);
            statusLabel.setText("Loaded " + products.size() + " products");
        } catch (Exception e) {
            System.err.println("Error loading products: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void displayProducts(List<Product> products) {
        productsGrid.getChildren().clear();
        
        int col = 0;
        int row = 0;
        int maxCols = 4;
        
        for (Product product : products) {
            VBox productCard = createProductCard(product);
            productsGrid.add(productCard, col, row);
            
            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createProductCard(Product product) {
        VBox card = new VBox(10);
        card.getStyleClass().addAll("product-card");
        card.setPrefWidth(250);
        card.setPrefHeight(350);
        
        // Product image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        
        // Try to load product image using imageUrl, fallback to placeholder
        try {
            String imageUrl = product.getImageUrl();
            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                Image image = new Image(imageUrl, 200, 150, true, true);
                imageView.setImage(image);
            } else {
                Image placeholder = new Image(getClass().getResourceAsStream("/images/placeholder.jpg"));
                imageView.setImage(placeholder);
            }
        } catch (Exception e) {
            Image placeholder = new Image(getClass().getResourceAsStream("/images/placeholder.jpg"));
            imageView.setImage(placeholder);
        }
        
        // Product info
        Label nameLabel = new Label(product.getName());
        nameLabel.getStyleClass().add("label-subtitle");
        nameLabel.setWrapText(true);
        
        Label priceLabel = new Label("৳" + product.getPrice());
        priceLabel.getStyleClass().add("label-title");
        
        Label pricingUnitLabel = new Label(product.getPricingUnitDisplay());
        pricingUnitLabel.getStyleClass().add("label-muted");
        pricingUnitLabel.setStyle("-fx-font-size: 10px;");
        
        Label stockLabel = new Label(product.getStockStatus());
        stockLabel.getStyleClass().add(product.isInStock() ? "status-success" : "status-danger");
        
        // Add to cart button
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.getStyleClass().add("button");
        addToCartButton.setDisable(!product.isInStock());
        addToCartButton.setOnAction(e -> addToCart(product));
        
        card.getChildren().addAll(imageView, nameLabel, priceLabel, pricingUnitLabel, stockLabel, addToCartButton);
        card.setAlignment(javafx.geometry.Pos.CENTER);
        
        return card;
    }

    private void addToCart(Product product) {
        int currentQuantity = cart.getOrDefault(product.getId(), 0);
        cart.put(product.getId(), currentQuantity + 1);
        
        updateCartButton();
        updateCartDisplay();
        
        statusLabel.setText("Added " + product.getName() + " to cart");
        
        // Show confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Added to Cart");
        alert.setHeaderText(null);
        alert.setContentText(product.getName() + " has been added to your cart!");
        alert.showAndWait();
    }

    private void updateCartButton() {
        int totalItems = cart.values().stream().mapToInt(Integer::intValue).sum();
        cartButton.setText("Cart (" + totalItems + ")");
    }

    private void updateCartDisplay() {
        cartItemsContainer.getChildren().clear();
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalItems = 0;
        
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Product product = productDAO.findById(entry.getKey());
            if (product != null) {
                int quantity = entry.getValue();
                BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
                totalAmount = totalAmount.add(itemTotal);
                totalItems += quantity;
                
                HBox cartItem = createCartItem(product, quantity);
                cartItemsContainer.getChildren().add(cartItem);
            }
        }
        
        totalItemsLabel.setText(String.valueOf(totalItems));
        totalAmountLabel.setText("৳" + totalAmount);
    }

    private HBox createCartItem(Product product, int quantity) {
        HBox item = new HBox(15);
        item.getStyleClass().add("card");
        item.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        // Product image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        imageView.setPreserveRatio(true);
        
        // Try to load product image using imageUrl, fallback to placeholder
        try {
            String imageUrl = product.getImageUrl();
            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                Image image = new Image(imageUrl, 60, 60, true, true);
                imageView.setImage(image);
            } else {
                Image placeholder = new Image(getClass().getResourceAsStream("/images/placeholder.jpg"));
                imageView.setImage(placeholder);
            }
        } catch (Exception e) {
            Image placeholder = new Image(getClass().getResourceAsStream("/images/placeholder.jpg"));
            imageView.setImage(placeholder);
        }
        
        // Product info
        VBox info = new VBox(5);
        Label nameLabel = new Label(product.getName());
        nameLabel.getStyleClass().add("label-subtitle");
        Label priceLabel = new Label("৳" + product.getPrice());
        
        Label pricingUnitLabel = new Label(product.getPricingUnitDisplay());
        pricingUnitLabel.getStyleClass().add("label-muted");
        pricingUnitLabel.setStyle("-fx-font-size: 10px;");
        
        info.getChildren().addAll(nameLabel, priceLabel);
        info.getChildren().add(pricingUnitLabel);
        
        // Quantity controls
        HBox quantityBox = new HBox(10);
        Button decreaseBtn = new Button("-");
        Button increaseBtn = new Button("+");
        Label quantityLabel = new Label(String.valueOf(quantity));
        
        decreaseBtn.setOnAction(e -> updateQuantity(product.getId(), quantity - 1));
        increaseBtn.setOnAction(e -> updateQuantity(product.getId(), quantity + 1));
        
        quantityBox.getChildren().addAll(decreaseBtn, quantityLabel, increaseBtn);
        quantityBox.setAlignment(javafx.geometry.Pos.CENTER);
        
        // Remove button
        Button removeBtn = new Button("Remove");
        removeBtn.getStyleClass().add("button-danger");
        removeBtn.setOnAction(e -> removeFromCart(product.getId()));
        
        item.getChildren().addAll(imageView, info, quantityBox, removeBtn);
        
        return item;
    }

    private void updateQuantity(int productId, int newQuantity) {
        if (newQuantity <= 0) {
            cart.remove(productId);
        } else {
            cart.put(productId, newQuantity);
        }
        
        updateCartButton();
        updateCartDisplay();
    }

    private void removeFromCart(int productId) {
        cart.remove(productId);
        updateCartButton();
        updateCartDisplay();
    }

    @FXML
    private void clearCart() {
        cart.clear();
        updateCartButton();
        updateCartDisplay();
        statusLabel.setText("Cart cleared");
    }

    @FXML
    private void proceedToCheckout() {
        try {
            if (cart.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty Cart");
                alert.setHeaderText(null);
                alert.setContentText("Your cart is empty. Please add some products first.");
                alert.showAndWait();
                return;
            }

            // Collect shipping info from profile fields
            String address = addressField.getText() != null ? addressField.getText().trim() : "";
            String city = cityField.getText() != null ? cityField.getText().trim() : "";
            String state = districtField.getText() != null ? districtField.getText().trim() : "";
            String zipCode = zipCodeField.getText() != null ? zipCodeField.getText().trim() : "";
            String country = countryField.getText() != null ? countryField.getText().trim() : "";
            String paymentMethod = "CASH_ON_DELIVERY";

            // Calculate total amount
            java.math.BigDecimal totalAmount = java.math.BigDecimal.ZERO;
            for (Integer productId : cart.keySet()) {
                com.ecommerce.model.Product product = productDAO.findById(productId);
                if (product == null) {
                    showError("Product not found for ID: " + productId);
                    return;
                }
                int quantity = cart.get(productId);
                java.math.BigDecimal price = product.getPrice();
                totalAmount = totalAmount.add(price.multiply(java.math.BigDecimal.valueOf(quantity)));
            }

            // Create order object (without order items)
            com.ecommerce.model.Order order = new com.ecommerce.model.Order(
                currentUser.getId(),
                totalAmount,
                address,
                city,
                state,
                zipCode,
                country,
                paymentMethod
            );

            // Save order to get generated order ID
            boolean orderCreated = orderDAO.create(order);
            if (!orderCreated) {
                // In some environments the order may still be created; try to recover by checking recent order for the user
                Order latest = orderDAO.findLatestByUserId(currentUser.getId());
                if (latest != null && latest.getTotalAmount().compareTo(totalAmount) == 0) {
                    order = latest;
                } else {
                    showError("Failed to place order. Please try again.");
                    return;
                }
            }

            // Now create order items with the correct orderId
            java.util.List<com.ecommerce.model.OrderItem> orderItems = new java.util.ArrayList<>();
            for (Integer productId : cart.keySet()) {
                com.ecommerce.model.Product product = productDAO.findById(productId);
                if (product == null) {
                    showError("Product not found for ID: " + productId);
                    return;
                }
                int quantity = cart.get(productId);
                java.math.BigDecimal price = product.getPrice();
                orderItems.add(new com.ecommerce.model.OrderItem(order.getId(), productId, quantity, price));
            }
            com.ecommerce.dao.OrderItemDAO orderItemDAO = new com.ecommerce.dao.OrderItemDAO();
            boolean itemsCreated = orderItemDAO.createAll(orderItems);
            if (!itemsCreated) {
                System.err.println("Warning: Some order items may not have been saved for order ID: " + order.getId());
                // Proceed as success since the order is created and visible in history
            }

            cart.clear();
            updateCartButton();
            updateCartDisplay();
            loadUserOrders();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Successfull");
            alert.setHeaderText(null);
            alert.setContentText("Order Successfull");
            alert.showAndWait();

            // Switch to My Orders tab (ensure correct index)
            showOrders();

            System.out.println("Order placed successfully. Order ID: " + order.getId());

        } catch (Exception e) {
            e.printStackTrace();
            showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void loadUserProfile() {
        firstNameField.setText(currentUser.getFirstName());
        lastNameField.setText(currentUser.getLastName());
        emailField.setText(currentUser.getEmail());
        phoneField.setText(currentUser.getPhone());
        addressField.setText(currentUser.getAddress());
        cityField.setText(currentUser.getCity());
        districtField.setText(currentUser.getState());
        zipCodeField.setText(currentUser.getZipCode());
        if (countryField != null) {
            String countryValue = currentUser.getCountry();
            if (countryValue == null || countryValue.isBlank() || countryValue.equalsIgnoreCase("USA")) {
                countryValue = "Bangladesh";
            }
            countryField.setText(countryValue);
        }
    }

    @FXML
    private void updateProfile() {
        // Collect updated values
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String city = cityField.getText().trim();
        String state = districtField.getText().trim();
        String zipCode = zipCodeField.getText().trim();
        String country = countryField.getText().trim();

        // Basic validation
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            showError("First name, last name, and email are required.");
            return;
        }
        if (!email.matches("^.+@.+\\..+$")) {
            showError("Please enter a valid email address.");
            return;
        }

        // Update currentUser object
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);
        currentUser.setCity(city);
        currentUser.setState(state);
        currentUser.setZipCode(zipCode);
        currentUser.setCountry(country);

        // Persist changes
        boolean success = userDAO.update(currentUser);
        if (success) {
            // Reload user from DB and update session
            User updatedUser = userDAO.findById(currentUser.getId());
            if (updatedUser != null) {
                currentUser = updatedUser;
                com.ecommerce.ECommerceApp.setCurrentUser(currentUser);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Profile Updated");
            alert.setHeaderText(null);
            alert.setContentText("Profile update successful");
            alert.showAndWait();
            loadUserProfile(); // Refresh fields
        } else {
            showError("Failed to update profile. Please try again later.");
        }
    }

    @FXML
    private void changePassword() {
        // TODO: Implement password change
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Change Password");
        alert.setHeaderText(null);
        alert.setContentText("Password change functionality will be implemented in the next version.");
        alert.showAndWait();
    }

    private void loadUserOrders() {
        try {
            List<Order> userOrders = orderDAO.findByUserId(currentUser.getId());
            orders.clear();
            orders.addAll(userOrders);
        } catch (Exception e) {
            System.err.println("Error loading user orders: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void viewOrderDetails(Order order) {
        try {
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            List<OrderItem> items = orderItemDAO.findByOrderId(order.getId());

            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Order Details");
            dialog.setHeaderText("Order #" + order.getId());

            ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(closeButtonType);

            VBox content = new VBox(10);
            content.setPadding(new javafx.geometry.Insets(10));

            // Summary
            Label summary = new Label("Status: " + order.getStatus() +
                    "    |    Total: ৳" + order.getTotalAmount() +
                    "    |    Created: " + (order.getCreatedAt() != null ? order.getCreatedAt().toString() : "N/A"));
            summary.getStyleClass().add("label-subtitle");
            content.getChildren().add(summary);

            // Table of items
            TableView<OrderItem> table = new TableView<>();
            table.setItems(FXCollections.observableArrayList(items));

            TableColumn<OrderItem, String> nameCol = new TableColumn<>("Product");
            nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                    data.getValue().getProductName() != null ? data.getValue().getProductName() : ("#" + data.getValue().getProductId())));

            TableColumn<OrderItem, Number> qtyCol = new TableColumn<>("Qty");
            qtyCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getQuantity()));

            TableColumn<OrderItem, String> priceCol = new TableColumn<>("Unit Price");
            priceCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("৳" + data.getValue().getPrice()));

            TableColumn<OrderItem, String> subtotalCol = new TableColumn<>("Subtotal");
            subtotalCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("৳" + data.getValue().getSubtotal()));

            table.getColumns().addAll(nameCol, qtyCol, priceCol, subtotalCol);
            table.setPrefHeight(200);
            content.getChildren().add(table);

            // Shipping info
            VBox shipping = new VBox(4);
            shipping.getChildren().addAll(
                    new Label("Shipping Address:"),
                    new Label(order.getFullShippingAddress())
            );
            content.getChildren().add(shipping);

            dialog.getDialogPane().setContent(content);
            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to load order details: " + e.getMessage());
        }
    }

    @FXML
    private void showProducts() {
        mainTabPane.getSelectionModel().select(0);
    }

    @FXML
    private void showCategories() {
        mainTabPane.getSelectionModel().select(1); // Categories tab index (after Products)
        try {
            List<Category> categories = categoryDAO.findAll();
            displayCategories(categories);
            statusLabel.setText("Loaded " + categories.size() + " categories");
        } catch (Exception e) {
            System.err.println("Error loading categories: " + e.getMessage());
            e.printStackTrace();
            showError("Failed to load categories: " + e.getMessage());
        }
    }

    @FXML
    private void showOrders() {
        mainTabPane.getSelectionModel().select(3); // My Orders tab
    }

    @FXML
    private void showProfile() {
        mainTabPane.getSelectionModel().select(4); // Profile tab
    }

    @FXML
    private void showCart() {
        mainTabPane.getSelectionModel().select(2); // Shopping Cart tab
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        
        if (alert.showAndWait().orElse(null) == ButtonType.OK) {
            try {
                // Clear the current user session
                ECommerceApp.setCurrentUser(null);
                
                // Clear cart
                if (cart != null) {
                    cart.clear();
                }
                
                // Load login screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
                Parent root = loader.load();
                
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                
                Stage stage = (Stage) mainTabPane.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Tatka - Login");
                
                System.out.println("User logged out successfully");
                
            } catch (IOException e) {
                System.err.println("Error during logout: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayCategories(List<Category> categories) {
        categoriesContainer.getChildren().clear();
        for (Category category : categories) {
            VBox card = new VBox(8);
            card.getStyleClass().add("category-card");
            card.setPrefWidth(400);
            card.setPadding(new javafx.geometry.Insets(10));

            Hyperlink nameLink = new Hyperlink(category.getName());
            nameLink.getStyleClass().add("label-title");
            nameLink.setOnAction(e -> showProductsByCategory(category));

            Label descLabel = new Label(category.getDescription() != null ? category.getDescription() : "");
            descLabel.getStyleClass().add("label-subtitle");
            descLabel.setWrapText(true);

            // Image (if available)
            javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);
            try {
                String imageUrl = category.getImageUrl();
                if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                    imageView.setImage(new javafx.scene.image.Image(imageUrl, 80, 80, true, true));
                } else {
                    imageView.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/placeholder.jpg")));
                }
            } catch (Exception e) {
                imageView.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/placeholder.jpg")));
            }

            HBox top = new HBox(15, imageView, nameLink);
            top.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            card.getChildren().addAll(top, descLabel);
            categoriesContainer.getChildren().add(card);
        }
    }

    private void showProductsByCategory(Category category) {
        mainTabPane.getSelectionModel().select(0); // Products tab
        List<Product> products = productDAO.findByCategory(category.getId());
        displayProducts(products);
        statusLabel.setText("Showing products for category: " + category.getName());
    }
} 