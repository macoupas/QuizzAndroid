package quizz.quizz.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import quizz.quizz.R;
import quizz.quizz.RecyclerViews.ItemClickListener;
import quizz.quizz.RecyclerViews.ResponseAdapter;
import quizz.quizz.metier.Player;
import quizz.quizz.metier.Quizz;
import quizz.quizz.metier.Response;

/**
 * Created by macoupas on 14/02/17.
 * QuestionActivity est l'activité où l'on se trouve quand on joue.
 * Elle permet d'afficher les questions et leurs réponses à sélectionnées.
 */
public class QuestionActivity extends AppCompatActivity {
    /**
     * Liste de réponses d'une question qui sera mise à jour à chaque changement de question.
     */
    private List<Response> responseList = new ArrayList<>();

    /**
     * Le quizz sélectioné par le joueur que l'on récupère dans le QuizzActivity
     */
    private Quizz quizz;

    /**
     * currentView est la view de la réponse sélectionnée dans la recyclerView
     */
    private View currentView;

    /**
     *  L'activité en dans laquelle nous sommes
     */
    private QuestionActivity questionActivity = this;

    /**
     * player1 est le joueur qui joue au quizz.
     */
    private Player player1 = new Player("Maxime");

    /**
     * responseAdapter est le ResponseAdpater de notre recyclerView de Response.
     */
    private ResponseAdapter responseAdapter;

    /**
     * recyclerView est la recyclerView qui permet de lister les reponses de la question en cours.
     */
    RecyclerView recyclerView;
    
    
    private int currentPosQuestion = 0;
    private View goodView;
    private Handler handler;
    private Runnable r;
    private boolean isClick =false;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference refData = database.getReference("QuizzList");
        final String key = getIntent().getStringExtra("quizz");

        TextView quizzName = (TextView) findViewById(R.id.quizzName);
        quizzName.setText(quizz.getName());

        handler = new Handler(Looper.getMainLooper());
        r = new Runnable() {
            @Override
            public void run() {
                next();
            }
        };

        refData.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                quizz = dataSnapshot.getValue(Quizz.class);
                recyclerView = (RecyclerView) findViewById(R.id.recycler);
                if (responseAdapter == null) {

                    responseAdapter = new ResponseAdapter(responseList, new ItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            if(isClick){
                                return;
                            }
                            isClick = true;
                            player1.setResponseSelect(responseList.get(position));
                            currentView = v;
                            verifyResponse(player1);
                            handler.postDelayed(r, 1000L);
                        }
                    });

                    recyclerView.setLayoutManager(new LinearLayoutManager(questionActivity));
                    recyclerView.setAdapter(responseAdapter);
                } else {
                    responseAdapter.notifyDataSetChanged();
                }
                updateQuestion(quizz, currentPosQuestion);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Per
     * @param quizz
     * @param pos
     */
    private void updateQuestion(Quizz quizz, int pos) {

        if(currentView != null){
            currentView.setBackgroundColor(Color.WHITE);
        }

        TextView question = (TextView) findViewById(R.id.textViewQuestion);
        question.setText(quizz.getQuestions().get(pos).getAsk());

        responseList.clear();
        responseList.addAll(quizz.getQuestions().get(pos).getResponseList());


        for(int i = 0; i<responseList.size(); i++){
            if(responseList.get(i).isTrue()){
                goodView = recyclerView.getChildAt(i);
            }
        }
    }

    /**
     * verifyResponse vérifie que la réponse sélectionnée par le joueur est bien la bonne en appelant la méthode goodResponse de p1.
     * Si c'est le cas, alors le background de la view sélectionnée deviens vert sinon rouge.
     * @param p1, le joueur auquel nous alons vérifié sa réponse.
     */
    private void verifyResponse (Player p1){
        if(!p1.goodResponse()){
            currentView.setBackgroundColor(Color.RED);
        }else{
            currentView.setBackgroundColor(Color.GREEN);
        }
    }

    /**
     * Cette méthode permet de passer d'une question à la suivante.
     * Si il n'y a plus de questions disponible alors nous allons dans QuizzActivity.
     */
    public void next(){
        if(quizz.getQuestions().size() -1 == currentPosQuestion){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Vous avec fini le quizz avec " + player1.getPoints() + " points.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(QuestionActivity.this, QuizzActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            dialog = builder.create();
            dialog.show();
        }else{
            updateQuestion(quizz, ++currentPosQuestion);
            isClick = false;
        }
    }
}
