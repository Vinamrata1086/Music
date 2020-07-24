package com.example.music;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

class SongCursor extends CursorAdapter {
    public SongCursor(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.songlistdesign,parent,false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView song = view.findViewById(R.id.sname);
        TextView singer = view.findViewById(R.id.singer);
        String sname = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
        String ssinger = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
        song.setText(sname);
        singer.setText(ssinger);

    }
}
