package com.example.ryokun.minesweeper.drawer;

public interface MineBoardDrawable {
  public int getHeight();
  public int getWidth();
  public int getCellNumber(int x, int y);
  public boolean getCellFlag(int x, int y);
  public boolean isCellBomb(int x, int y);
  public boolean isCellOpen(int x, int y);
  public boolean isGameover();
}
