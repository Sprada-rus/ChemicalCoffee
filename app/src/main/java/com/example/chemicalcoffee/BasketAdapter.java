package com.example.chemicalcoffee;

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

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {
    private ArrayList<String> captions = new ArrayList<String>();
    private ArrayList<Integer> imgId = new ArrayList<Integer>();
    private ArrayList<Float> amount = new ArrayList<Float>();
    private ArrayList<Integer> count = new ArrayList<>();
    private Listener incrementListener;
    private Listener decrementListener;

    interface Listener{
        void onClick();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final CardView cardView;
        private Float price;
        private int count;

        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }

        public void setPrice(Float price){
            this.price = price;
            TextView priceObj = (TextView) cardView.findViewById(R.id.amount);
            priceObj.setText(String.valueOf(price));
        }

        public Float getPrice(){
            return price;
        }

        public void setCount(int count){
            this.count = count;
            TextView countObj = (TextView) cardView.findViewById(R.id.count);
            countObj.setText(String.valueOf(count));
        }

        public int getCount(){
            return count;
        }
    }

    public BasketAdapter(ArrayList<String> captions, ArrayList<Integer> imageID, ArrayList<Float> amount, ArrayList<Integer> count){
        this.captions = captions;
        this.imgId = imageID;
        this.amount = amount;
        this.count = count;
    }

    @Override
    public int getItemCount() {
        return captions.size();
    }

    public void setIncrementListener(Listener listener){
        this.incrementListener = listener;
    }

    public void setDecrementListener(Listener listener){
        this.decrementListener = listener;
    }

    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_basket, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.img_obj);
        TextView nameObj = (TextView) cardView.findViewById(R.id.name_obj);
        TextView countObj = (TextView) cardView.findViewById(R.id.count);
        TextView increment = (TextView) cardView.findViewById(R.id.incrementCount);
        TextView decrement = (TextView) cardView.findViewById(R.id.decrementCount);
        TextView amountObj = (TextView) cardView.findViewById(R.id.amount);

        //Подгрузка картинки
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imgId.get(position));
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions.get(position));

        //Вставляем Имя объекта
        nameObj.setText(captions.get(position));

        //Вставляем кол-во
        countObj.setText(String.valueOf(count.get(position)));

        //Вставляем цену
        holder.price = amount.get(position);
        amountObj.setText(String.valueOf(holder.price));


        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incrementListener != null){
                    incrementListener.onClick();
                }
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (decrementListener != null){
                    decrementListener.onClick();
                }
            }
        });
    }
}
