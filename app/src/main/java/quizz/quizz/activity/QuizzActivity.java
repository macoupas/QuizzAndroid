package quizz.quizz.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import quizz.quizz.R;
import quizz.quizz.RecyclerViews.ItemClickListener;
import quizz.quizz.RecyclerViews.QuizzAdapter;
import quizz.quizz.metier.Question;
import quizz.quizz.metier.Quizz;
import quizz.quizz.metier.Response;

public class QuizzActivity extends AppCompatActivity{

    private List<Quizz> quizzList = new ArrayList<>();
    private String quizzKey;
    private View currentView;
    private QuizzAdapter quizzAdapter;
    private RecyclerView recyclerView;
    SharedPreferences.Editor editor;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference refData = database.getReference("QuizzList");
        //ajouterQuizz(refData);
        //database.getReference("test").setValue("hello");
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        refData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Quizz q = dataSnapshot.getValue(Quizz.class);
                quizzList.add(q);
                quizzAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Quizz q = dataSnapshot.getValue(Quizz.class);
                String qKey = dataSnapshot.getKey();
                q.setKey(dataSnapshot.getKey());
                quizzAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String qKey = dataSnapshot.getKey();
                quizzAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Quizz q = dataSnapshot.getValue(Quizz.class);
                String qKey = dataSnapshot.getKey();
                q.setKey(dataSnapshot.getKey());
                quizzAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final View button = findViewById(R.id.button);
        button.setEnabled(false);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        quizzAdapter = new QuizzAdapter(quizzList, new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d("quizzKey", quizzList.get(position).getKey());
                quizzKey = quizzList.get(position).getKey();
                editor.putInt("currentView", position);
                editor.putString("quizzKey", quizzKey);
                editor.commit();

                if(currentView != null) {
                    currentView.setBackgroundColor(Color.WHITE);
                }
                v.setBackgroundColor(Color.CYAN);
                currentView = v;
                button.setEnabled(true);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(quizzAdapter);

        pos = sharedPref.getInt("currentView", -1);
        if(pos != -1){
            currentView = recyclerView.findViewHolderForAdapterPosition(pos).itemView;
            currentView.setBackgroundColor(Color.CYAN);
            quizzKey = sharedPref.getString("quizzKey", null);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizzActivity.this, QuestionActivity.class);
                Intent quizz = intent.putExtra("quizz", quizzKey);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ajouterQuizz(DatabaseReference d) {
        List<Question> qList = new ArrayList<>();
        List<Response> rList = new ArrayList<>();
        rList.add(new Response("France", false));
        rList.add(new Response("Allemagne", false));
        rList.add(new Response("Angleterre", false));
        rList.add(new Response("Espagne", true));
        qList.add(new Question("Quel pays a Madrid comme capital ?",rList));
        final Quizz qu = new Quizz(qList, "Pays");
        String key = d.push().getKey();
        qu.setKey(key);
        d.child(key).setValue(qu);
        addQuestion(d, qu);
    }

    private  void addQuestion(DatabaseReference d, Quizz q) {
        List<Response> rList = new ArrayList<>();
        rList.add(new Response("France", true));
        rList.add(new Response("Allemagne", false));
        rList.add(new Response("Angleterre", false));
        rList.add(new Response("Espagne", false));
        Question qu = new Question("Quel pays a Paris comme capitale ?", rList);
        d.child(q.getKey()).child("questions").child(String.valueOf(q.getQuestions().size())).setValue(qu);
    }
}
