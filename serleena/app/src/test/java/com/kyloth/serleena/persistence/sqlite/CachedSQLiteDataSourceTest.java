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


package com.kyloth.serleena.persistence.sqlite;

import com.kyloth.serleena.common.DirectAccessList;
import com.kyloth.serleena.common.EmergencyContact;
import com.kyloth.serleena.common.GeoPoint;
import com.kyloth.serleena.common.IQuadrant;
import com.kyloth.serleena.common.ListAdapter;
import com.kyloth.serleena.common.NoSuchWeatherForecastException;
import com.kyloth.serleena.common.TelemetryEvent;
import com.kyloth.serleena.common.UserPoint;
import com.kyloth.serleena.model.ISerleenaDataSource;
import com.kyloth.serleena.persistence.IExperienceStorage;
import com.kyloth.serleena.persistence.IWeatherStorage;
import com.kyloth.serleena.persistence.NoSuchQuadrantException;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test di unità per la classe CachedSQLiteDataSource.
 *
 * @author Filippo Sestini <sestini.filippo@gmail.com>
 * @version 1.0.0
 */
public class CachedSQLiteDataSourceTest {

    ISerleenaSQLiteDataSource ds;
    SQLiteDAOTrack track;
    SQLiteDAOExperience experience;
    CachedSQLiteDataSource cachedDS;
    Iterable<TelemetryEvent> events;
    Iterable<IExperienceStorage> experiences1;
    Iterable<IExperienceStorage> experiences2;
    DirectAccessList<EmergencyContact> contacts1;
    DirectAccessList<EmergencyContact> contacts2;

    @Before
    public void initialize() {
        ds = mock(ISerleenaSQLiteDataSource.class);
        track = mock(SQLiteDAOTrack.class);
        experience = mock(SQLiteDAOExperience.class);
        cachedDS = new CachedSQLiteDataSource(ds);

        ArrayList<TelemetryEvent> list = new ArrayList<>();
        list.add(mock(TelemetryEvent.class));
        list.add(mock(TelemetryEvent.class));
        list.add(mock(TelemetryEvent.class));
        events = list;

        ArrayList<IExperienceStorage> eList = new ArrayList<>();
        eList.add(mock(IExperienceStorage.class));
        eList.add(mock(IExperienceStorage.class));
        eList.add(mock(IExperienceStorage.class));
        experiences1 = eList;
        eList = new ArrayList<>();
        eList.add(mock(IExperienceStorage.class));
        eList.add(mock(IExperienceStorage.class));
        eList.add(mock(IExperienceStorage.class));
        experiences2 = eList;

        ArrayList<EmergencyContact> cList = new ArrayList<>();
        cList.add(mock(EmergencyContact.class));
        cList.add(mock(EmergencyContact.class));
        cList.add(mock(EmergencyContact.class));
        contacts1 = new ListAdapter<>(cList);
        cList = new ArrayList<>();
        cList.add(mock(EmergencyContact.class));
        cList.add(mock(EmergencyContact.class));
        cList.add(mock(EmergencyContact.class));
        contacts2 = new ListAdapter<>(cList);
    }

    /**
     * Verifica che il metodo getTelemetries() venga correttamente inoltrato
     * al datasource incapsulato.
     */
    @Test
    public void testNormalRedirectionOfGetTelemetries() {
        cachedDS.getTelemetries(track);
        verify(ds).getTelemetries(track);
    }

    /**
     * Verifica che il metodo getTracks() venga correttamente inoltrato
     * al datasource incapsulato.
     */
    @Test
    public void testNormalRedirectionOfGetTracks() {
        cachedDS.getTracks(experience);
        verify(ds).getTracks(experience);
    }

    /**
     * Verifica che il metodo addUserPoint() venga correttamente inoltrato
     * al datasource incapsulato.
     */
    @Test
    public void testNormalRedirectionOfAddUserPoint() {
        UserPoint up = new UserPoint(4, 4);
        cachedDS.addUserPoint(experience, up);
        verify(ds).addUserPoint(experience, up);
    }

