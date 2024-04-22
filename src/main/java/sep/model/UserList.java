package sep.model;

import java.util.ArrayList;
import java.util.Objects;

public class UserList {
    private ArrayList<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public User getUserByID(int id) {
        User user = null;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID() == id){
                user = users.get(i);
            }
        }
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserList userList = (UserList) o;
        return Objects.equals(users, userList.users);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(users);
    }

    @Override
    public String toString() {
        String string = "";

        for (User user : users) {
            string += user.toString() + "\n";
        }
        return string;
    }
}
