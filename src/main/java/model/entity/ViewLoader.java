package model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewLoader implements Serializable {
    ArrayList<String> viewedUsers;

    public ViewLoader(ArrayList<String> viewedUsers) {
        this.viewedUsers = viewedUsers;
    }
}
