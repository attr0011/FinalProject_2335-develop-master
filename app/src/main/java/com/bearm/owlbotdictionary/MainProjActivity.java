package com.bearm.owlbotdictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.bearm.owlbotdictionary.Model.WordEntry;

/**
 * This class references information from the course's labs, slides, and the android wiki
 * This class handles the java code for the main page
 * It handles the toolbars, and also includes a button for going to the next page
 */

public class MainProjActivity extends AppCompatActivity {
    Button owlbot;

    /**
     * onCreate handles the toolbars and also the button to go to the next page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_proj);
        //This gets the toolbar from the layout:
        Toolbar tBar = (Toolbar)findViewById(R.id.toolbar);

        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(tBar);
        owlbot = findViewById(R.id.button);
        owlbot.setOnClickListener(btn ->
        {
            Intent goToProfile = new Intent(MainProjActivity.this, MainActivity.class);
            startActivity(goToProfile);
        });


    }

    /**
     * This method handles inflating the menu items for the action bar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maintoolbar, menu);


	    /* slide 15 material:
	    MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView sView = (SearchView)searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }  });

	    */

        return true;
    }

    /**
     * This method handles the menu and what to do when various options are selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.activity3:
                Intent goToProfile = new Intent(MainProjActivity.this, MainActivity.class);
                startActivity(goToProfile);
                break;
//            case R.id.activity2:
//                Intent goToProfile = new Intent(MainActivity.this, FoodlActivity.class);
//                startActivity(goToProfile);
//                break;
//            case R.id.activity3:
//                Intent goToProfile = new Intent(MainActivity.this, OwlbotActivity.class);
//                startActivity(goToProfile);
//                break;
            case R.id.action_search:
                message = "You clicked on search";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;

    }

}