package com.example.chemicalcoffee;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SQListImageAdapter extends RecyclerView.Adapter<SQListImageAdapter.ViewHolder> {
    private String[] captions;
    private String[] from;
    private int[] imgID;
    private Cursor cursor;
    private Listener listener;

    interface Listener{
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    public SQListImageAdapter(Cursor cursor, String[] from){
        this.from = from;
        this.cursor = cursor;
        getItemValue();
    }


    private void getItemValue(){
        int[] imgId = new int[cursor.getCount()];
        String[] textValue = new String[cursor.getCount()];
        if(cursor.moveToFirst()){
            imgId[0] = cursor.getInt(cursor.getColumnIndex(from[1]));
            textValue[0] = cursor.getString(cursor.getColumnIndex(from[0]));
        }

        for (int i = 1; i < cursor.getCount(); i++){
            cursor.moveToNext();
            imgId[i] = cursor.getInt(cursor.getColumnIndex(from[1]));
            textValue[i] = cursor.getString(cursor.getColumnIndex(from[0]));
        }

        captions = textValue;
        imgID = imgId;
    }

    @Override
    public int getItemCount(){
        return captions.length;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public SQListImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_object_image, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.img_obj);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imgID[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);
        TextView textView = (TextView) cardView.findViewById(R.id.name_obj);
        textView.setText(captions[position]);
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });
    }
}
