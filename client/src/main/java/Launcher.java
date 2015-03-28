import org.apache.commons.cli.*;

/**
 * @author Adrian Scripca
 */
public class Launcher {
    private Options options = new Options();
    private Configuration config = new Configuration();

    public static void main(String... args) {
        new Launcher().run(args);
    }

    private void run(String[] args) {
        createOptions();
        if (args.length == 0) {
            usage();
        }

        try {
            CommandLineParser parser = new GnuParser();
            CommandLine line = parser.parse(options, args);

            config.load();
            if (line.hasOption("server-url")) {
                config.setServerUrl(line.getOptionValue("server-url"));
            }
            if (line.hasOption("username")) {
                config.setUsername(line.getOptionValue("username"));
            }
            if (line.hasOption("class-fqn")) {
                config.setClassFqn(line.getOptionValue("class-fqn"));
            }
            if (line.hasOption("class-path")) {
                config.setClassPath(line.getOptionValue("class-path"));
            }
            config.save();

            interpretCommand(line);

        } catch (ParseException | ClientException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println();
            usage();
        }
    }

    private void interpretCommand(CommandLine line) {
        String action = line.getOptionValue("action");
        if (action == null) {
            // we might simply be configuring stuff
            return;
        }

        Client client = new Client(config);
        if ("login".equalsIgnoreCase(action)) {
            client.performLogin();
        } else if ("upload".equalsIgnoreCase(action)) {
            client.uploadBrain();
        } else if ("configure".equalsIgnoreCase(action)) {
            client.setParameter(line.getOptionValue("k"), line.getOptionValue("v"));
        } else {
            throw new ClientException("Unknown action " + action);
        }
    }

    private void usage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ant-client", options);
        System.exit(1);
    }

    private void createOptions() {
        Option username = OptionBuilder.withArgName("name")
                .hasArg()
                .withDescription("the player username")
                .withLongOpt("username")
                .create("u");
        options.addOption(username);

        Option action = OptionBuilder.withArgName("actionName")
                .hasArg()
                .withDescription("action to perform: [login|upload|configure]")
                .withLongOpt("action")
                .create("a");
        options.addOption(action);

        Option serverUrl = OptionBuilder.withArgName("url")
                .hasArg()
                .withDescription("URL to server")
                .withLongOpt("server-url")
                .create("h");
        options.addOption(serverUrl);

        Option classFqn = OptionBuilder.withArgName("fqn")
                .hasArg()
                .withDescription("brain class fully qualified name")
                .withLongOpt("class-fqn")
                .create("q");
        options.addOption(classFqn);

        Option classPath = OptionBuilder.withArgName("path")
                .hasArg()
                .withDescription("path to brain class")
                .withLongOpt("class-path")
                .create("f");
        options.addOption(classPath);

        Option paramKey = OptionBuilder.withArgName("name")
                .hasArg()
                .withDescription("the parameter name to be set")
                .withLongOpt("param-name")
                .create("k");
        options.addOption(paramKey);

        Option paramValue = OptionBuilder.withArgName("value")
                .hasArg()
                .withDescription("the parameter value to be set")
                .withLongOpt("param-value")
                .create("v");
        options.addOption(paramValue);
    }
}
