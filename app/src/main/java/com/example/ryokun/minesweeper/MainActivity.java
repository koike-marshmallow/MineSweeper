package com.example.ryokun.minesweeper;

import android.graphics.Point;
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
    static int boardWidth = 8;
    static int boardHeight = 8;
    static int bombCount = 10;

    MineBoard board;
    MineBoardViewDrawer drawer;
    TextView statusView;
    boolean openMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = new MineBoard(boardWidth, boardHeight);
        drawer = new MineBoardViewDrawer(board, this);
        statusView = (TextView)findViewById(R.id.statustext);
        openMode = true;
        View boardView = drawer.refresh();
        final LinearLayout parent = (LinearLayout) findViewById(R.id.wrapboard);
        parent.addView(boardView);
        ViewTreeObserver observer = parent.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout() {
                int parent_width = parent.getWidth();
                drawer.setCellWidth(parent_width / board.getWidth());
            }
        });



        drawer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Point pt = drawer.getPositionByView(view);

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
                statusView.setText(String.format("爆弾:%d 旗:%d",
                        board.countBombCell(), board.countFlagCell()));
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
        statusView.setText(String.format("爆弾:%d 旗:%d",
                board.countBombCell(), board.countFlagCell()));
    }

    void alert(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
