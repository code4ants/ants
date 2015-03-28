package lordsoftheants.ants.api.rest;

/**
 * @author Adrian Scripca
 */
public class AddPlayerResponse extends Response {
    public String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
