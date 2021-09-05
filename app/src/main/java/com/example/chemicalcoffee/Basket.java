package com.example.chemicalcoffee;

import java.util.ArrayList;

public class Basket {
    private ArrayList<ObjProduct> basketList = new ArrayList<>();

    public void addInBasket(ObjProduct product){
        basketList.add(product);
    }

    public String pullNameOfBasket(int index){
        return basketList.get(index).getNameObject();
    }

    public int pullCountOfBasket(int index){
        return basketList.get(index).getCount();
    }

    public float pullCoastOfBasket(int index){
        return basketList.get(index).getCoast();
    }

    public int getCountInBasket(){
        int count = 0;

        for (ObjProduct product : basketList){
            count += product.getCount();
        }

        return count;
    }
}
