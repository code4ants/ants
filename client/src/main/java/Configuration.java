import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Properties;

/**
 * @author Adrian Scripca
 */
public class Configuration {

    private static final String SETTINGS_FILENAME = "ants.properties";
    private String serverUrl;
    private String username;
    private String token;
    private String classFqn;
    private String classPath;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public void save() {
        Properties props = new Properties();
        if (serverUrl != null)
            props.setProperty(Keys.SERVER_URL.getValue(), serverUrl);

        if (username != null)
            props.setProperty(Keys.USERNAME.getValue(), username);

        if (token != null)
            props.setProperty(Keys.TOKEN.getValue(), token);

        if (classFqn != null)
            props.setProperty(Keys.CLASS_FQN.getValue(), classFqn);

        if (classPath != null)
            props.setProperty(Keys.CLASS_PATH.getValue(), classPath);

        OutputStream out = null;
        try {
            out = new FileOutputStream(SETTINGS_FILENAME);
            props.store(out, "ant master settings");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    public void load() {
        Properties props = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(SETTINGS_FILENAME);
            props.load(is);

            serverUrl = props.getProperty(Keys.SERVER_URL.getValue());
            username = props.getProperty(Keys.USERNAME.getValue());
            token = props.getProperty(Keys.TOKEN.getValue());
            classPath = props.getProperty(Keys.CLASS_PATH.getValue());
            classFqn = props.getProperty(Keys.CLASS_FQN.getValue());

        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public enum Keys {
        SERVER_URL("serverUrl"),
        USERNAME("username"),
        TOKEN("token"),
        CLASS_FQN("classFqn"),
        CLASS_PATH("classPath");

        private String value;

        Keys(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
