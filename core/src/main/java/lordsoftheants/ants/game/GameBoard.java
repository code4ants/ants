package lordsoftheants.ants.game;

import lordsoftheants.ants.api.AntShell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Adrian Scripca
 */
public class GameBoard {

    private List<Cell> cells;
    private int width;
    private int height;

    public GameBoard(int width, int height) {
        cells = new ArrayList<>(width * height);
        this.width = width;
        this.height = height;

        // allocate
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                cells.add(new Cell(x, y));

        clear();
    }

    private void clear() {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                get(x, y).ants.clear();
    }

    public void set(int x, int y, Cell value) {
        cells.set(y * width + x, value);
    }

    public Cell get(int x, int y) {
        return cells.get(y * width + x);
    }

    public Cell getSpawingPointForPlayerSlot(int slot) {
        CellType desiredCellType = CellType.fromValue(CellType.SPAWNING_POINT_1.getValue() + slot);
        for (Cell cell : cells) {
            if (cell.getType() == desiredCellType) {
                return cell;
            }
        }
        return null;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public enum CellType {
        EMPTY(0),
        BORDER(1),
        WALL(2),
        SPAWNING_POINT_1(3),
        SPAWNING_POINT_2(4),
        SPAWNING_POINT_3(5),
        SPAWNING_POINT_4(6),
        SPAWNING_POINT_5(7),
        SPAWNING_POINT_6(8);

        private int value;

        CellType(int value) {
            this.value = value;
        }

        public static CellType fromValue(int value) {
            for (CellType type : values()) {
                if (type.getValue() == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Could not find cell type with value " + value);
        }

        public int getValue() {
            return value;
        }
    }

    public static class Cell {
        public CellType type = CellType.EMPTY;
        public List<AntShell> ants = new LinkedList<>();
        public int x;
        public int y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public CellType getType() {
            return type;
        }

        public void setType(CellType type) {
            this.type = type;
        }

        public void add(AntShell ant) {
            ants.add(ant);
        }

        public void remove(AntShell ant) {
            ants.remove(ant);
        }

        public List<AntShell> getAnts() {
            return ants;
        }
    }
}
