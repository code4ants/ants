package lordsoftheants.ants.api.rest;

/**
 * @author Adrian Scripca
 */
public class UploadBrainRequest {
    private String token;
    private String classFqn;
    private byte[] brainCode;

    public UploadBrainRequest() {
        // Jackson needs it. do not remove!
    }

    public UploadBrainRequest(String token, String classFqn, byte[] brainCode) {
        this.token = token;
        this.classFqn = classFqn;
        this.brainCode = brainCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClassFqn() {
        return classFqn;
    }

    public void setClassFqn(String classFqn) {
        this.classFqn = classFqn;
    }

    public byte[] getBrainCode() {
        return brainCode;
    }

    public void setBrainCode(byte[] brainCode) {
        this.brainCode = brainCode;
    }
}
