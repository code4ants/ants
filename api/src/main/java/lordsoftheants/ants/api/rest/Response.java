package lordsoftheants.ants.api.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Adrian Scripca
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    public boolean success;
    public String description;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void reportSuccess(String description) {
        this.success = true;
        this.description = description;
    }

    public void reportFailure(String description) {
        this.success = false;
        this.description = description;
    }
}
