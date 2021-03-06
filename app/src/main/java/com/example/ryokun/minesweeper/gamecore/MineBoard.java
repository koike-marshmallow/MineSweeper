package com.example.ryokun.minesweeper.gamecore;

import com.example.ryokun.minesweeper.drawer.MineBoardDrawable;

public class MineBoard
implements MineBoardDrawable{
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

  public MineBoard(int w, int h){
    init(w, h);
  }

  public void init(int w, int h){
    if( w > 0 && h > 0 ){
      board = new MineCell[h][w];
      for(int i=0; i<board.length; i++){
        for(int j=0; j<board[i].length; j++){
          board[i][j] = new MineCell();
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
      MineCell cell = get(x + VEC_X[i], y + VEC_Y[i]);
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
    boolean closed = false;
    for(MineCell[] row : board){
      for(MineCell cell : row){
        if( cell.isOpen() && cell.isBomb() ){
          return true;
        }else if( !cell.isOpen() && !cell.isBomb() ){
          closed = true;
        }
      }
    }
    return !closed;
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
    MineCell cell = get(x, y);
    if( cell != null && !cell.isOpen() ) {
      cell.setFlag(f0);
    }
  }

  public boolean reverseFlag(int x, int y){
    MineCell cell = get(x, y);
    if( cell != null ){
      setFlag(x, y, !cell.getFlag());
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

  public void printArray(){
    for(int i=0; i<board.length; i++){
      MineCell[] tmp = board[i];
      for(int j=0; j<tmp.length; j++){
        System.out.format("(%d,%d) - %s\n", i, j, tmp[j].toString());
      }
    }
  }

  public String toString(){
    String head = "MineBoard@";
    String cont = "";
    cont += "(" + getWidth() + ", " + getHeight() + ")";
    cont += " bomb=" + countBombCell();
    cont += " isGameover=" + isGameover();
    return head + "[" + cont + "]";
  }


  //interface extended
  public int getCellNumber(int x, int y){
    return inBound(x, y) ? get(x, y).getNumber() : -1;
  }
  public boolean getCellFlag(int x, int y){
    return inBound(x, y) ? get(x, y).getFlag() : false;
  }
  public boolean isCellBomb(int x, int y){
    return inBound(x, y) ? get(x, y).isBomb() : false;
  }
  public boolean isCellOpen(int x, int y){
    return inBound(x, y) ? get(x, y).isOpen() : false;
  }
}
