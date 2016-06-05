package com.example.ryokun.minesweeper;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ryokun.minesweeper.drawer.MineBoardViewDrawer;
import com.example.ryokun.minesweeper.gamecore.BombSetter;
import com.example.ryokun.minesweeper.gamecore.MineBoard;

public class MainActivity extends AppCompatActivity {
    static int boardWidth = 5;
    static int boardHeight = 5;
    static int bombCount = 5;

    MineBoard board;
    MineBoardViewDrawer drawer;
    boolean openMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = new MineBoard(boardWidth, boardHeight);
        drawer = new MineBoardViewDrawer(board, this);
        openMode = true;
        View boardView = drawer.refresh();
        LinearLayout parent = (LinearLayout) findViewById(R.id.wrapboard);
        parent.addView(boardView);

        drawer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Point pt = drawer.getPositionByView(view);
                String toastMsg = pt != null ? "x=" + pt.x + ",y=" + pt.y : "null";
                Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();

                if( pt != null && !board.isGameover() ){
                    if( openMode ) {
                        if (board.countBombCell() == 0) {
                            BombSetter.setBomb(board, pt.x, pt.y, bombCount);
                            board.numbering();
                        }
                        board.open(pt.x, pt.y);
                    }else{
                        board.reverseFlag(pt.x, pt.y);
                    }
                }
                drawer.refresh(board.isGameover());
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
                retryGame();
            }
        });

    }

    void retryGame(){
        board.init(boardWidth, boardHeight);
        drawer.refresh();
    }
}
