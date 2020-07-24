package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    TextView rdur,cdur,songnm;
    ImageButton play,forward,rewind;
    SeekBar sb;
    ImageView iv;
    MediaPlayer mp;
    String snm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rdur=findViewById(R.id.rdur);
        cdur=findViewById(R.id.cdur);
        play=findViewById(R.id.play);
        forward=findViewById(R.id.imgb2);
        rewind=findViewById(R.id.imgb1);
        sb=findViewById(R.id.seekBar);
        iv=findViewById(R.id.iv);
        songnm=findViewById(R.id.song);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        String data = b.getString("songdata");
        snm=i.getStringExtra("songname");
        songnm.setText(snm);
        songnm.setSelected(true);
        mp=MediaPlayer.create(this, Uri.parse(data));
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(data);
        byte [] d=mmr.getEmbeddedPicture();
        if(d!=null){
            Bitmap bmp = BitmapFactory.decodeByteArray(d,0,d.length);
            iv.setImageBitmap(bmp);
        }
        else{
            iv.setImageResource(R.drawable.download);
        }
        rdur.setText(convertToDuration(mp.getDuration()));
        sb.setMax(mp.getDuration());
        sb.setOnSeekBarChangeListener(this);
        play.setOnClickListener(this);
        new Thread(){
            public void run()
            {
                while(mp.getCurrentPosition()!=mp.getDuration())
                {
                    sb.setProgress(mp.getCurrentPosition());
                }
            }
        }.start();
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.release();


            }
        });

    }
    String convertToDuration(long dur) {
        String d="";
        dur=dur/1000;
        d=d+dur/60+":"+dur%60;
        return d;
    }

    @Override
    public void onClick(View v) {
        if (!mp.isPlaying())
        {
            mp.start();
            play.setImageResource(R.drawable.pause);
        }
        else{
            mp.pause();
            play.setImageResource(R.drawable.play);
        }

    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            mp.seekTo(progress);
        }
        rdur.setText(convertToDuration(mp.getDuration()-mp.getCurrentPosition()));
        cdur.setText(convertToDuration(mp.getCurrentPosition()));




    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {


    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mp.seekTo(seekBar.getProgress());

    }


}