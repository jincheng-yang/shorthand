package com.example.datacollector;

import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Calendar;

import processing.core.*;
import processing.data.*;

public class Sketch extends PApplet {

    JSONObject json;
    JSONArray points;
    JSONObject figure;
    JSONArray figures;
    int pointCount = 0;
    int figureCount = 0;
    static String[] strokes = {"t", "d", "t-d blend", "n", "m", "n-m blend", "u", "k", "g", "o", "r", "l", "left s", "p", "b", "right s", "f", "v", "sh", "ch", "j", "left th", "n-t blend", "m-d blend", "right th", "t-n blend", "d-m blend", "x", "reverse a", "reverse e", "dot", "ng", "nk", "kr", "gl", "kl", "gr", "rk", "lk", "pr", "pl", "br", "bl", "fr", "fl"};
    public static int canvasWidth = 984;
    public static int canvasHeight = 1185;

    public void settings() {
        size(canvasWidth, canvasHeight);
    }

    public void setup() {
        fill(240, 5);
        background(240);

        json = new JSONObject();
        figure = new JSONObject();
        figures = new JSONArray();
        points = new JSONArray();
    }

    public void draw() {
        stroke(128);
        strokeWeight(1);
        rect(0, 0, width, height);
        line(0, height / 2, width, height / 2);
        line(0, height / 4, width, height / 4);
        line(0, height / 4 * 3, width, height / 4 * 3);
        if (mousePressed) {
            stroke(125, 58, 192);
            strokeWeight(4);
            line(pmouseX, pmouseY, mouseX, mouseY);
            JSONObject point = new JSONObject();
                point.setInt("t", millis());
                point.setInt("x", mouseX);
                point.setInt("y", mouseY);
            points.setJSONObject(pointCount++, point);
        }
    }

    public void mouseReleased() {
        figure.setString("character", strokes[((SeekBar) getActivity().findViewById(R.id.seekBar)).getProgress()]);
        figure.setJSONArray("point", points);
        figures.setJSONObject(figureCount++, figure);

        pointCount = 0;
        points = new JSONArray();
        figure = new JSONObject();
    }

    public void save() {
        json.setString("author", ((EditText) getActivity().findViewById(R.id.editText)).getText().toString());
        json.setString("time", timestamp());
        json.setJSONArray("data", figures);

        String filename = "data.json";
        JSONArray database = new JSONArray();

        // Load Data Base
        try {
            database = loadJSONArray(filename);
        } catch (Exception e) {

        }

        // Save Data Base
        database.append(json);
        saveJSONArray(database, filename);

        figureCount = 0;
        figures = new JSONArray();
        json = new JSONObject();
    }

    String timestamp() {
        return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", Calendar.getInstance());
    }

}