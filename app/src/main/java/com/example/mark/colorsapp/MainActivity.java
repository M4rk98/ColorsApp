package com.example.mark.colorsapp;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button[] colorTiles = new Button[4];
    private int[] colors = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorTiles[0] = (Button)findViewById(R.id.colorLeftTop);
        colorTiles[1] = (Button)findViewById(R.id.colorRightTop);
        colorTiles[2] = (Button)findViewById(R.id.colorLeftBottom);
        colorTiles[3] = (Button)findViewById(R.id.colorRightBottom);

        colors[0] = getResources().getColor(R.color.colorRed);
        colors[1] = getResources().getColor(R.color.colorGreen);
        colors[2] = getResources().getColor(R.color.colorYellow);
        colors[3] = getResources().getColor(R.color.colorBlue);

        for(int i = 0; i < colorTiles.length; i++)
        {
            colorTiles[i].setOnClickListener(colorChangeListener);
        }

        Button resetB = (Button) findViewById(R.id.reset);
        resetB.setOnClickListener(resetListener);

        resetTiles();

    }

    View.OnClickListener colorChangeListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Button pressedTile = (Button) v;
            int background = ((ColorDrawable)pressedTile.getBackground()).getColor();

            for(int i = 0; i < colorTiles.length; i++)
            {
                // Ha a háttérszín megegyezik valamelyik színnel a sorban következő háttérszínét
                // állítjuk be neki
                if(background == colors[i])
                {
                    // Ha elértünk az utolsó színhez akkor a legelső színt állítjuk be
                    if(i == 3)
                    {
                        pressedTile.setBackgroundColor(colors[0]);
                    } else {
                        pressedTile.setBackgroundColor(colors[i+1]);
                    }
                }
            }

            int i = 0;
            // az új szín lekérése
            background = ((ColorDrawable)pressedTile.getBackground()).getColor();

            if(checkTiles(background))
            {
                TextView texzW = (TextView)findViewById(R.id.isWin);
                texzW.setText(getResources().getString(R.string.app_event_win));
            }

        }
    };

    View.OnClickListener resetListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            resetTiles();
        }
    };

    // Ellenőrzi, hogy az aktuális csempe színe megegyezik-e a többiével
    private boolean checkTiles(int pressedBackgroundColor)
    {
        int i = 0;

        while (pressedBackgroundColor == ((ColorDrawable)colorTiles[i].getBackground()).getColor())
        {
            i++;
            if(i == 4)
            {
                return true;
            }
        }

        return false;
    }

    private void resetTiles()
    {
        /*
        Legalább egyszer beállít minden csempét random színre DE
        ellenőrzi, hogy ne lehessen a 4 csempe ugyanolyan színű különben újra generálja amíg
        nem lesznek különböző
         */
        do {
            for(int j = 0; j < colorTiles.length; j++)
            {
                colorTiles[j].setBackgroundColor(generateRandomColor(4));
            }
        } while(checkTiles(((ColorDrawable)colorTiles[0].getBackground()).getColor()));

        TextView texzW = (TextView)findViewById(R.id.isWin);
        texzW.setText(getResources().getString(R.string.app_event_default));

    }

    private int generateRandomColor(int max)
    {
        Random rn = new Random();
        return colors[rn.nextInt(max)];
    }


}
