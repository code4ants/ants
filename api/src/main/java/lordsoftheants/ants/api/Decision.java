package lordsoftheants.ants.api;

/**
 * @author Adrian Scripca
 */
public enum Decision {
    GO_LEFT(0),
    GO_RIGHT(1),
    GO_UP(2),
    GO_DOWN(3);

    private int value;

    Decision(int value) {
        this.value = value;
    }

    public static Decision fromValue(int value) {
        for (Decision d : values()) {
            if (d.getValue() == value) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown decision value " + value);
    }

    public int getValue() {
        return value;
    }
}
