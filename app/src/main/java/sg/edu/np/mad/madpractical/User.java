package sg.edu.np.mad.madpractical;

public class User {
    public String name;
    public String description;
    public int id;
    public boolean followed;

    public User(String aName, String aDescription, int aId, boolean aFollowed) {
        this.name = aName;
        this.description = aDescription;
        this.id = aId;
        this.followed = aFollowed;
    }
}
