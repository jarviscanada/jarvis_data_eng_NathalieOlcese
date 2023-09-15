import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderDAO extends DataAccessObject<Order> {

    final static Logger logger = LoggerFactory.getLogger(OrderDAO.class);

    private static final String GET_ONE = "SELECT c.first_name, c.last_name, c.email, o.order_id, o.creation_date, o.total_due, o.status, s.first_name, s.last_name, s.email, ol.quantity, p.code, p.name, p.size, p.variety, p.price FROM orders o JOIN customer c ON o.customer_id=c.customer_id JOIN salesperson s ON o.salesperson_id=s.salesperson_id JOIN order_item ol ON ol.order_id=o.order_id JOIN product p ON ol.product_id=p.product_id WHERE o.order_id=?";
    
    private static final String GET_ALL = "SELECT DISTINCT o.order_id, c.first_name, c.last_name, c.email, o.creation_date, o.total_due, o.status, s.first_name, s.last_name, s.email FROM orders o JOIN customer c ON o.customer_id=c.customer_id JOIN salesperson s ON o.salesperson_id=s.salesperson_id";
    
    private static final String UPDATE_ORDER = "UPDATE orders SET total_due=?, status=? WHERE order_id=?";
    
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id=?";
    
    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Order findById(long id) {
        Order order = null;
        try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            long orderId = 0;
            List<OrderLine>
