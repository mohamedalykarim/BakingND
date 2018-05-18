package com.backingnd.mohamedali.bakingnd.Utils;

import com.backingnd.mohamedali.bakingnd.R;

public class BackgroundUtils {


    /**
     * this method is for getting the background from the drawables folder
     * there is 6 background files
     * @param position: the position of the recipe item
     * @return the background resource
     */
    public static int getColoredBackground(int position){
        int resource = 0;

        int helperNumber = position/6;
        position = position - helperNumber * 6;

        if (position == 0) resource = R.drawable.bg_1;
        if (position == 1) resource = R.drawable.bg_2;
        if (position == 2) resource = R.drawable.bg_3;
        if (position == 3) resource = R.drawable.bg_4;
        if (position == 4) resource = R.drawable.bg_5;
        if (position == 5) resource = R.drawable.bg_6;

       return resource;
    }
}
