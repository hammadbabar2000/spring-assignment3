package ca.sheridancollege.babarha.lec92_customformlogin.database;

import ca.sheridancollege.babarha.lec92_customformlogin.beans.Book;
import ca.sheridancollege.babarha.lec92_customformlogin.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseAccess {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void insertHardCodedData() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("title", "The Way Of Kings");
        namedParameters.addValue("author", "Brandon Sanderson");
        namedParameters.addValue("publisher", "Tor Books");
        namedParameters.addValue("price", 10);
        namedParameters.addValue("status", "Finished");
        String query = "INSERT INTO BOOK (TITLE, AUTHOR, PUBLISHER, PRICE, STATUS) VALUES (:title, :author, :publisher, :price, :status)";
        int rows = jdbc.update(query, namedParameters);
        if (rows > 0) {
            System.out.println("Data inserted successfully");
        }
    }

    public void insertBook(Book book) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("title", book.getTitle());
        namedParameters.addValue("author", book.getAuthor());
        namedParameters.addValue("publisher", book.getPublisher());
        namedParameters.addValue("price", book.getPrice());
        namedParameters.addValue("status", book.getStatus());
        String query = "INSERT INTO BOOK (TITLE, AUTHOR, PUBLISHER, PRICE, STATUS) VALUES (:title, :author, :publisher, :price, :status)";
        int rows = jdbc.update(query, namedParameters);
        if (rows > 0) {
            System.out.println("Data inserted successfully");
        }
    }

    public List<Book> getBookList() {
        String query = "SELECT * FROM BOOK";
        return jdbc.query(query, new BeanPropertyRowMapper<>(Book.class));
    }

    public void updateBook(Book book) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", book.getId());
        namedParameters.addValue("title", book.getTitle());
        namedParameters.addValue("author", book.getAuthor());
        namedParameters.addValue("publisher", book.getPublisher());
        namedParameters.addValue("price", book.getPrice());
        namedParameters.addValue("status", book.getStatus());
        String query = "UPDATE BOOK SET TITLE = :title, AUTHOR = :author, PUBLISHER = :publisher, PRICE = :price, STATUS = :status WHERE ID = :id";
        int rows = jdbc.update(query, namedParameters);
        if (rows > 0) {
            System.out.println("Data updated successfully");
        }
    }

    public void deleteBook(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        String query = "DELETE FROM BOOK WHERE ID = :id";
        int rows = jdbc.update(query, namedParameters);
        if (rows > 0) {
            System.out.println("Data deleted successfully");
        }
    }

    public List<Book> getBookById(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        String query = "SELECT * FROM BOOK WHERE ID = :id";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> getBookByTitle(String title) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("title", "%" + title + "%");
        String query = "SELECT * FROM BOOK WHERE lower(TITLE) LIKE lower(:title)";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Book.class));
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(String email, String password) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query =
                "INSERT INTO sec_user " +
                        "(email, encryptedPassword, enabled) " +
                        "VALUES (:email, :encryptedPassword, 3)";
        namedParameters.addValue("email", email);
        namedParameters.addValue("encryptedPassword",
                passwordEncoder.encode(password));
        jdbc.update(query, namedParameters);
    }

    public void addRole(Long userId, Long roleId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO user_role (userId, roleId) "
                + "VALUES (:userId, :roleId)";
        namedParameters.addValue("userId", userId);
        namedParameters.addValue("roleId", roleId);
        jdbc.update(query, namedParameters);
    }

    // Method to find a user account by email
    public User findUserAccount(String email) {
        MapSqlParameterSource namedParameters = new
                MapSqlParameterSource();
        String query = "SELECT * FROM sec_user where email = :email";
        namedParameters.addValue("email", email);
        try {
            return jdbc.queryForObject(query, namedParameters, new
                    BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    // Method to get User Roles for a specific User id
    public List<String> getRolesById(Long userId) {
        MapSqlParameterSource namedParameters = new
                MapSqlParameterSource();
        String query = "SELECT sec_role.roleName "
                + "FROM user_role, sec_role "
                + "WHERE user_role.roleId = sec_role.roleId "
                + "AND userId = :userId";
        namedParameters.addValue("userId", userId);
        return jdbc.queryForList(query, namedParameters,
                String.class);
    }
}
