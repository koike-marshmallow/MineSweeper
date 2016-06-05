package com.example.ryokun.minesweeper.drawer;

public class MineBoardDrawer {
  public static char[] NUMBERS = {'１', '２', '３', '４', '５', '６', '７', '８'};
  public static char ZERO = '０';
  public static char BOMB = '＊';
  public static char FLAG = '＃';
  public static char UNOPENED = '■';

  private MineBoardDrawable board;

  public MineBoardDrawer(MineBoardDrawable b0){
    setBoard(b0);
  }

  public void setBoard(MineBoardDrawable b0){
    board = b0;
  }

  public MineBoardDrawable getBoard(){
    return board;
  }

  char getCellCharactor(int x, int y, boolean allopen){
    if( board.isCellOpen(x, y) || allopen ){
      if( board.isCellBomb(x, y) ){
        return BOMB;
      }
      int num = board.getCellNumber(x, y);
      if( num == 0 ){
        return ZERO;
      }
      return NUMBERS[(num - 1) % NUMBERS.length];
    }
    if( board.getCellFlag(x, y) ){
      return FLAG;
    }
    return UNOPENED;
  }

  public String generateString(boolean allopen){
    StringBuffer buffer = new StringBuffer();
    //header
    buffer.append("MS |");
    String hr = "---+";
    for(int i=0; i<board.getWidth(); i++){
      buffer.append(String.format(" %2d", i));
      hr += "---";
    }
    buffer.append("\n" + hr + "\n");
    //content
    for(int i=0; i<board.getHeight(); i++){
      buffer.append(String.format("%2d |", i));
      for(int j=0; j<board.getWidth(); j++){
        buffer.append(' ');
        buffer.append(getCellCharactor(j, i, allopen));
      }
      buffer.append("\n");
    }

    return buffer.toString();
  }

  public void printBoard(boolean allopen){
    String boardstr = generateString(allopen);
    System.out.print(boardstr);
  }

  public void printBoard(){
    printBoard(false);
  }
}
