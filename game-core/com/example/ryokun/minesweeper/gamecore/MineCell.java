package com.example.ryokun.minesweeper.gamecore;

public class MineCell {
  private int number;
  private boolean isBomb;
  private boolean flag;
  private boolean isOpen;

  public MineCell(){
    number = 0;
    isBomb = false;
    isOpen = false;
    flag = false;
  }

  public void setNumber(int n0){
    number = n0;
  }

  public void setBomb(boolean b0){
    isBomb = b0;
  }

  public void setFlag(boolean f0){
    flag = f0;
  }

  public void open(){
    isOpen = true;
  }

  public void close(){
    isOpen = false;
  }

  public int getNumber(){
    return number;
  }

  public boolean isBomb(){
    return isBomb;
  }

  public boolean getFlag(){
    return flag;
  }

  public boolean isOpen(){
    return isOpen;
  }
}
