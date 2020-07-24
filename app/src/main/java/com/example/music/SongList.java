package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SongList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Cursor c;
    ListView lv;
    SongCursor sc;
    ArrayList<String> sdata = new ArrayList<>();
    ArrayList<String> sname = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        lv=findViewById(R.id.lv);
        getData();
        lv.setOnItemClickListener(this);
    }
    void getData(){
        c=getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
        sc=new SongCursor(this,c,0);
        lv.setAdapter(sc);
        Cursor c1 = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
        while (c1.moveToNext()){
            int i =c1.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            int j = c1.getColumnIndex(MediaStore.Audio.Media.DATA);
            sdata.add(c1.getString(j));
            sname.add(c1.getString(i));
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("songname",sname.get(position));
        intent.putExtra("songdata",sdata.get(position));
        startActivity(intent);

    }
}