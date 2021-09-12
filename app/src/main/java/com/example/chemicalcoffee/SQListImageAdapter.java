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

import java.util.ArrayList;

public class SQListImageAdapter extends RecyclerView.Adapter<SQListImageAdapter.ViewHolder> {
    private ArrayList<String> captions = new ArrayList<String>();
    private ArrayList<Integer> imgID = new ArrayList<Integer>();
    private ArrayList<Integer> objectsId = new ArrayList<Integer>();
    private Listener listener;

    interface Listener{
        void onClick(int position, int id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private static int objId;

        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }

        public static void put(int id){
            objId = id;
        }
    }

    public SQListImageAdapter(ArrayList<String> captions, ArrayList<Integer> imageID, ArrayList<Integer> objectsId){
        this.captions = captions;
        this.imgID = imageID;
        this.objectsId = objectsId;
    }



    @Override
    public int getItemCount(){
        return captions.size();
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
        holder.put(objectsId.get(position));
        ImageView imageView = (ImageView) cardView.findViewById(R.id.img_obj);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imgID.get(position));
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions.get(position));
        TextView textView = (TextView) cardView.findViewById(R.id.name_obj);
        textView.setText(captions.get(position));
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (listener != null){
                    listener.onClick(position, objectsId.get(position));
                }
            }
        });
    }
}
