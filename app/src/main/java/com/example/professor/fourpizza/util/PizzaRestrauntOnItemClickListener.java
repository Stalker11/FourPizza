package com.example.professor.fourpizza.util;


import android.view.View;

import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.models.RestrauntPictures;

public interface PizzaRestrauntOnItemClickListener {
    void onItemClick(int position, View v, PizzaRestraunt restraunt, RestrauntPictures picture);
}
