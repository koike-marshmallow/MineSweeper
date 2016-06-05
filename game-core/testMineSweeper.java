import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import com.example.ryokun.minesweeper.gamecore.MineBoard;
import com.example.ryokun.minesweeper.gamecore.BombSetter;
import com.example.ryokun.minesweeper.drawer.MineBoardDrawer;

public class testMineSweeper{
  static boolean logging = true;

  static int bombCount = 10;
  static int boardWidth = 8;
  static int boardHeight = 8;

  static MineBoard board;
  static MineBoardDrawer drawer;

  static void log(String mod, String msg){
    if( logging ){
      System.out.println(
        (mod != null ? (mod + ": ") : "") + msg
      );
    }
  }

  static void init(int sx, int sy){
    board = new MineBoard(sx, sy);
    drawer = new MineBoardDrawer(board);
    log("init", "createMineBoard => " + board.toString());
  }

  static void setBomb(int ex, int ey, int cnt){
    if( BombSetter.setBomb(board, ex, ey, cnt) ){
      log("setBomb", "bomb set complete. bombcount=" + board.countBombCell());
    }else{
      int bc = board.countBombCell();
      log("setBomb", "bomb set FAILED. bombcount=" + bc + ", required=" + cnt);
    }
    board.numbering();
    log("setBomb", "board numbering finished");
  }

  static int openCell(int x, int y){
    if( board.countBombCell() == 0 ){
      setBomb(x, y, bombCount);
    }
    int result = board.open(x, y);
    log("open", String.format("cell open (%d, %d) code=%d", x, y, result));
    return result;
  }

  public static void main(String[] args)
  throws IOException{
    init(boardWidth, boardHeight);

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    while( !board.isGameover() ){
      drawer.printBoard();
      System.out.format("bomb:%d  flag:%d\n",
        board.countBombCell(), board.countFlagCell());

      System.out.print("> ");
      String input = reader.readLine();

      Scanner scanner = new Scanner(input);
      String command = scanner.hasNext() ? scanner.next() : "";
      int x = scanner.hasNextInt() ? scanner.nextInt() : -1;
      int y = scanner.hasNextInt() ? scanner.nextInt() : -1;

      if( command.equals("quit") ){
        break;

      }else if( command.equals("open") ){
        int result = openCell(x, y);
        switch( result ){
          case MineBoard.OPENED:
            System.out.println("既に開かれています");
            break;
          case MineBoard.FLAGED:
            System.out.println("旗が立てられています");
            break;
          case MineBoard.OUTOFBOUNDS:
            System.out.println("位置が不正です");
            break;
        }

      }else if( command.equals("flag") ){
        if( board.inBound(x, y) ){
          boolean result = board.reverseFlag(x, y);
          log("setFlag", String.format("set flag (%d, %d) flag=", x ,y) +  result);
        }else{
          System.out.println("位置が不正です");
        }
      }
    }

    System.out.println("GAME OVER");
    drawer.printBoard(true);

  }
}
