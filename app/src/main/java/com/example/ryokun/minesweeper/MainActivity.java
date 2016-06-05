package com.example.ryokun.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout board = new GridLayout(this);
        board.setColumnCount(5);
        board.setRowCount(5);
        for(int i=0; i<25; i++){
            ImageView view = new ImageView(this);
            view.setImageResource(R.drawable.abc_ic_star_black_36dp);
            board.addView(view);
        }

        RelativeLayout p = (RelativeLayout)findViewById(R.id.parent);
        p.addView(board);

        View testv = findViewById(R.id.testview);
        testv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "clicked!", Toast.LENGTH_SHORT).show();
        }});
    }
}
