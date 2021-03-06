package mobiledev.unb.ca.skeleton_lab4_part2;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataModel {

    private ArrayList<Course> coursesArray = new ArrayList<>();
    private Context context;
    AssetManager assetManager;

    // Initializer to read our data source (JSON file) into an array of course objects
    public DataModel(Context context) {

        this.context = context;
        assetManager = this.context.getAssets();

        getJSON();
    }

    private void getJSON() {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();

        try {
            br = new BufferedReader(new InputStreamReader(assetManager.open("CS.json")));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        processJSON(sb);
    }

    private void processJSON(StringBuffer sb) {
        String jsonString = sb.toString();

        // Finally, parse resultant JSON
        try {

            // Create a JSON Object from file contents String
            JSONObject jsonObject = new JSONObject(jsonString);

            // Create a JSON Array from the JSON Object
            // This array is the "courses" array mentioned in the lab write-up
            JSONArray jsonArray = jsonObject.getJSONArray("courses");

            for (int i=0; i < jsonArray.length(); i++) {

                // Create a JSON Object from individual JSON Array element
                JSONObject elementObject = jsonArray.getJSONObject(i);

                // Create a Course Object to contain JSON Object data
                Course course = new Course();

                // Get data from individual JSON Object and store to Course Object
                course.id = elementObject.getString("courseID");
                course.name = elementObject.getString("name");
                course.desc = elementObject.getString("description");

                // Add new Course to courses ArrayList
                coursesArray.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    // Getter method for courses ArrayList
    public ArrayList<Course> getCourses() {
        return coursesArray;
    }
}
