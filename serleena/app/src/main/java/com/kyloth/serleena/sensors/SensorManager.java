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


/**
 * Name: SensorManager.java
 * Package: com.kyloth.serleena.sensors
 * Author: Filippo Sestini
 * Date: 2015-05-21
 *
 * History:
 * Version  Programmer        Date        Changes
 * 1.0.0    Filippo Sestini   2015-05-21  Creazione file e scrittura
 *                                        codice e documentazione Javadoc
 */

package com.kyloth.serleena.sensors;

import android.content.Context;

/**
 * Concretizza ISensorManager
 *
 * @author Filippo Sestini <sestini.filippo@gmail.com>
 * @version 1.0.0
 */
public class SensorManager implements ISensorManager {

    private Context context;

    /**
     * Crea un gestore della sensoristica a partire dal contesto specificato.
     *
     * @param context Contesto dell'applicazione.
     * @see Context
     */
    public SensorManager(Context context) {
        this.context = context;
    }

    /**
     * Implementa ISensorManager.getLocationSource().
     *
     * @return Oggetto ILocationManager.
     */
    @Override
    public ILocationManager getLocationSource() {
        return SerleenaLocationManager.getInstance(context);
    }

    /**
     * Implementa ISensorManager.getHeadingSource().
     *
     * @return Oggetto IHeadingManager.
     */
    @Override
    public IHeadingManager getHeadingSource()
            throws SensorNotAvailableException {
        return HeadingManager.getInstance(context);
    }

    /**
     * Implementa ISensorManager.getHeartRateManager().
     *
     * @return Oggetto IHeartRateManager.
     */
    @Override
    public IHeartRateManager getHeartRateSource() {
        return HeartRateManager.getInstance(context);
    }

    /**
     * Implementa ISensorManager.getLocationReachedSource().
     *
     * @return Oggetto ILocationReachedManager.
     */
    @Override
    public ILocationReachedManager getLocationReachedSource() {
        return LocationReachedManager.getInstance(context);
    }

    /**
     * Implementa ISensorManager.getWakeupSource().
     *
     * @return Oggetto IWakeupManager.
     */
    @Override
    public IWakeupManager getWakeupSource() {
        return WakeupManager.getInstance(context);
    }

    /**
     * Implementa ISensorManager.getTelemetryManager().
     *
     * @return Oggetto ITelemetryManager.
     */
    @Override
    public ITelemetryManager getTelemetryManager() {
        return TelemetryManager.getInstance(context);
    }

}