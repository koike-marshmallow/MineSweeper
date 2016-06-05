import com.example.ryokun.minesweeper.gamecore.*;
import com.example.ryokun.minesweeper.drawer.*;

public class testBoardDraw {
  public static void main(String[] args){
    MineBoard board = new MineBoard(5, 5);
    MineBoardDrawer drawer = new MineBoardDrawer(board);
    drawer.printBoard();
  }
}
