package io.github.eufranio.ivittar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Lista extends AppCompatActivity {

    private String songName;
    private List<String> songs;
    private ArrayAdapter adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        list = this.findViewById(R.id.lista);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                int song = Main.getSong(name);
                final MediaPlayer mp = MediaPlayer.create(Lista.this, song);
                mp.start();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                songName = (String) adapter.getItemAtPosition(position);
                return false;
            }
        });

        list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuItem menuDelete = menu.add("Compartilhar");
                menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent().setAction(Intent.ACTION_SEND).setType("audio/*");
                        File songFile = new File(getFilesDir(), songName + ".mp3");
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(songFile));
                        startActivity(Intent.createChooser(intent, "Comapartilhar"));
                        return true;
                    }
                });
            }
        });

    }

    private void loadSongs() {
        this.songs = Main.getSongs();
        if (this.songs != null) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.songs);
            this.list.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.loadSongs();
    }
}
