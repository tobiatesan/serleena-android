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


package com.kyloth.serleena.common;

import android.hardware.GeomagneticField;

import org.junit.Test;

import java.lang.Float;
import java.lang.System;

import static org.junit.Assert.*;

/**
 * Created by fsestini on 6/7/15.
 */
public class AzimuthMagneticNorthTest {
    /*
        lat 34° = 0.593411946
        lon 118° = 2.05948852
        decl ~12° = 0.20943951
     */
    @Test
    public void testCorrectResult() {
        AzimuthMagneticNorth az = new AzimuthMagneticNorth(0);
        GeoPoint loc = new GeoPoint(0.593411946, 2.05948852);
        float result = az.toTrueNorth(loc);
        System.out.println(result);
        assertEquals(0.20943951, result, 0.05);
    }

    @Test
    public void foo() {
        GeomagneticField geoField =
                new GeomagneticField(20f, 20f, 0, System.currentTimeMillis());

        float declination = geoField.getDeclination();
        assertTrue(declination != 0);
    }
}
