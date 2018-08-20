package io.github.eufranio.ivittar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main extends AppCompatActivity {

    private static Random r = new Random();
    //private List<Integer> songs = new ArrayList<>();
    private static Map<String, Integer> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            for (Field f : R.raw.class.getDeclaredFields()) {
                f.setAccessible(true);
                map.put(f.getName(), f.getInt(null));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        /*songs.add(R.raw.passarmao);
        songs.add(R.raw.yuke);
        songs.add(R.raw.segurancas);
        songs.add(R.raw.bixo);
        songs.add(R.raw.eu_quero_cantar);
        songs.add(R.raw.brigadaa);
        songs.add(R.raw.entao_vaiii);
        songs.add(R.raw.aii);
        songs.add(R.raw.ressucitaa);
        songs.add(R.raw.to_vivendo_um_sonhoo);
        songs.add(R.raw.eeeei);
        songs.add(R.raw.na_cara_de_vcs);
        songs.add(R.raw.simbora);
        songs.add(R.raw.vaii);*/

        Button button = this.findViewById(R.id.btn_pabllo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> songs = new ArrayList<>(map.values());
                final MediaPlayer mp = MediaPlayer.create(Main.this, songs.get(r.nextInt(songs.size())));
                mp.start();
            }
        });

        Button lista = this.findViewById(R.id.btn_lista);
        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, Lista.class);
                startActivity(i);
            }
        });
    }

    public static int getSong(String name) {
        return map.get(name);
    }

    public static List<String> getSongs() {
        return new ArrayList<>(map.keySet());
    }

}
