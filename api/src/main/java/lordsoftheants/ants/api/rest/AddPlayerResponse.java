package lordsoftheants.ants.api.rest;

/**
 * @author Adrian Scripca
 */
public class AddPlayerResponse extends Response {
    public String token;
    public int slot;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