    /**
     * Verifica che il metodo createTelemetry() venga correttamente inoltrato
     * al datasource incapsulato.
     */
    @Test
    public void testNormalRedirectionOfCreateTelemetry() {
        cachedDS.createTelemetry(events, track);
        verify(ds).createTelemetry(events, track);
    }

    /**
     * Verifica che i dati restituiti da getExperiences() vengano
     * correttamente cachati e utilizzati in successive chiamate dello stesso
     * metodo.
     */
    @Test
    public void testThatExperiencesGetCached() {
        when(ds.getExperiences()).thenReturn(experiences1);
        Iterable<IExperienceStorage> exps1 = cachedDS.getExperiences();
        assertTrue(exps1 == experiences1);

        when(ds.getExperiences()).thenReturn(experiences2);
        Iterable<IExperienceStorage> exps2 = cachedDS.getExperiences();
        assertTrue(exps2 == experiences1);
    }

    /**
     * Verifica che l'oggetto IQuadrant restituito da getQuadrant() venga
     * correttamente cachato e restituito a chiamate successive dello stesso
     * metodo, quando il parametro indica una posizione geografica
     * all'interno dello stesso quadrante.
     */
    @Test
    public void testThatQuadrantGetsCachedForSameArea()
            throws NoSuchQuadrantException {
        IQuadrant q1 = mock(IQuadrant.class);
        IQuadrant q2 = mock(IQuadrant.class);
        GeoPoint gp = mock(GeoPoint.class);
        SQLiteDAOExperience exp = mock(SQLiteDAOExperience.class);

        when(ds.getQuadrant(gp, exp)).thenReturn(q1);
        cachedDS.getQuadrant(gp, exp);

        GeoPoint gp2 = mock(GeoPoint.class);
        when(q1.contains(gp2)).thenReturn(true);
        when(ds.getQuadrant(gp2, exp)).thenReturn(q2);

        assertTrue(cachedDS.getQuadrant(gp2, exp) == q1);
    }

    /**
     * Verifica che la chiamata di getQuadrant() relativamente a punti
     * geografici in quadranti diversi causano la restituzione di oggetti
     * IQuadrant appropriati, ignorando i valori cachati.
     */
    @Test
    public void testThatDifferentAreasGetDifferentQuadrants()
            throws NoSuchQuadrantException {
        IQuadrant q1 = mock(IQuadrant.class);
        IQuadrant q2 = mock(IQuadrant.class);
        GeoPoint gp1 = mock(GeoPoint.class);
        GeoPoint gp2 = mock(GeoPoint.class);
        SQLiteDAOExperience exp = mock(SQLiteDAOExperience.class);

        when(ds.getQuadrant(gp1, exp)).thenReturn(q1);
        assertTrue(cachedDS.getQuadrant(gp1, exp) == q1);
        verify(ds).getQuadrant(gp1, exp);

        when(ds.getQuadrant(gp2, exp)).thenReturn(q2);
        when(q1.contains(gp2)).thenReturn(false);
        assertTrue(cachedDS.getQuadrant(gp2, exp) == q2);
        verify(ds).getQuadrant(gp2, exp);
    }

    /**
     * Verifica che i dati relativi ai contatti di emergenza restituiti da
     * getContacts() vengano correttamente cachati e restituiti per chiamate
     * consecutive del metodo con stessi parametri di posizione geografica.
     */
    @Test
    public void testThatEmergencyGetsCachedForSameLocation() {
        GeoPoint location = mock(GeoPoint.class);

        when(ds.getContacts(location)).thenReturn(contacts1);
        cachedDS.getContacts(location);
        when(ds.getContacts(location)).thenReturn(contacts2);
        assertTrue(cachedDS.getContacts(location) == contacts1);
    }

    /**
     * Verifica che la chiamata di getContacts() relativamente a punti
     * geografici diversi causano la restituzione di oggetti appropriati,
     * ignorando i valori cachati.
     */
    @Test
    public void testThatDifferentLocationsGetDifferentContacts() {
        GeoPoint location1 = new GeoPoint(4, 4);
        GeoPoint location2 = new GeoPoint(5, 5);

        when(ds.getContacts(location1)).thenReturn(contacts1);
        assertTrue(cachedDS.getContacts(location1) == contacts1);

        when(ds.getContacts(location2)).thenReturn(contacts2);
        assertTrue(cachedDS.getContacts(location2) == contacts2);
    }

