import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDatabase {
    public static void main(String[] args) {
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:database/ecommerce.db");
            Statement stmt = conn.createStatement();
            
            // Check if pricing_unit column exists
            try {
                ResultSet rs = stmt.executeQuery("SELECT pricing_unit FROM products LIMIT 1");
                System.out.println("pricing_unit column exists in products table");
            } catch (Exception e) {
                System.out.println("pricing_unit column does not exist, adding it...");
                stmt.execute("ALTER TABLE products ADD COLUMN pricing_unit TEXT DEFAULT 'PER_UNIT'");
                System.out.println("Successfully added pricing_unit column");
                
                // Update existing products
                stmt.execute("UPDATE products SET pricing_unit = 'PER_UNIT' WHERE pricing_unit IS NULL");
                System.out.println("Updated existing products with default pricing unit");
            }
            
            // Test inserting a product with pricing unit
            stmt.execute("INSERT INTO products (name, description, price, stock_quantity, category_id, image_url, pricing_unit) VALUES ('Test Product', 'Test Description', 10.00, 100, 1, 'test.jpg', 'PER_KG')");
            System.out.println("Successfully inserted test product with PER_KG pricing unit");
            
            // Test reading the product back
            ResultSet rs = stmt.executeQuery("SELECT name, pricing_unit FROM products WHERE name = 'Test Product'");
            if (rs.next()) {
                System.out.println("Test product found: " + rs.getString("name") + " with pricing unit: " + rs.getString("pricing_unit"));
            }
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 