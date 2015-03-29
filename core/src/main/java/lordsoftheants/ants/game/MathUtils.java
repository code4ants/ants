package lordsoftheants.ants.game;

/**
 * @author Adrian Scripca
 */
public class MathUtils {

    public static final float EPSILON = 0.000001f;

    /**
     * @return <code>true</code> if the line segments intersect, <code>false</code> otherwise.
     */
    public static boolean doLinesIntersect(
            int p0x, int p0y, int p1x, int p1y,
            int p2x, int p2y, int p3x, int p3y) {

        float s1x, s1y, s2x, s2y;
        s1x = p1x - p0x;
        s1y = p1y - p0y;
        s2x = p3x - p2x;
        s2y = p3y - p2y;

        float determinant = -s2x * s1y + s1x * s2y;
        if (almostEqual(determinant, 0)) {
            // may be collinear or parallel
            // if they are collinear then they share points
            if ((p0x == p2x && p0y == p2y) || (p0x == p3x && p0y == p3y)) {
                return true;
            }
            return false;
        }

        float s, t;
        s = (-s1y * (p0x - p2x) + s1x * (p0y - p2y)) / determinant;
        t = (s2x * (p0y - p2y) - s2y * (p0x - p2x)) / determinant;
        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            // we could calculate here the point of intersection if really needed
            return true;
        }

        return false;
    }

    private static boolean almostEqual(float v1, float v2) {
        return Math.abs(v1 - v2) < EPSILON;
    }
}
