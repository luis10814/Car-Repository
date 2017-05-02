package assign4;

import java.io.Serializable;

public abstract class User  implements Serializable {

    protected int id;
    protected String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Abstract print method, to be implemented by subclasses of class User.
     */
    public abstract void print();
}
