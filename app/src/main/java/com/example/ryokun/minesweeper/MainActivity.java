package com.example.ryokun.minesweeper;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryokun.minesweeper.drawer.MineBoardViewDrawer;
import com.example.ryokun.minesweeper.gamecore.BombSetter;
import com.example.ryokun.minesweeper.gamecore.MineBoard;

public class MainActivity extends AppCompatActivity {
    int boardWidth = 8;
    int boardHeight = 8;
    int bombCount = 10;

    MineBoard board;
    MineBoardViewDrawer drawer;

    TextView statusView;
    LinearLayout boardWrapper;
    int boardDisplayWidth;

    boolean openMode;

    void initGame(){
        board = new MineBoard(boardWidth, boardHeight);
        drawer = new MineBoardViewDrawer(board, this);
        drawer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                boardCellPressed(view);
            }
        });
        drawer.setCellWidth(boardDisplayWidth / board.getWidth());
        boardWrapper.removeAllViews();
        boardWrapper.addView(drawer.refresh());
    }

    void boardCellPressed(View view){
        Point pos = drawer.getPositionByView(view);
        if( pos != null && !board.isGameover() ) {
            if (openMode) {
                if( board.countBombCell() == 0 ){
                    BombSetter.setBomb(board, pos.x, pos.y, bombCount);
                    board.numbering();
                }
                board.open(pos.x, pos.y);
            } else {
                board.reverseFlag(pos.x, pos.y);
            }
        }
        drawer.refresh(board.isGameover());
        statusView.setText(String.format("爆弾: %d, 旗: %d",
                board.countBombCell(), board.countFlagCell()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusView = (TextView)findViewById(R.id.statustext);
        boardWrapper = (LinearLayout) findViewById(R.id.wrapboard);

        initGame();
        openMode = true;

        ViewTreeObserver observer = boardWrapper.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                boardDisplayWidth = boardWrapper.getWidth();
                drawer.resizeCellWidth(boardDisplayWidth);
            }
        });

        final ImageButton modeBtn = (ImageButton)findViewById(R.id.modebtn);
        modeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if( openMode ){
                    modeBtn.setImageResource(R.drawable.bicon_flag);
                    openMode = false;
                }else{
                    modeBtn.setImageResource(R.drawable.bicon_open);
                    openMode = true;
                }
            }
        });

        final Button retryBtn = (Button)findViewById(R.id.retrybtn);
        retryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                initGame();
            }
        });

        final Button levelBtn = (Button)findViewById(R.id.levelbtn);
        levelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final String[] items = {"8 x 8", "12 x 12", "16 x 16"};
                final int[] mWidth = {8, 12, 16};
                final int[] mHeight = {8, 12, 16};
                final int[] mBomb = {10, 20, 40};
                new AlertDialog.Builder(getActivity())
                        .setTitle("難易度を選択")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if( which >= 0 && which < Math.min(mWidth.length, mHeight.length) ){
                                    boardWidth = mWidth[which];
                                    boardHeight = mHeight[which];
                                    bombCount = mBomb[which];
                                    initGame();
                                }
                            }
                        })
                        .show();
            }
        });

    }

    void alert(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    Activity getActivity(){
        return this;
    }
}
