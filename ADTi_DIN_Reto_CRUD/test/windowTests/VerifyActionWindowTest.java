package windowTests;

import controller.Controller;
import controller.VerifyActionWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class VerifyActionWindowTest extends ApplicationTest {

    private VerifyActionWindowController verifyActionController;
    private Controller realController;
    private MockModelDAO mockDAO;
    private User mockUser;
    private boolean callbackExecuted;

    @Override
    public void start(Stage stage) throws Exception {
        mockDAO = new MockModelDAO();
        realController = new Controller(mockDAO);

        mockUser = new User(1, "test@example.com", "testuser", "Password123",
                "John", "Doe", "123456789", Gender.MALE, "1234567890123456");

        LoggedProfile.getInstance().setProfile(mockUser);
        callbackExecuted = false;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyActionWindow.fxml"));
        Parent root = loader.load();
        verifyActionController = loader.getController();

        verifyActionController.setController(realController, 1);
        verifyActionController.setOnUserDeletedCallback(() -> callbackExecuted = true);

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Before
    public void setUp() {
        LoggedProfile.getInstance().clear();
        mockDAO.setShouldThrowException(false, null);
        mockDAO.setMockUser(null);
        callbackExecuted = false;
    }

    @Test
    public void testAllComponentsAreLoaded() {
        Pane rightPane = lookup("#rightPane").query();
        Label usernameLabel = lookup("#username").query();
        Label titleLabel = lookup("#titleLabel").query();
        Label textLabel = lookup("#textLabel").query();
        Button confirmButton = lookup("#confirmBttn").query();
        Button cancelButton = lookup("#cancelBttn").query();

        assertNotNull(rightPane);
        assertNotNull(usernameLabel);
        assertNotNull(titleLabel);
        assertNotNull(textLabel);
        assertNotNull(confirmButton);
        assertNotNull(cancelButton);

        assertEquals("testuser", usernameLabel.getText());
        assertEquals("ARE YOU SURE YOU WANT TO DELETE THE PROFILE?", titleLabel.getText());
        assertEquals("This action is irreversible. The profile and its data will be deleted forever and it will not be able to restore it.", textLabel.getText());
        assertEquals("CONFIRM", confirmButton.getText());
        assertEquals("CANCEL", cancelButton.getText());
    }

    @Test
    public void testConfirmButtonNavigatesToCaptchaWindow() {
        LoggedProfile.getInstance().setProfile(mockUser);
        
        clickOn("#confirmBttn");
        sleep(500);

        Label textLabel = lookup("#titleLabel").query();
        assertEquals("WRITE THE NEXT CODE TO CONFIRM", textLabel.getText());
    }

    @Test
    public void testCancelButton() {
        clickOn("#cancelBttn");
    }

    @Test
    public void testCallbackIsSet() {
        assertFalse("Callback should not be executed yet", callbackExecuted);
    }
}