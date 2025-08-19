package com.ecommerce.dao;

import com.ecommerce.model.OrderItem;
import com.ecommerce.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class OrderItemDAO {
    public boolean create(OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getProductId());
            stmt.setInt(3, item.getQuantity());
            stmt.setBigDecimal(4, item.getPrice());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("OrderItemDAO.create() SQL error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean createAll(List<OrderItem> items) {
        boolean allSuccess = true;
        for (OrderItem item : items) {
            if (!create(item)) {
                allSuccess = false;
            }
        }
        return allSuccess;
    }

    public List<OrderItem> findByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.id, oi.order_id, oi.product_id, oi.quantity, oi.unit_price, " +
                "p.name AS product_name, p.image_url AS product_image_url " +
                "FROM order_items oi " +
                "LEFT JOIN products p ON oi.product_id = p.id " +
                "WHERE oi.order_id = ? ORDER BY oi.id";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getBigDecimal("unit_price"));
                    try { item.setProductName(rs.getString("product_name")); } catch (SQLException ignore) {}
                    try { item.setProductImageUrl(rs.getString("product_image_url")); } catch (SQLException ignore) {}
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("OrderItemDAO.findByOrderId() SQL error: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }
} 