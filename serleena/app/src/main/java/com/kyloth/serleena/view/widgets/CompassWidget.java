///////////////////////////////////////////////////////////////////////////////
// 
// This file is part of Serleena.
// 
// The MIT License (MIT)
//
// Copyright (C) 2015 Antonio Cavestro, Gabriele Pozzan, Matteo Lisotto, 
//   Nicola Mometto, Filippo Sestini, Tobia Tesan, Sebastiano Valle.    
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to 
// deal in the Software without restriction, including without limitation the
// rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
// sell copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
// IN THE SOFTWARE.
//
///////////////////////////////////////////////////////////////////////////////


package com.kyloth.serleena.view.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.kyloth.serleena.R;

public class CompassWidget extends ImageView {

    private float direction = 0;

    /**
     * Crea un nuovo oggetto CompassWidget.
     */
    public CompassWidget(Context context) {
        super(context);
    }

    /**
     * Crea un nuovo oggetto CompassWidget.
     */
    public CompassWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Crea un nuovo oggetto CompassWidget.
     */
    public CompassWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Ridefinisce View.onDraw().
     *
     * Disegna la vista applicando una rotazione all'immagine di una bussola in
     * base al valore impostato con setOrientation().
     *
     * @param canvas Oggetto Canvas sul quale effettuare le operazioni grafiche.
     */
    @Override
    public void onDraw(Canvas canvas) {
        int height = this.getHeight();
        int width = this.getWidth();

        canvas.rotate(direction, width / 2, height / 2);
        super.onDraw(canvas);
    }

    /**
     * Imposta l'orientamento della bussola.
     *
     * @param direction Orientamento della bussola in gradi.
     */
    public void setOrientation(float direction) {
        this.direction = direction;
        this.setImageResource(R.drawable.compass);
        this.invalidate();
    }

    /**
     * Restituisce il valore di orientamento impostato nel wedget.
     * @return Gradi di rotazione in riferimento al nord reale.
     */
    public float getOrientation() {
        return this.direction;
    }

    /**
     * Reimposta la bussola.
     */
    public void reset() {
        this.setImageResource(android.R.color.transparent);
        invalidate();
    }

}
