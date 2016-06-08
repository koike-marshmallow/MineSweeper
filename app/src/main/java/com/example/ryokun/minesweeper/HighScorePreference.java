package com.example.ryokun.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taka on 2016/06/08.
 */
public class HighScorePreference {
    SharedPreferences preferences;
    HashMap<String, Integer> scores;

    public HighScorePreference(Activity a0){
        preferences = a0.getSharedPreferences("highScores", Context.MODE_PRIVATE);
        scores = new HashMap<String, Integer>();
    }

    public boolean loadPreferences(){
        Map<String, ?> data = preferences.getAll();
        boolean result = true;

        scores.clear();
        for(String key : data.keySet()){
            Integer tmp;
            try{
                tmp = (Integer)data.get(key);
                scores.put(key, tmp);
            }catch(Exception e0){
                Log.e("LoadScore", "ClassCastException: key=" + key);
                result = false;
            }
        }

        return result;
    }

    public boolean savePreferences(){
        preferences.edit().clear();

        for(String key : scores.keySet()){
            preferences.edit().putInt(key, scores.get(key));
        }

        preferences.edit().commit();

        return true;
    }

    public void clear(){
        scores.clear();
    }

    public void setScore(String key, int val){
        scores.put(key, val);
    }

    public int getScore(String key){
        return scores.get(key);
    }

    public String[] getKeyList(){
        String[] keys = new String[scores.size()];
        int i = 0;
        for(String key : scores.keySet()){
            if( i < keys.length ) keys[i] = key;
            i++;
        }

        return keys;
    }
}
