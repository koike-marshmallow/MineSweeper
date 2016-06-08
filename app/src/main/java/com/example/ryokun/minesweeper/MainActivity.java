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
    LevelConfig[] levels = {
            new LevelConfig("easy1", "8 x 8", 8, 8, 10),
            new LevelConfig("easy2", "12 x 12", 12, 12, 20),
            new LevelConfig("normal1", "16 x 16", 16, 16, 40)
    };

    MineBoard board;
    MineBoardViewDrawer drawer;

    TextView statusView;
    LinearLayout boardWrapper;
    int boardDisplayWidth;

    int level = 0;
    boolean openMode;

    void initGame(){
        board = new MineBoard(levels[level].columnCount, levels[level].rowCount);
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
                    BombSetter.setBomb(board, pos.x, pos.y, levels[level].bombCount);
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
                String[] labels = new String[levels.length];
                for(int i=0; i<levels.length; i++){
                    labels[i] = levels[i].label;
                }
                new AlertDialog.Builder(getActivity())
                        .setTitle("難易度を選択")
                        .setItems(labels, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if( which >= 0 && which < levels.length ){
                                    level = which;
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

    class LevelConfig{
        public String key;
        public String label;
        public int rowCount;
        public int columnCount;
        public int bombCount;

        public LevelConfig(String k, String l, int r, int c, int b){
            key = k;
            label = l;
            rowCount = r;
            columnCount = c;
            bombCount = b;
        }
    }
}