    /**
     * Verifica che i dati meteo restituiti da getContacts() vengano
     * correttamente cachati e restituiti per chiamate consecutive del metodo
     * con stessi parametri di posizione geografica e data.
     */
    @Test
    public void testThatWeatherGetsCachedForSameLocationAndDate()
            throws NoSuchWeatherForecastException {
        IWeatherStorage weather1 = mock(IWeatherStorage.class);
        IWeatherStorage weather2 = mock(IWeatherStorage.class);
        GeoPoint location = mock(GeoPoint.class);
        Date date = mock(Date.class);

        when(ds.getWeatherInfo(location, date)).thenReturn(weather1);
        cachedDS.getWeatherInfo(location, date);
        when(ds.getWeatherInfo(location, date)).thenReturn(weather2);
        assertTrue(cachedDS.getWeatherInfo(location, date) == weather1);
    }

    /**
     * Verifica che la chiamata di getWeatherInfo() relativamente a punti
     * geografici o date diversi causano la restituzione di oggetti appropriati,
     * ignorando i valori cachati.
     */
    @Test
    public void testThatDifferentLocationOrDateGetsDifferentWeather()
            throws NoSuchWeatherForecastException {
        IWeatherStorage weather1 = mock(IWeatherStorage.class);
        IWeatherStorage weather2 = mock(IWeatherStorage.class);
        GeoPoint location1 = new GeoPoint(4, 4);
        GeoPoint location2 = new GeoPoint(5, 5);
        Date date1 = new Date(System.currentTimeMillis());
        Date date2 = new Date(System.currentTimeMillis() - 10000);

        when(ds.getWeatherInfo(location1, date1)).thenReturn(weather1);
        cachedDS.getWeatherInfo(location1, date1);

        when(ds.getWeatherInfo(any(GeoPoint.class),
                any(Date.class))).thenReturn(weather2);
        assertTrue(cachedDS.getWeatherInfo(location1, date2) == weather2);

        when(ds.getWeatherInfo(any(GeoPoint.class),
                any(Date.class))).thenReturn(weather1);
        assertTrue(cachedDS.getWeatherInfo(location2, date2) == weather1);

        when(ds.getWeatherInfo(any(GeoPoint.class),
                any(Date.class))).thenReturn(weather2);
        assertTrue(cachedDS.getWeatherInfo(location1, date1) == weather2);
    }

    /**
     * Verifica che i punti utente relativi a un oggetto SQLiteDAOExperience
     * vengano correttamente cachati quando questi non cambiano, ma vengano
     * restituiti dati aggiornati quando viene aggiunto un nuovo punto utente
     * all'esperienza.
     */
    @Test
    public void testThatUserPointsGetCachedAndReturnedCorrectly() {
        Iterable<UserPoint> ups1 = new ArrayList<>();
        Iterable<UserPoint> ups2 = new ArrayList<>();
        Iterable<UserPoint> ups3 = new ArrayList<>();
        SQLiteDAOExperience exp = mock(SQLiteDAOExperience.class);

        when(ds.getUserPoints(exp)).thenReturn(ups1);
        assertTrue(cachedDS.getUserPoints(exp) == ups1);

        when(ds.getUserPoints(exp)).thenReturn(ups2);
        assertTrue(cachedDS.getUserPoints(exp) == ups1);

        cachedDS.addUserPoint(mock(SQLiteDAOExperience.class),
                mock(UserPoint.class));
        assertTrue(cachedDS.getUserPoints(exp) == ups1);

        cachedDS.addUserPoint(exp, mock(UserPoint.class));
        when(ds.getUserPoints(exp)).thenReturn(ups3);
        assertTrue(cachedDS.getUserPoints(exp) == ups3);

        when(ds.getUserPoints(exp)).thenReturn(ups2);
        assertTrue(cachedDS.getUserPoints(exp) == ups3);
    }

}