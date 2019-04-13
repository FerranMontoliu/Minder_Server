package model.entity;

import java.util.ArrayList;

public class LikesLoader {
    private ArrayList<String> likedUsers;

    /**
     * Constructor per defecte
     */
    public LikesLoader(){
        likedUsers = new ArrayList<>();
    }

    /**
     * Getter del atribut likedUsers
     * @return atribut likedUsers
     */
    public ArrayList<String> getLikedUsers() {
        return likedUsers;
    }

    /**
     * Metode que afegeix un usuari mes al LikesLoader.
     * @param username usuari nou al qual ha donat like.
     */
    public void addLike(String username){
        likedUsers.add(username);
    }

}


