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
 * Name: IBackgroundLocationManager.java
 * Package: com.kyloth.serleena.sensors
 * Author: Filippo Sestini
 *
 * History:
 * Version  Programmer        Changes
 * 1.0.0    Filippo Sestini   Creazione file e scrittura
 *                            codice e documentazione Javadoc
 */

package com.kyloth.serleena.sensors;

/**
 * Rappresenta l'interfaccia di un oggetto in grado di fornire dati sulla
 * posizione utente in background, anche quando il dispositivo è in sleep mode.
 *
 * Insieme a ILocationObserver, realizza il pattern Observer.
 *
 * @use Viene passato come parametro al costruttore di LocationReachedManager, attraverso il quale questo ottiene aggiornamenti in background sulla posizione.
 * @author Filippo Sestini <sestini.filippo@gmail.com>
 * @version 1.0.0
 */
public interface IBackgroundLocationManager {

    /**
     * Registra un observer all'oggetto.
     *
     * @param observer Observer da registrare.
     */
    void attachObserver(ILocationObserver observer);

    /**
     * Cancella la registrazione di un observer.
     *
     * @param observer Observer la cui registrazione deve essere cancellata.
     */
    void detachObserver(ILocationObserver observer);

    /**
     * Notifica gli observer registrati all'oggetto.
     */
    void notifyObservers();

}
