package com.example.javaht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoPageActivity extends AppCompatActivity {

    private TextView info;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        info = findViewById(R.id.infoText);
        backBtn = findViewById(R.id.infoPageBackButton);

        info.setText("       - Pelin tarkoituksena on kehittää omien hahmojen tasoa taistelemalla pelin luomia botteja vastaan. \n" +
                "\n" +
                "- Hahmoja voi luoda \"lisää hahmo\"-napista, joka kysyy uuden hahmon nimen. \n" +
                "\n" +
                "- Hahmoilla voi taistella joko botteja tai omia hahmoja vastaan. \n" +
                "\n" +
                "- Voitetun taistelun jälkeen hahmoa voi vaihtaa tai jatkaa samalla. \n" +
                "\n" +
                "- Kuolleet hahmot löytyvät hautausmaalta. \n" +
                "\n" +
                "Tekijät: Lauri, Konsta ja Tuomas");

    }

    public void changeLayoutToHomePage(View view)   {
        Intent intent = new Intent(InfoPageActivity.this, MainActivity.class);
        startActivity(intent);
    }
}