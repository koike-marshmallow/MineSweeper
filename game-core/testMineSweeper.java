import com.example.ryokun.minesweeper.gamecore.MineBoard;
import com.example.ryokun.minesweeper.gamecore.BombSetter;
import com.example.ryokun.minesweeper.drawer.MineBoardDrawer;

public class testMineSweeper{
  static MineBoard board;
  static MineBoardDrawer drawer;

  static void init(int sx, int sy){
    board = new MineBoard(sx, sy);
    drawer = new MineBoardDrawer(board);
  }

  public static void main(String[] args){
    init(5, 5);
    boolean result = BombSetter.setBomb(board, 2, 2, 5);
    board.numbering();
    drawer.printBoard(true);
    System.out.println("BombSetter: " + result + "(" + board.countBombCell() + ")");
  }
}
