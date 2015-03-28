package lordsoftheants.ants.api.rest;

import java.util.List;

/**
 * @author Adrian Scripca
 */
public class Board {
    private int width;
    private int height;
    private List<Integer> data;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
