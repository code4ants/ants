package lordsoftheants.ants.game;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Adrian Scripca
 */
public class MathUtilsTest {

    @Test
    public void test_overlapping_lines_intersect() {
        int p0x, p0y;
        int p1x, p1y;
        int p2x, p2y;
        int p3x, p3y;

        // first line segment (0,0) -> (1,0)
        p0x = 0; p0y = 0;
        p1x = 1; p1y = 0;

        // second line segment (1,0) -> (0,0)
        p2x = 1; p2y = 0;
        p3x = 0; p3y = 0;

        // They should be intersecting because they overlap
        assertThat(MathUtils.doLinesIntersect(p0x, p0y, p1x, p1y, p2x, p2y, p3x, p3y), is(true));

    }

    @Test
    public void test_parallel_lines_do_not_intersect() {
        int p0x, p0y;
        int p1x, p1y;
        int p2x, p2y;
        int p3x, p3y;

        // first line segment (0,0) -> (1,0)
        p0x = 0; p0y = 0;
        p1x = 1; p1y = 0;

        // second line segment (0,1) -> (1,1)
        p2x = 0; p2y = 1;
        p3x = 1; p3y = 1;

        // They should not be intersecting because they are parallel but not collinear
        assertThat(MathUtils.doLinesIntersect(p0x, p0y, p1x, p1y, p2x, p2y, p3x, p3y), is(false));
    }

    @Test
    public void test_collinear_but_disjoint_do_not_intersect() {
        int p0x, p0y;
        int p1x, p1y;
        int p2x, p2y;
        int p3x, p3y;

        // first line segment (0,0) -> (1,0)
        p0x = 0; p0y = 0;
        p1x = 1; p1y = 0;

        // second line segment (2,0) -> (3,0)
        p2x = 2; p2y = 0;
        p3x = 3; p3y = 0;

        // They should not be intersecting because even though they are collinear they are disjoint
        assertThat(MathUtils.doLinesIntersect(p0x, p0y, p1x, p1y, p2x, p2y, p3x, p3y), is(false));
    }


    @Test
    public void test_perpendicullar_lines_intersect() {
        int p0x, p0y;
        int p1x, p1y;
        int p2x, p2y;
        int p3x, p3y;

        // first line segment (0,0) -> (1,0)
        p0x = 0; p0y = 0;
        p1x = 1; p1y = 0;

        // second line segment (1,1) -> (1,0)
        p2x = 1; p2y = 1;
        p3x = 1; p3y = 0;

        // They should be intersecting
        assertThat(MathUtils.doLinesIntersect(p0x, p0y, p1x, p1y, p2x, p2y, p3x, p3y), is(true));
    }

    @Test
    public void test_crossing_lines_intersect() {
        int p0x, p0y;
        int p1x, p1y;
        int p2x, p2y;
        int p3x, p3y;

        // first line segment (0,0) -> (1,1)
        p0x = 0; p0y = 0;
        p1x = 1; p1y = 1;

        // second line segment (1,0) -> (0,1)
        p2x = 1; p2y = 0;
        p3x = 0; p3y = 1;

        // They should be intersecting because they cross
        assertThat(MathUtils.doLinesIntersect(p0x, p0y, p1x, p1y, p2x, p2y, p3x, p3y), is(true));
    }
}