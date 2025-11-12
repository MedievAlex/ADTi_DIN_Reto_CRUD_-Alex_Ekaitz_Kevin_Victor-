package windowTests;

import controller.Controller;
import model.LoggedProfile;
import controller.LoginWindowController;
import exception.OurException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;
import model.Gender;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import dao.MockModelDAO;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

public class LoginWindowTest extends ApplicationTest
{

    private LoginWindowController loginController;
    private Controller realController;
    private MockModelDAO mockDAO;
    private User mockUser;
    private User mockAdmin;

    @Override
    public void start(Stage stage) throws Exception
    {
        mockDAO = new MockModelDAO();
        realController = new Controller(mockDAO);

        mockUser = new User(1, "user@test.com", "testuser", "password123",
                "Test", "User", "123456789", Gender.MALE, "1234567890123456");
        mockAdmin = new User(1, "admin@test.com", "adminuser", "password123",
                "Admin", "User", "123456789", Gender.MALE, "1234567890123456");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
        Parent root = loader.load();
        loginController = loader.getController();

        loginController.setController(realController);

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Before
    public void setUp()
    {
        LoggedProfile.getInstance().clear();
        mockDAO.setMockUser(null);
        mockDAO.setShouldThrowException(false, null);
    }

    @Test
    public void testAllComponentsAreLoaded()
    {
        Pane leftPane = lookup("#leftPane").query();
        Pane rightPane = lookup("#rightPane").query();
        assertNotNull(leftPane);
        assertNotNull(rightPane);

        TextField credentialField = lookup("#credentialTextField").query();
        PasswordField passwordField = lookup("#passwordPasswordField").query();
        assertNotNull(credentialField);
        assertNotNull(passwordField);
        assertEquals("exampleuser / example@email.com", credentialField.getPromptText());
        assertEquals("password", passwordField.getPromptText());

        Button loginButton = lookup("#logInBttn").query();
        Button signUpButton = lookup("#signUpBttn").query();
        assertNotNull(loginButton);
        assertNotNull(signUpButton);
        assertEquals("LOG IN", loginButton.getText());
        assertEquals("If you have no account SIGN UP", signUpButton.getText());
    }

    @Test
    public void testTextFieldWriting()
    {
        clickOn("#credentialTextField").write("testuser");
        verifyThat("#credentialTextField", hasText("testuser"));

        clickOn("#passwordPasswordField").write("mypassword123");
        verifyThat("#passwordPasswordField", hasText("mypassword123"));
    }

    @Test
    public void testLoginWithEmptyFields()
    {
        clickOn("#logInBttn");
        pressEscape();
    }

    @Test
    public void testLoginWithInvalidCredentials()
    {
        mockDAO.setMockUser(null);

        clickOn("#credentialTextField").write("wronguser");
        clickOn("#passwordPasswordField").write("wrongpass");
        clickOn("#logInBttn");
        pressEscape();
    }

    @Test
    public void testLoginWithException()
    {
        mockDAO.setShouldThrowException(true, new OurException("Database error"));

        clickOn("#credentialTextField").write("testuser");
        clickOn("#passwordPasswordField").write("password123");
        clickOn("#logInBttn");
        pressEscape();
    }

    @Test
    public void testNavigateToSignUpAndBack()
    {
        clickOn("#signUpBttn");
        sleep(1000);

        clickOn("#logInBttn");
        sleep(1000);

        verifyThat("#credentialTextField", hasText(""));
        verifyThat("#passwordPasswordField", hasText(""));
    }

    @Test
    public void testFieldsPersistInSameWindow()
    {
        clickOn("#credentialTextField").write("testuser");
        clickOn("#passwordPasswordField").write("password123");

        clickOn("#credentialTextField");

        verifyThat("#credentialTextField", hasText("testuser"));
        verifyThat("#passwordPasswordField", hasText("password123"));
    }

    @Test
    public void testSuccessfulUserLogin()
    {
        mockDAO.setMockUser(mockUser);
        LoggedProfile.getInstance().setProfile(mockUser);

        clickOn("#credentialTextField").write("testuser");
        clickOn("#passwordPasswordField").write("password123");
        clickOn("#logInBttn");
        sleep(1000);

        Label usernameField = lookup("#username").query();
        assertNotNull(usernameField);
        assertEquals("testuser", usernameField.getText());
    }

    @Test
    public void testSuccessfulAdminLogin()
    {
        mockDAO.setMockUser(mockAdmin);
        LoggedProfile.getInstance().setProfile(mockAdmin);

        clickOn("#credentialTextField").write("adminuser");
        clickOn("#passwordPasswordField").write("password123");
        clickOn("#logInBttn");
        sleep(1000);

        Label usernameField = lookup("#username").query();
        assertNotNull(usernameField);
        assertEquals("adminuser", usernameField.getText());
    }

    private void pressEscape()
    {
        try
        {
            sleep(1000);
            push(javafx.scene.input.KeyCode.ESCAPE);
            sleep(500);
        }
        catch (Exception e)
        {
        }
    }
}
