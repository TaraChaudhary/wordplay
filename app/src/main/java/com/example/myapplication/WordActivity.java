package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Myadapter.Myadapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WordActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] words = {"apple", "orange", "banana", "mango"};
    private EditText etWord;
    SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    TextView textView;
    private int level = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        textView =findViewById(R.id.leveltext);
        Button btnOK = findViewById(R.id.btnOK);
        Button btnClean = findViewById(R.id.btnClean);
        etWord = findViewById(R.id.etWord);
        recyclerView = findViewById(R.id.recyclerView);

//        listWords = findViewById(R.id.listWords);
//        showWord(level);
        SharedPreferences savedata = getSharedPreferences("", Context.MODE_PRIVATE);
        if (savedata.getInt("Level",0)==0) {
            showWord(level);
        }
        else {
            level=savedata.getInt("Level",0);
            showWord(level);
        }


        btnOK.setOnClickListener(this);
        btnClean.setOnClickListener(this);

//        listWords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                etWord.append(parent.getItemAtPosition(position).toString());
//            }
//        });
    }

    private Character[] shuffleWords(int level){
        char[] word = words[level].toCharArray();


        ArrayList<Character> chars = new ArrayList<>(word.length);
        for(char c: word){
            chars.add(c);
        }

        Collections.shuffle(chars);
        Character[] shuffledWord = new Character[chars.size()];

        for(int i=0; i<shuffledWord.length; i++ ){
            shuffledWord[i] = chars.get(i);
        }
        return shuffledWord;
    }

    private void showWord(int i){
        Myadapter myadapter = new Myadapter(WordActivity.this, shuffleWords(i),etWord);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(layoutManager);
        textView.setText("LEVEL : " + (i+1));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOK:
                String usr_word = etWord.getText().toString();
                if(level<words.length) {
                    if (usr_word.equals(words[level])) {
                        level++;
                        showWord(level);
                        sharedPreferences = getSharedPreferences("Game",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("Level",level);
                        editor.commit();
                        etWord.setText("");
                        Toast.makeText(WordActivity.this, "Next Level", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(WordActivity.this, "Wrong Word", Toast.LENGTH_SHORT).show();
                        etWord.setText("");
                        showWord(level);
                    }
                }
                else {
                    level=0;
                    Toast.makeText(WordActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnClean:
                etWord.getText().clear();
                showWord(level);
                break;
        }
    }
}
