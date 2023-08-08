import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerDAO extends DataAccessObject<Customer> {

    final static Logger logger = LoggerFactory.getLogger(CustomerDAO.class);
    private static final String GET_ONE = "SELECT customer_id, first_name, last_name, email, phone, address, city, state, zipcode FROM customer WHERE customer_id=?";

    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Customer findById(long id) {
        Customer customer = null;
        try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getLong("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setZipCode(rs.getString("zipcode"));
            }
        } catch (SQLException ex) {
            logger.error("Failed to perform SQL query", ex);
            throw new RuntimeException(ex);
        }
        return customer;
    }

    
}

