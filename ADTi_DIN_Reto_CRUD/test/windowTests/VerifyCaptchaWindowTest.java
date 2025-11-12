package windowTests;

import controller.Controller;
import controller.VerifyCaptchaWindowController;
import dao.MockModelDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Gender;
import model.LoggedProfile;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

/**
 * Test class for VerifyCaptchaWindow.
 * This class uses TestFX to validate that all components are loaded correctly,
 * and that the behavior of the confirmation and cancellation actions
 * works as expected.
 */
public class VerifyCaptchaWindowTest extends ApplicationTest {

    private VerifyCaptchaWindowController captchaController;
    private Controller realController;
    private MockModelDAO mockDAO;
    private User mockUser;

    @Override
    public void start(Stage stage) throws Exception {
        mockDAO = new MockModelDAO();
        realController = new Controller(mockDAO);

        mockUser = new User(1, "test@example.com", "testuser", "Password123",
                "John", "Doe", "123456789", Gender.MALE, "1234567890123456");

        LoggedProfile.getInstance().setProfile(mockUser);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyCaptchaWindow.fxml"));
        Parent root = loader.load();
        captchaController = loader.getController();

        captchaController.setController(realController, -1);

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Before
    public void setUp() {
        LoggedProfile.getInstance().clear();
        mockDAO.setShouldThrowException(false, null);
        mockDAO.setMockUser(null);
    }

    @Test
    public void testAllComponentsAreLoaded() {
        Pane rightPane = lookup("#rightPane").query();
        TextField codeTextField = lookup("#codeTextField").query();
        Label usernameLabel = lookup("#username").query();
        Label codeLabel = lookup("#codeLabel").query();
        Button confirmButton = lookup("#confirmBttn").query();
        Button cancelButton = lookup("#cancelBttn").query();
        Label titleLabel = lookup("#titleLabel").query();
        Label errorLabel = lookup("#errorLabel").query();

        assertNotNull(rightPane);
        assertNotNull(codeTextField);
        assertNotNull(usernameLabel);
        assertNotNull(codeLabel);
        assertNotNull(confirmButton);
        assertNotNull(cancelButton);
        assertNotNull(titleLabel);
        assertNotNull(errorLabel);

        assertEquals("testuser", usernameLabel.getText());
        assertEquals("CONFIRM", confirmButton.getText());
        assertEquals("CANCEL", cancelButton.getText());
        assertEquals("WRITE THE NEXT CODE TO CONFIRM", titleLabel.getText());
        assertEquals("", errorLabel.getText());
        assertFalse(codeLabel.getText().isEmpty());
    }

    @Test
    public void testConfirmWithEmptyField() {
        clickOn("#confirmBttn");
        Label errorLabel = lookup("#errorLabel").query();
        assertEquals("Please enter the code.", errorLabel.getText());
    }

    @Test
    public void testConfirmWithIncorrectCode() {
        String wrongCode = "9999";
        clickOn("#codeTextField").write(wrongCode);
        clickOn("#confirmBttn");

        Label errorLabel = lookup("#errorLabel").query();
        assertEquals("Incorrect code. Try again.", errorLabel.getText());
    }

    @Test
    public void testConfirmWithCorrectCode() {
        Label codeLabel = lookup("#codeLabel").query();
        String correctCode = codeLabel.getText();

        clickOn("#codeTextField").write(correctCode);
        clickOn("#confirmBttn");

        pressEscape();
    }

    @Test
    public void testCancelButton() {
        clickOn("#cancelBttn");
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
