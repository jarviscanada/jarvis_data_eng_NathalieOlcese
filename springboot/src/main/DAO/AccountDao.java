import ca.jrvs.apps.trading.model.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private final String TABLE_NAME = "account";
    private final String ID_COLUMN = "id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public AccountDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIDColumnName() {
        return ID_COLUMN;
    }

    @Override
    public Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    public int updateOne(Account entity) {
        String sql = "UPDATE " + getTableName() + " SET amount = ? WHERE " + getIDColumnName() + " = ?";
        return getJdbcTemplate().update(sql, entity.getAmount(), entity.getId());
    }

    public Optional<Account> findByTraderId(Integer traderId) {
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE trader_id = ?";
        try {
            Account account = getJdbcTemplate().queryForObject(selectSql,
                    BeanPropertyRowMapper.newInstance(Account.class), traderId);
            return Optional.of(account);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Can't find account for trader id: " + traderId);
            return Optional.empty();
        }
    }

    public void updateAmountById(Integer accountId, Double amount) {
        String sql = "UPDATE " + getTableName() + " SET amount = ? WHERE " + getIDColumnName() + " = ?";
        getJdbcTemplate().update(sql, amount, accountId);
    }
}
