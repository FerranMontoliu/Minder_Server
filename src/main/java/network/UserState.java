package network;

import model.User;

import java.io.Serializable;
import java.util.ArrayList;

public class UserState implements Serializable {

    private ArrayList<User> model;

    public UserState(ArrayList<User> model) {
        this.model = model;
    }
}
