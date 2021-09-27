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

    private OnItemClickListener listener;
    private OnItemClickListener incrementListener;
    private OnItemClickListener decrementListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setDecrementListener(OnItemClickListener decrementListener) {
        this.decrementListener = decrementListener;
    }

    public void setIncrementListener(OnItemClickListener incrementListener){
        this.incrementListener = incrementListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final CardView cardView;
        public ImageView imageView;//=
        public TextView countObj; //=
        public TextView increment;// =
        public TextView decrement;// =
        public TextView amountObj;// =

        public ViewHolder(CardView v, OnItemClickListener decrListener, OnItemClickListener incrListener){
            super(v);
            cardView = v;
            imageView = (ImageView) cardView.findViewById(R.id.img_obj);
            countObj = (TextView) cardView.findViewById(R.id.count);
            increment = (TextView) cardView.findViewById(R.id.incrementCount);
            decrement = (TextView) cardView.findViewById(R.id.decrementCount);
            amountObj = (TextView) cardView.findViewById(R.id.amount);

            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (incrListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            incrListener.onItemClick(position);
                        }
                    }
                }
            });

            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (decrListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            decrListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public BasketAdapter(ArrayList<ObjProduct> list){
        this.basketList = list;
    }

    @Override
    public int getItemCount() {
        return basketList.size();
    }

//    public void setIncrementListener(Listener listener){
//        this.incrementListener = listener;
//    }
//
//    public void setDecrementListener(Listener listener){
//        this.decrementListener = listener;
//    }

    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_basket, parent, false);
        return new ViewHolder(cardView, decrementListener, incrementListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ObjProduct product = basketList.get(position);
        CardView cardView = holder.cardView;
        //Подгрузка картинки
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), basketList.get(position).getImgId());
        holder.imageView.setImageDrawable(drawable);
        holder.imageView.setContentDescription(basketList.get(position).getNameObject());

        //Вставляем кол-во
        holder.countObj.setText(String.valueOf(basketList.get(position).getCount()));

        //Вставляем цену
        holder.amountObj.setText(String.valueOf(basketList.get(position).getCoast()));


//        holder.increment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(incrementListener != null){
//                    incrementListener.onClick();
//                }
//            }
//        });
//
//        holder.decrement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (decrementListener != null){
//                    decrementListener.onClick();
//                }
//            }
//        });
    }
}
