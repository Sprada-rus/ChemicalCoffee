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
    private ArrayList<ObjProduct> basketList = new ArrayList<>();

    private Listener incrementListener;
    private Listener decrementListener;

    interface Listener{
        void onClick();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    public BasketAdapter(ArrayList<ObjProduct> list){
        this.basketList = list;
    }

    @Override
    public int getItemCount() {
        return basketList.size();
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
        TextView countObj = (TextView) cardView.findViewById(R.id.count);
        TextView increment = (TextView) cardView.findViewById(R.id.incrementCount);
        TextView decrement = (TextView) cardView.findViewById(R.id.decrementCount);
        TextView amountObj = (TextView) cardView.findViewById(R.id.amount);

        //Подгрузка картинки
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), basketList.get(position).getImgId());
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(basketList.get(position).getNameObject());

        //Вставляем кол-во
        countObj.setText(String.valueOf(basketList.get(position).getCount()));

        //Вставляем цену
        amountObj.setText(String.valueOf(basketList.get(position).getCoast()));


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
