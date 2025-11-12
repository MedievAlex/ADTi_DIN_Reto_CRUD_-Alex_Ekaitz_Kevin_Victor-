package unitTests;

import controller.Controller;
import dao.ModelDAO;
import exception.OurException;
import model.Profile;
import model.User;
import model.Gender;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class ControllerTest {
    
    private static class MockModelDAO implements ModelDAO {
        private final User mockUser;
        private final Profile mockProfile;
        private final ArrayList<User> mockUsers;
        private boolean shouldThrowException;
        private OurException exceptionToThrow;

        public MockModelDAO() {
            this.mockUser = new User(1, "test@test.com", "testuser", "password",
                    "Test", "User", "123456789", Gender.MALE, "1234567890123456");
            this.mockProfile = mockUser;
            this.mockUsers = new ArrayList<>();
            mockUsers.add(mockUser);
            this.shouldThrowException = false;
        }

        public void setShouldThrowException(boolean shouldThrow, OurException exception) {
            this.shouldThrowException = shouldThrow;
            this.exceptionToThrow = exception;
        }

        @Override
        public User register(User user) throws OurException {
            if (shouldThrowException) {
                throw exceptionToThrow;
            }
            return mockUser;
        }

        @Override
        public Profile login(String credential, String password) throws OurException {
            if (shouldThrowException) {
                throw exceptionToThrow;
            }
            return mockProfile;
        }

        @Override
        public ArrayList<User> getUsers() throws OurException {
            if (shouldThrowException) {
                throw exceptionToThrow;
            }
            return mockUsers;
        }

        @Override
        public boolean updateUser(User user) throws OurException {
            if (shouldThrowException) {
                throw exceptionToThrow;
            }
            return true;
        }

        @Override
        public boolean deleteUser(int id) throws OurException {
            if (shouldThrowException) {
                throw exceptionToThrow;
            }
            return id > 0;
        }
    }
    
    private Controller controller;
    private MockModelDAO mockDAO;
    
    @Before
    public void setUp() throws OurException {
        mockDAO = new MockModelDAO();
        controller = new Controller(mockDAO);
    }
    
    @Test
    public void testRegisterUser() throws OurException {
        User testUser = new User("new@test.com", "newuser", "password123", 
                               "New", "User", "987654321", Gender.FEMALE, "9876543210987654");
        
        User result = controller.register(testUser);
        
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("test@test.com", result.getEmail());
        assertEquals("testuser", result.getUsername());
    }
    
    @Test
    public void testLogin() throws OurException {
        Profile result = controller.login("testuser", "password");
        
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@test.com", result.getEmail());
    }
    
    @Test
    public void testGetUsers() throws OurException {
        ArrayList<User> result = controller.getUsers();
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
    }
    
    @Test
    public void testUpdateUser() throws OurException {
        User userToUpdate = new User(1, "updated@test.com", "updateduser", "newpass", 
                                   "Updated", "Name", "111111111", Gender.MALE, "1234567890123456");
        
        boolean result = controller.updateUser(userToUpdate);
        assertTrue(result);
    }
    
    @Test
    public void testDeleteUser() throws OurException {
        boolean result = controller.deleteUser(1);
        assertTrue(result);
        
        boolean result2 = controller.deleteUser(0);
        assertFalse(result2);
    }
    
    @Test(expected = OurException.class)
    public void testExceptionPropagation() throws OurException {
        mockDAO.setShouldThrowException(true, new OurException("Test exception"));
        controller.login("test", "test");
    }
}