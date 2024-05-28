package sep.viewmodel;

import sep.model.Model;

/**
 * The HelpViewModel class provides the view model for accessing help information.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class HelpViewModel {
    private final Model model;

    /**
     * Constructs a HelpViewModel with the specified model.
     *
     * @param model The model to interact with for accessing help information
     */
    public HelpViewModel(Model model) {
        this.model = model;
    }
}
