package com.example.ryokun.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ryokun.minesweeper.drawer.MineBoardViewDrawer;
import com.example.ryokun.minesweeper.gamecore.MineBoard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MineBoard board = new MineBoard(5, 5);
        MineBoardViewDrawer drawer = new MineBoardViewDrawer(board, this);

        View boardView = drawer.refresh(false);
        RelativeLayout parent = (RelativeLayout)findViewById(R.id.parent);
        parent.addView(boardView);

    }
}
