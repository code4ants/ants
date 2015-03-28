package lordsoftheants.ants.api.rest;

/**
 * @author Adrian Scripca
 */
public class AddPlayerRequest {
    private String name;

    public AddPlayerRequest() {
        // Need this for jackson!
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
