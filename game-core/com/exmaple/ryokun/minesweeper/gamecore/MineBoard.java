package com.example.ryokun.minesweeper.gamecore;

public class MineBoard{
  static int[] VEC_X = {-1,  0,  1,  1,  1,  0, -1, -1};
  static int[] VEC_Y = {-1, -1, -1,  0,  1,  1,  1,  0};
  static int VEC_LENGTH = Math.min(VEC_X.length, VEC_Y.length);

  public static final int CONTINUE = 101;
  public static final int OPENED = 102;
  public static final int FLAGED = 106;
  public static final int BURNED = 103;
  public static final int GAMEOVER = 104;
  public static final int OUTOFBOUNDS = 105;

  private MineCell[][] board;

  public void init(int w, int h){
    if( w > 0 && h > 0 ){
      board = new MineCell[h][w];
      for(MineCell[] row : board){
        for(MineCell cell : row){
          cell = new MineCell();
        }
      }
    }
  }

  public int getHeight(){
    return board.length;
  }

  public int getWidth(){
    return board[0].length;
  }

  public boolean inBound(int x, int y){
    if( y >= 0 && y < board.length ){
      if( x >= 0 && x < board[y].length ){
        return true;
      }
    }
    return false;
  }

  public MineCell get(int x, int y){
    if( inBound(x, y) ){
      return board[y][x];
    }
    return null;
  }

  public void setBomb(int x, int y){
    if( inBound(x, y) ){
      get(x, y).setBomb(true);
    }
  }

  int countAroundBombCell(int x, int y){
    int count = 0;
    for(int i=0; i<VEC_LENGTH; i++){
      MineCell cell = get(x + VEC_X[i], y + VEC_X[i]);
      if( cell != null && cell.isBomb() ){
        count++;
      }
    }
    return count;
  }

  public void numbering(){
    for(int y=0; y<getHeight(); y++){
      for(int x=0; x<getWidth(); x++){
        MineCell cell = get(x, y);
        if( !cell.isBomb() ){
          cell.setNumber(countAroundBombCell(x, y));
        }else{
          cell.setNumber(-1);
        }
      }
    }
  }

  public boolean isBurned(){
    for(MineCell[] row : board){
      for(MineCell cell : row){
        if( cell.isOpen() && cell.isBomb() ){
          return true;
        }
      }
    }
    return false;
  }

  public boolean isGameover(){
    for(MineCell[] row : board){
      for(MineCell cell : row){
        if( cell.isOpen() && cell.isBomb() ){
          return true;
        }else if( !cell.isOpen() && !cell.isBomb() ){
          return false;
        }
      }
    }
    return true;
  }

  void openAroundCell(int x, int y){
    for(int i=0; i<VEC_LENGTH; i++){
      int tx = x + VEC_X[i];
      int ty = y + VEC_Y[i];
      if( inBound(tx, ty) ){
        open(tx, ty);
      }
    }
  }

  public int open(int x, int y){
    int code;
    MineCell target = get(x, y);

    if( target == null ) return OUTOFBOUNDS;
    if( target.isOpen() ) return OPENED;
    if( target.getFlag() ) return FLAGED;

    target.open();
    if( target.isBomb() ) return BURNED;
    if( target.getNumber() == 0 ){
      openAroundCell(x, y);
    }

    if( isGameover() ) return GAMEOVER;

    return CONTINUE;
  }

  public void setFlag(int x, int y, boolean f0){
    if( inBound(x, y) ){
      get(x, y).setFlag(f0);
    }
  }

  public boolean reverseFlag(int x, int y){
    MineCell cell = get(x, y);
    if( cell != null ){
      cell.setFlag(!cell.getFlag());
      return cell.getFlag();
    }
    return false;
  }

  public int countBombCell(){
    int count = 0;
    for(MineCell[] row : board){
      for(MineCell cell : row){
        if( cell.isBomb() ){
          count++;
        }
      }
    }
    return count;
  }

  public int countFlagCell(){
    int count = 0;
    for(MineCell[] row : board){
      for(MineCell cell : row){
        if( cell.getFlag() ){
          count++;
        }
      }
    }
    return count;
  }

  public int countOpenedCell(){
    int count = 0;
    for(MineCell[] row : board){
      for(MineCell cell : row){
        if( cell.isOpen() ){
          count++;
        }
      }
    }
    return count;
  }
}
