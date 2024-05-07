package sep.model;

import java.util.ArrayList;
import java.util.Objects;

public class UserList {
    private ArrayList<Patron> patrons;

    public UserList() {
        patrons = new ArrayList<>();
    }

    public ArrayList<Patron> getUsers() {
        return patrons;
    }

    public void addUser(Patron patron) {
        patrons.add(patron);
    }

    public void removeUser(Patron patron) {
        patrons.remove(patron);
    }

    public Patron getUserByID(int id) {
        Patron patron = null;

        for (int i = 0; i < patrons.size(); i++) {
            if (patrons.get(i).getUserID() == id){
                patron = patrons.get(i);
            }
        }
        return patron;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserList userList = (UserList) o;
        return Objects.equals(patrons, userList.patrons);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(patrons);
    }

    @Override
    public String toString() {
        String string = "";

        for (Patron patron : patrons) {
            string += patron.toString() + "\n";
        }
        return string;
    }
}
