package sep.viewmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AdminMainViewModelTest {
    private Model model;
    private AdminMainViewModel adminMainViewModel;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        adminMainViewModel = new AdminMainViewModel(model);
    }

    @Test
    public void testConstructor() {
        assertNotNull(adminMainViewModel);
    }
}