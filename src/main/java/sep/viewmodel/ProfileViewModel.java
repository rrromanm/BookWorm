package sep.viewmodel;

import sep.model.Model;

//TODO: NOTE TO ROMANS: I finished updateUsername etc. but you need to create getUsername etc.
// so we can retrieve values from db, and thus update them in ViewModel
public class ProfileViewModel {
    private final Model model;

    public ProfileViewModel(Model model)
    {
        this.model = model;
    }
}
