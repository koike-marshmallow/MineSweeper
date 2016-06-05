package com.example.ryokun.minesweeper.drawer;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.ryokun.minesweeper.R;

/**
 * Created by 崇晃 on 2016/06/05.
 */
public class MineBoardViewDrawer {
    public static int[] RID_NUMBERS = {
            R.drawable.n1,
            R.drawable.n2,
            R.drawable.n3,
            R.drawable.n4,
            R.drawable.n5,
            R.drawable.n6,
            R.drawable.n7,
            R.drawable.n8
    };
    public static int RID_ZERO = R.drawable.none;
    public static int RID_BOMB = R.drawable.bomb;
    public static int RID_COVER = R.drawable.cover;
    public static int RID_FLAG = R.drawable.flag;
    public static int RID_BURN = R.drawable.burn;

    private MineBoardDrawable board;
    private Activity parentActivity;
    private GridLayout rootView;
    private ImageView[][] views;


    public MineBoardViewDrawer(MineBoardDrawable b0, Activity a0){
        parentActivity = a0;
        setBoard(b0);
    }

    void initViews(){
        rootView = new GridLayout(parentActivity);
        rootView.setRowCount(board.getHeight());
        rootView.setColumnCount(board.getWidth());
        rootView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        views = new ImageView[board.getHeight()][board.getWidth()];
        for(int i=0; i<board.getHeight(); i++){
            for(int j=0; j<board.getWidth(); j++){
                ImageView niv = new ImageView(parentActivity);
                niv.setAdjustViewBounds(true);
                rootView.addView(niv);
                views[i][j] = niv;
            }
        }
    }

    public void setBoard(MineBoardDrawable b0){
        board = b0;
        initViews();
    }

    public MineBoardDrawable getBoard(){
        return board;
    }

    public ImageView getCellView(int x, int y){
        if( y >= 0 && y < views.length ){
            if( x >= 0 && x < views[y].length ){
                return views[y][x];
            }
        }
        return null;
    }

    public int getCellImageResourceId(int x, int y, boolean open){
        if( board.isCellOpen(x, y) || open ){
            if( board.isCellBomb(x, y) ) {
                return board.isCellOpen(x, y) ? RID_BURN : RID_BOMB;
            }else{
                int num = board.getCellNumber(x, y);
                if( num == 0 ){
                    return RID_ZERO;
                }else {
                    return RID_NUMBERS[(num - 1) % RID_NUMBERS.length];
                }
            }
        }else{
            if( board.getCellFlag(x, y) ){
                return RID_FLAG;
            }else{
                return RID_COVER;
            }
        }
    }

    public View refresh(boolean allopen){
        for(int i=0; i<board.getHeight(); i++){
            for(int j=0; j<board.getWidth(); j++){
                ImageView tmp = getCellView(j, i);
                tmp.setImageResource(getCellImageResourceId(j, i, allopen));
            }
        }
        return rootView;
    }

    public View refresh(){
        return refresh(false);
    }

    public View getView(){
        return rootView;
    }

    public void setOnClickListener(View.OnClickListener listener){
        for(ImageView[] row : views){
            for(ImageView iv : row){
                iv.setOnClickListener(listener);
            }
        }
    }

    public Point getPositionByView(View view){
        for(int y=0; y<board.getHeight(); y++){
            for(int x=0; x<board.getWidth(); x++){
                if( getCellView(x, y) == view ){
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    public void setCellWidth(int width){
        for(ImageView[] row : views){
            for(ImageView iv : row){
                iv.setMaxWidth(width);
            }
        }
    }
}
