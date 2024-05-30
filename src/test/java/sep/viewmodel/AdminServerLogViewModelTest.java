package sep.viewmodel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Model;

public class AdminServerLogViewModelTest {

    private Model model;
    private AdminServerLogViewModel viewModel;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        viewModel = new AdminServerLogViewModel(model);
    }

    @Test
    public void testConstructor() {
        assertNotNull(viewModel, "AdminServerLogViewModel should be instantiated");
    }
}