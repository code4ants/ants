import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lordsoftheants.ants.api.rest.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Adrian Scripca
 */
public class Client {

    private ObjectMapper objectMapper = new ObjectMapper();
    private Configuration config;

    public Client(Configuration config) {
        this.config = config;
    }

    public void setParameter(String key, String value) {
        if (StringUtils.isBlank(key))
            throw new ClientException("Cannot set parameter with null name");

        if (StringUtils.isBlank(value))
            throw new ClientException("Cannot set parameter with null value");

        SetParameterRequest request = new SetParameterRequest();
        request.setToken(config.getToken());
        request.setKey(key);
        request.setValue(value);

        HttpResponse<String> httpResponse;
        try {
            httpResponse = Unirest.post(config.getServerUrl() + "/parameter")
                    .header("Content-Type", "application/json")
                    .body(toJson(request))
                    .asString();
            SetParameterResponse response = fromJson(httpResponse.getBody(), SetParameterResponse.class);
            if (response == null || !response.isSuccess()) {
                throw new ClientException("Failed to set parameter. Reason: " + (response == null ? "None" : response.getDescription()));
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new ClientException("Failed to set parameter. Reason: " + e.getMessage());
        }
    }


    public void performLogin() {
        if (StringUtils.isBlank(config.getServerUrl()))
            throw new ClientException("Server url not defined!");

        if (StringUtils.isBlank(config.getUsername()))
            throw new ClientException("Username not defined!");

        AddPlayerRequest request = new AddPlayerRequest();
        request.setName(config.getUsername());
        try {
            HttpResponse<String> httpResponse = Unirest
                    .post(config.getServerUrl() + "/players")
                    .header("Content-Type", "application/json")
                    .body(toJson(request))
                    .asString();
            AddPlayerResponse response = fromJson(httpResponse.getBody(), AddPlayerResponse.class);
            if (response != null && response.isSuccess()) {
                config.setToken(response.getToken());
                config.save();
            } else {
                throw new ClientException("Failed to perform login. Reason: " + (response == null ? "None" : response.getDescription()));
            }
        } catch (UnirestException e) {
            throw new ClientException("Failed to perform login. Reason: " + e.getMessage());
        }
    }

    public void uploadBrain() {
        if (StringUtils.isBlank(config.getServerUrl()))
            throw new ClientException("Server url not defined!");
        if (StringUtils.isBlank(config.getClassFqn()))
            throw new ClientException("Brain class fully qualified name not defined!");
        if (StringUtils.isBlank(config.getClassPath()))
            throw new ClientException("Brain class path not defined!");
        if (StringUtils.isBlank(config.getToken()))
            throw new ClientException("Need to perform login first!");

        UploadBrainRequest request = new UploadBrainRequest();
        request.setToken(config.getToken());
        request.setClassFqn(config.getClassFqn());
        request.setBrainCode(getFileBytes(config.getClassPath()));

        HttpResponse<String> httpResponse;
        try {
            httpResponse = Unirest.put(config.getServerUrl() + "/brain")
                    .header("Content-Type", "application/json")
                    .body(toJson(request))
                    .asString();
            UploadBrainResponse response = fromJson(httpResponse.getBody(), UploadBrainResponse.class);
            if (response == null || !response.isSuccess()) {
                throw new ClientException("Failed to upload brain. Reason: " + (response == null ? "None" : response.getDescription()));
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new ClientException("Failed to upload brain. Reason: " + e.getMessage());
        }
    }

    private byte[] getFileBytes(String pathToClass) {
        try {
            return IOUtils.toByteArray(new FileInputStream(pathToClass));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClientException("Failed to read file " + pathToClass + ". Reason:" + e.getMessage());
        }
    }

    private <T> T fromJson(String json, Class<T> clazz) {
        try {
            System.out.println("<< " + json);
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String toJson(Object object) {
        try {
            String result = objectMapper.writeValueAsString(object);
            System.out.println(">> " + result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
