package lordsoftheants.ants.game;

/**
 * @author Adrian Scripca
 */
public class GameBoardBuilder {
    public static GameBoard boardFromData(int width, int height, int[] data) {
        GameBoard result = new GameBoard(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int offset = x + y * width;
                if (data.length >= offset) {
                    result.get(x, y).setType(GameBoard.CellType.fromValue(data[offset]));
                }
            }
        }
        return result;
    }
}
