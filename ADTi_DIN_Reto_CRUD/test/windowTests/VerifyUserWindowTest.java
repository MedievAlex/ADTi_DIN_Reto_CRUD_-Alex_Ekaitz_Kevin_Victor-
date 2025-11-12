package windowTests;

import controller.Controller;
import controller.VerifyUserWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.LoggedProfile;
import model.User;
import model.Gender;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import dao.MockModelDAO;

import static org.junit.Assert.*;

public class VerifyUserWindowTest extends ApplicationTest
{

    private VerifyUserWindowController verifyController;
    private Controller realController;
    private MockModelDAO mockDAO;
    private User mockUser;

    @Override
    public void start(Stage stage) throws Exception
    {
        mockDAO = new MockModelDAO();
        realController = new Controller(mockDAO);

        mockUser = new User(1, "test@example.com", "testuser", "Password123",
                "John", "Doe", "123456789", Gender.MALE, "1234567890123456");

        LoggedProfile.getInstance().setProfile(mockUser);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyUserWindow.fxml"));
        Parent root = loader.load();
        verifyController = loader.getController();

        verifyController.setController(realController, -1);

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Before
    public void setUp()
    {
        LoggedProfile.getInstance().clear();
        mockDAO.setShouldThrowException(false, null);
        mockDAO.setMockUser(null);
    }

    @Test
    public void testAllComponentsAreLoaded()
    {
        Pane rightPane = lookup("#rightPane").query();
        PasswordField passwordField = lookup("#passwordPasswordField").query();
        Label usernameLabel = lookup("#username").query();
        Button confirmButton = lookup("#confirmBttn").query();
        Button cancelButton = lookup("#cancelBttn").query();
        Label titleLabel = lookup("#titleLabel").query();
        Label errorLabel = lookup("#errorLabel").query();

        assertNotNull(rightPane);
        assertNotNull(passwordField);
        assertNotNull(usernameLabel);
        assertNotNull(confirmButton);
        assertNotNull(cancelButton);
        assertNotNull(titleLabel);
        assertNotNull(errorLabel);

        assertEquals("testuser", usernameLabel.getText());
        assertEquals("CONFIRM", confirmButton.getText());
        assertEquals("CANCEL", cancelButton.getText());
        assertEquals("ENTER YOUR PASSWORD", titleLabel.getText());
        assertEquals("· · · · · · · ·", passwordField.getPromptText());
        assertEquals("", errorLabel.getText());
    }

    @Test
    public void testConfirmWithIncorrectPassword()
    {
        clickOn("#passwordPasswordField").write("WrongPassword");
        clickOn("#confirmBttn");

        Label errorLabel = lookup("#errorLabel").query();
        assertEquals("Incorrect password.", errorLabel.getText());
    }

    @Test
    public void testConfirmWithCorrectPassword()
    {
        LoggedProfile.getInstance().setProfile(mockUser);

        clickOn("#passwordPasswordField").write("Password123");
        clickOn("#confirmBttn");
        sleep(500);

        Label textLabel = lookup("#titleLabel").query();
        assertEquals("ARE YOU SURE YOU WANT TO DELETE THE PROFILE?", textLabel.getText());
    }

    @Test
    public void testCancelButton()
    {
        clickOn("#cancelBttn");
    }
}
