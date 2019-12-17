package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WordActivity extends AppCompatActivity {
    ListView listView;
    TextView textView;
    Button button, clr;
    int level=0;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String un,pw;
    String[] words={"apple","orange","grapes"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        sp = getApplicationContext().getSharedPreferences("mysp",MODE_PRIVATE);
        editor = sp.edit();


        listView = findViewById(R.id.wordlist);
        textView = findViewById(R.id.ans);
        button = findViewById(R.id.btn);
        clr=findViewById(R.id.clear);



        ShowWord(level);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr_word=textView.getText().toString();
                if (usr_word.equals(words[level])) {
                    level++;
                    ShowWord(level);
                    textView.setText("");
                }
                else {
                    Toast.makeText(WordActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
            }
        });

    }
    public void ShowWord(int i){
        Character[] word=shuffleWord(words[i]);

        ArrayAdapter<Character> adapter=new ArrayAdapter<Character>(this, R.layout.spinner_values,word);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String letter=parent.getItemAtPosition(position).toString();
                textView.append(letter);
            }
        });

    }

    private Character[] shuffleWord(String word)
    {
        ArrayList<Character> chars=new ArrayList<>(word.length());
        for(char c:word.toCharArray())
        {
            chars.add(c);

        }
        Collections.shuffle(chars);
        Character[] shuffeled=new Character[chars.size()];
        for (int i=0;i<shuffeled.length; i++)
        {
            shuffeled[i]=chars.get(i);

        }
        //  String suffeledword=new String(shuffeled);
        return shuffeled;

    }
}
