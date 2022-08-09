package com.flaviumircia.aquatrouble;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class NightModeTiles {
    private float lr,lg,lb,dr,dg,db,drf,dgf,dbf;
    private ColorMatrixColorFilter filter;
    private final float[] defaultMatrix=
            {
        -1.0f, 0.0f, 0.0f, 0.0f, 255f,
        0.0f, -1.0f, 0.0f, 0.0f, 255f,
        0.0f, 0.0f, -1.0f, 0.0f, 255f,
        0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private final int destinationColor;

    private float[] setGrayScaleMatrix()
    {
         this.lr = (255.0f - Color.red(destinationColor))/255.0f;
         this.lg = (255.0f - Color.green(destinationColor))/255.0f;
         this.lb = (255.0f - Color.blue(destinationColor))/255.0f;

        return new float[]{
                lr, lg, lb, 0, 0, //
                lr, lg, lb, 0, 0, //
                lr, lg, lb, 0, 0, //
                0, 0, 0, 0, 255,
        };

    }
    private float[] setTintMatrix()
    {
        this.dr = Color.red(destinationColor);
        this.dg = Color.green(destinationColor);
        this.db = Color.blue(destinationColor);
        this.drf = dr / 255f;
        this.dgf = dg / 255f;
        this.dbf = db / 255f;

        return new float[]{
        drf, 0, 0, 0, 0, //
        0, dgf, 0, 0, 0, //
        0, 0, dbf, 0, 0, //
        0, 0, 0, 1, 0,};
    }
    private float [] setTheScale(){
        float lDestination = drf * lr + dgf * lg + dbf * lb;
        float scale = 1f - lDestination;
        float translate = 1 - scale * 0.2f;
        scale*=2.8f;
        return new float[]{
                scale, 0, 0, 0, dr * translate, //
                0, scale, 0, 0, dg * translate, //
                0, 0, scale, 0, db * translate, //
                0, 0, 0, 1, 0, };
    }
    public NightModeTiles(String background_color){
        //background color
        this.destinationColor = Color.parseColor(background_color);

        ColorMatrix inverseMatrix=new ColorMatrix(defaultMatrix);
        ColorMatrix grayScaleMatrix = new ColorMatrix(setGrayScaleMatrix());
        ColorMatrix tintMatrix= new ColorMatrix(setTintMatrix());
        ColorMatrix scaleMatrix= new ColorMatrix(setTheScale());

        grayScaleMatrix.preConcat(inverseMatrix);
        tintMatrix.preConcat(grayScaleMatrix);
        scaleMatrix.preConcat(tintMatrix);

        this.filter=new ColorMatrixColorFilter(scaleMatrix);
    }

    public ColorMatrixColorFilter getFilter() {
        return filter;
    }
}
