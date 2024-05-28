package sep.viewmodel;

import sep.model.Model;

/**
 * The AdminMainViewModel class provides the view model for the admin main view.
 * It handles the interaction between the admin main view and the underlying model.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */

public class AdminMainViewModel {
    private final Model model;

    /**
     * Constructs an AdminMainViewModel with the specified model.
     *
     * @param model The model to interact with for admin operations
     */
    public AdminMainViewModel(Model model) {
        this.model = model;
    }
}
