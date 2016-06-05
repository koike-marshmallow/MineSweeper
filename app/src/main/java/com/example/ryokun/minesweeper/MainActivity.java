package com.example.ryokun.minesweeper;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ryokun.minesweeper.drawer.MineBoardViewDrawer;
import com.example.ryokun.minesweeper.gamecore.BombSetter;
import com.example.ryokun.minesweeper.gamecore.MineBoard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MineBoard board = new MineBoard(5, 5);
        final MineBoardViewDrawer drawer = new MineBoardViewDrawer(board, this);
        View boardView = drawer.refresh(false);
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.parent);
        parent.addView(boardView);

        drawer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Point pt = drawer.getPositionByView(view);
                String toastMsg = pt != null ? "x=" + pt.x + ",y=" + pt.y : "null";
                Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();

                if( pt != null && !board.isGameover() ){
                    if( board.countBombCell() == 0 ){
                        BombSetter.setBomb(board, pt.x, pt.y, 5);
                        board.numbering();
                    }
                    board.open(pt.x, pt.y);
                }
                drawer.refresh(board.isGameover());
            }
        });

    }
}
