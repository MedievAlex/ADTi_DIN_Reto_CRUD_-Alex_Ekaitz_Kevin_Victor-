package exception;

/**
 *
 * @author Alex, Ekaitz, Kevin, Victor
 */
public class ErrorMessages {
    public static final String REGISTER_USER = "User could not be registered. Please try again later.";
    public static final String GET_USERS = "Failed to retrieve users.";
    public static final String UPDATE_USER = "User could not be updated.";
    public static final String DELETE_USER = "User could not be deleted.";
    public static final String LOGIN = "Login failed. Please check your credentials.";
    public static final String VERIFY_CREDENTIALS = "Could not verify existing credentials.";
    public static final String ROLLBACK = "Transaction rollback failed.";
    public static final String RESET_AUTOCOMMIT = "Failed to reset connection settings.";
    public static final String TIMEOUT = "Timeout retrieving a connection.";
    public static final String DATABASE = "Error initializing database connection.";
}