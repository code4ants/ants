package lordsoftheants.ants.api.rest;

/**
 * @author Adrian Scripca
 */
public class SetParameterRequest {

    private String token;

    private String key;

    private String value;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
