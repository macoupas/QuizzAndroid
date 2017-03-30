package quizz.quizz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import quizz.quizz.R;

/**
 * Created by Maxime
 * MainActivity est le menu de notre application. Nous pouvons y choisir de jouer en solo ou en multijoueur via des textView cliquable.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Dans ce onCreate, nous attachons aux textView un onClickListener pour pouvoir passer en mode solo ou multi
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View oneP = (View) findViewById(R.id.onep);
        final View twoP = (View) findViewById(R.id.twop);

        oneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizzActivity.class);
                startActivity(intent);
            }
        });

        twoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
