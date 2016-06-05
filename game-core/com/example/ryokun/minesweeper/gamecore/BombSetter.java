package com.example.ryokun.minesweeper.gamecore;

import java.awt.Point;

public class BombSetter{
  public static int OVERFLOW = 10;

  static boolean isExclude(MineBoard board, Point target, Point exclude, boolean expand){
    if( exclude != null && exclude.equals(target) ){
      return true;
    }else{
      if( expand ){
        for(int i=0; i<MineBoard.VEC_LENGTH; i++){
          Point tmp = new Point(exclude);
          tmp.translate(MineBoard.VEC_X[i], MineBoard.VEC_Y[i]);
          if( board.inBound((int)tmp.getX(), (int)tmp.getY()) ){
            if( target.equals(tmp) ){
              return true;
            }
          }
        }
      }else{
        return false;
      }
    }
    return false;
  }

  static Point randomSetPoint(MineBoard board, Point exclude){
    Point point = new Point();
    int cnt = 0;
    do{
      if( cnt > OVERFLOW ) return null;
      cnt++;
      point.x = (int)(Math.random() * board.getWidth());
      point.y = (int)(Math.random() * board.getHeight());
    }while(
      isExclude(board, point, exclude, true) ||
      board.isCellBomb((int)point.getX(), (int)point.getY()) );
    return point;
  }

  public static boolean setBomb(MineBoard board, int ex, int ey){
    Point exclude = board.inBound(ex, ey) ? new Point(ex, ey) : null;
    Point sp = randomSetPoint(board, exclude);
    if( sp != null ){
      board.setBomb((int)sp.getX(), (int)sp.getY());
      return true;
    }else{
      return false;
    }
  }

  public static boolean setBomb(MineBoard board){
    return setBomb(board, -1, -1);
  }

  public static boolean setBomb(MineBoard board, int ex, int ey, int cnt){
    for(int i=0; i<cnt; i++){
      if( !setBomb(board, ex, ey) ){
        return false;
      }
    }
    return true;
  }

  public static boolean setBomb(MineBoard board, int cnt){
    return setBomb(board, -1, -1, cnt);
  }
}
