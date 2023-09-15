import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {

    final static Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        try {
            DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "user", "pass");
            Connection connection = dcm.getConnection();

            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = customerDAO.findById(1);
            logger.debug(customer.toString());

            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1000);
            logger.debug(order.toString());

            connection.close();
        } catch (SQLException ex) {
            logger.error("Failed to perform SQL query", ex);
        }
    }
}
