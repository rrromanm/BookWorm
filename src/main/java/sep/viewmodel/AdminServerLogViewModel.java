package sep.viewmodel;

import sep.model.Model;

/**
 * The AdminServerLogViewModel class provides the view model for managing the server log in the admin view.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class AdminServerLogViewModel {
    private final Model model;

    /**
     * Constructs an AdminServerLogViewModel with the specified model.
     *
     * @param model The model to interact with for managing server log data
     */
    public AdminServerLogViewModel(Model model) {
        this.model = model;
    }
}
