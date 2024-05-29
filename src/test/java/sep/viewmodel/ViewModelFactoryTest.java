package sep.viewmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Model;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class ViewModelFactoryTest {

    private Model model;
    private ViewModelFactory viewModelFactory;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        viewModelFactory = new ViewModelFactory(model);
    }

    @Test
    public void testGetLoginViewModel() {
        assertNotNull(viewModelFactory.getLoginViewModel());
    }

    @Test
    public void testGetAdminLoginViewModel() {
        assertNotNull(viewModelFactory.getAdminLoginViewModel());
    }

    @Test
    public void testGetCreateAccountViewModel() {
        assertNotNull(viewModelFactory.getCreateAccountViewModel());
    }

    @Test
    public void testGetDonateViewModel() {
        assertNotNull(viewModelFactory.getDonateViewModel());
    }

    @Test
    public void testGetAdminManageAccountsViewModel() {
        assertNotNull(viewModelFactory.getAdminManageAccountsViewModel());
    }

    @Test
    public void testGetAdminServerLogViewModel() {
        assertNotNull(viewModelFactory.getAdminServerLogViewModel());
    }

    @Test
    public void testGetHelpViewModel() {
        assertNotNull(viewModelFactory.getHelpViewModel());
    }

    @Test
    public void testGetMainViewModel() {
        assertNotNull(viewModelFactory.getMainViewModel());
    }

    @Test
    public void testGetMyBooksViewModel() {
        assertNotNull(viewModelFactory.getMyBooksViewModel());
    }

    @Test
    public void testGetProfileViewModel() {
        assertNotNull(viewModelFactory.getProfileViewModel());
    }

    @Test
    public void testGetAdminManageEventsViewModel() {
        assertNotNull(viewModelFactory.getAdminManageEventsViewModel());
    }

    @Test
    public void testGetAdminManageDonatedBooksViewModel() {
        assertNotNull(viewModelFactory.getAdminManageDonatedBooksViewModel());
    }

    @Test
    public void testGetAdminMainViewModel() {
        assertNotNull(viewModelFactory.getAdminMainViewModel());
    }

    @Test
    public void testGetAdminManageBooksViewModel() {
        assertNotNull(viewModelFactory.getAdminManageBooksViewModel());
    }

    @Test
    public void testGetEventsViewModel() {
        assertNotNull(viewModelFactory.getEventsViewModel());
    }
}
