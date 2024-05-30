package sep.viewmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class HelpViewModelTest {

    private Model model;
    private HelpViewModel viewModel;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        viewModel = new HelpViewModel(model);
    }

    @Test
    public void testConstructorInitialization() {
        assertNotNull(viewModel);
    }
}