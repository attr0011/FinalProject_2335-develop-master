package com.bearm.owlbotdictionary;

import com.bearm.owlbotdictionary.DAL.WordDAO;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.owlbotdictionary.Adapters.MyAdapter;
import com.bearm.owlbotdictionary.DAL.WordDAO;
import com.bearm.owlbotdictionary.Interfaces.IVolleyCallback;
import com.bearm.owlbotdictionary.Model.Word;
import com.bearm.owlbotdictionary.Model.WordEntry;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This class references information from the course's labs, slides, and the android wiki
 * This is the main activity class for our project
 *
 */

public class MainActivity extends AppCompatActivity {

    String url, input, finalUrl;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    ArrayList<WordEntry> arrayWord;
    TextInputEditText editSearch;
  //  MyListAdapter myAdapter;
//    TextView tvWord;
//    TextView tvPronunciation;
    TextView tvExampleSubtitle;
    private SharedPreferences sharedPref;
    private String currentInput;
    OpenHelper myOpener;
    SQLiteDatabase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * For getting widgets from activity_main layout
         */
        editSearch = findViewById(R.id.edit_search);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE); //added by khadija
        currentInput = sharedPref.getString("edit_hint_text", "");
        editSearch.setText(currentInput);
//        tvWord = findViewById(R.id.tv_word);
//        tvPronunciation = findViewById(R.id.tv_pronunciation);

        Toolbar tbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbar);

        arrayWord = new ArrayList<>();
        mAdapter = new MyAdapter();

        recyclerView = findViewById(R.id.recycler_list);

        /**
         * A recycler view to get the search results from owlbotDictionary
         */

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        /**
         * URL for Owlbot dictionary website
         */

        url = getString(R.string.url_dictionary);

        myOpener = new OpenHelper( this ); //[3]
        /**
         * The following method opens the database.
         */
        dataBase = myOpener.getWritableDatabase();

        Cursor print = dataBase.rawQuery( "Select * from " + OpenHelper.TABLE_NAME //[3]
                + " ORDER BY " + OpenHelper.COLM_ID + " ASC" + ";", null );//no arguments to the query


        /**
         * The following steps convert the column names to indices:
         */
        int id_Index = print.getColumnIndex( OpenHelper.COLM_ID ); //[3] the following method initially returned -1 to represent no column exists.
        int  word_Index = print.getColumnIndex( OpenHelper.COL_WORD);
        int  pron_Index = print.getColumnIndex( OpenHelper.COL_PRON);
        int  def_Index = print.getColumnIndex( OpenHelper.COL_DEF);
        int  example_Index = print.getColumnIndex( OpenHelper.COL_EXAMPLE);
        int  image_Index = print.getColumnIndex( OpenHelper.COL_IMAGE);
        int  type_Index = print.getColumnIndex( OpenHelper.COL_TYPE);

        boolean flag = true;

        print.moveToNext();

        /**
         * The following method moves the cursor to next row.
         * The while loop will return false if there is no more data
         */
        while( print.moveToNext() )
        {
            String word = print.getString(word_Index);
            String pron = print.getString(pron_Index);
            String def = print.getString(def_Index);
            String example = print.getString(example_Index);
            String image = print.getString(image_Index);
            String type = print.getString(type_Index);

            //myAdapter.add(new Message(message, true, id));

            try {
                arrayWord.add(new WordEntry(word, pron, def, type, image, example));
                recyclerView.setAdapter(mAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        print.close();

        MaterialButton searchBtn = findViewById(R.id.search_button);
        /**
         * ClickListener for the search button
         */
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Saving user's input in search field
                input = String.valueOf(editSearch.getText());
                currentInput = editSearch.getText().toString();
                onPause();

                //Checking if input is valid (not empty and only alphabet characters)
                if (!input.equals("") && (input.matches("[a-zA-Z]+"))) {
                    //Sending input to request
                    searchWord(input);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.input_invalid_message_toast), Toast.LENGTH_LONG).show();
                }

//


            }
        });


    }

//    myList.setOnItemLongClickListener( (p, b, pos, id) -> {
//        Message selectedContact = list.get(pos);
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setTitle("Do you want to delete this?")
//                .setMessage("The selected row is:"+ pos + "\n“The database id is:"+id)
//                .setPositiveButton("Yes", (click, arg) -> {
//                    list.remove(pos);
//                    myAdapter.notifyDataSetChanged();
//                    db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(selectedContact.getId())});
//                })
//                .setNegativeButton("No", (click, arg) -> { })
//                .create().show();
//        return true;
//    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * Inflates the menu items for use in the action bar
          */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarowlbot, menu);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        /**
         * Looks at the menu XML file. There is a case for every ID in the file
         */
        switch(item.getItemId())
        {
            /**
             * Case decides what to do when the menu item is selected
             */
            case R.id.homeOb:
                Intent goToProfile = new Intent(MainActivity.this, MainProjActivity.class);
                startActivity(goToProfile);
                finish();
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    /**
     * Method for calling WordDAO class and managing the response of the request it executes
     *
     * @param word user input that will be sent in the WordDAO request
     */
    private void searchWord(String word) {
        //Adding user's input to the url that will be used in the request
        finalUrl = url + word;
        final WordDAO jsonParserVolley = new WordDAO(this);
        jsonParserVolley.requestData(finalUrl, new IVolleyCallback() {

                    @Override
                    public void getResponse(JSONObject response) {

                        Log.d("VOLLEY", "RES" + response);
//                        cleanData();
                        //Sending response to be managed
                        parseData(response);
                    }
                }
        );
    }


    /**
     * Method to clear the data of all the elements of the word entry before displaying new info.
     */
//    private void cleanData() {
//        //Word
//        tvWord.setText("");
//
//        //Pronunciation
//        tvPronunciation.setText("");
//
//        //Array: Definitions, types, image and examples
//        arrayWord.clear();
//
//
//    }

    /**
     * Method for extracting the info from the request response
     *
     * @param response JSONObject obteined in the request made by WordDAO class
     */
    private void parseData(JSONObject response) {
        try {

            JSONObject entry;
            String definition;
            String type;
            String example;
            String urlImage;

            //Extracting single values of the word, like pronunciation, from the JSON object
            String word = response.getString("word");
            String pronunciation = response.getString("pronunciation");

            Log.e("WORDS", word + " " + pronunciation);

            //Extracting multiple values of the word, like type, definition and examples, from the JSON object
            JSONArray definitions = response.getJSONArray("definitions");
            //TODO - Extract multiple values
            Log.e("DEFINITIONS.LENGHT", String.valueOf(definitions.length()));

            for (int i = 0; i < definitions.length(); i++) {
                entry = definitions.getJSONObject(i);
                definition = String.valueOf(entry.get("definition"));
                type = entry.getString("type");
                example = entry.getString("example");
                urlImage = String.valueOf(entry.get("image_url"));

                Log.e("VALUES", definition + " " + type + " " + example + " " + urlImage);
                //add code to ensure you are adding all this information to database

                //store values in a set/insert into database:
                ContentValues newAddedRow = new ContentValues();// [3]

                //adding new value to the set.
                newAddedRow.put(OpenHelper.COL_WORD, word);
                //adding new value to the set.
                newAddedRow.put(OpenHelper.COL_PRON, pronunciation);
                //adding new value to the set.
                newAddedRow.put(OpenHelper.COL_DEF, definition);
                //adding new value to the set.
                newAddedRow.put(OpenHelper.COL_EXAMPLE, example);
                //adding new value to the set.
                newAddedRow.put(OpenHelper.COL_IMAGE, urlImage);
                //adding new value to the set.
                newAddedRow.put(OpenHelper.COL_TYPE, type);
                //adding new value to the set.
                newAddedRow.put(OpenHelper.COL_WORD, word);

                //.insert represents a new row is inserted into the database.
                long id = dataBase.insert(OpenHelper.TABLE_NAME, null, newAddedRow); //returns the id

                arrayWord.add(new WordEntry(word, pronunciation, definition, type, urlImage, example));
            }


            //Log.e("WORDS", definitions + "/ " + types + "/ " + examples);
//            tvWord.setText(word);
//            if(!pronunciation.equals("null")){
//                tvPronunciation.setText("/" + pronunciation + "/");
//            }

            recyclerView.setAdapter(mAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * onPause method
     */
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPref.edit(); //[1]
        editor.putString("edit_hint_text", currentInput);
        editor.apply();

    }

    /**
     * Custom adapter for the RecyclerView that was used above
     */
    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        //        public ArrayList<WordEntry> mDataset;
//        public Context context;
        private String example;

        // Create new views (invoked by the layout manager)
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);

            return new MyViewHolder(v);
        }


        /**
         * Replaces the contents of a view (invoked by the layout manager)
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.tvWord.setText(arrayWord.get(position).word);
            if(!arrayWord.get(position).pronunciation.equals("null")){
                holder.tvPronunciation.setText(arrayWord.get(position).pronunciation);
            }
            holder.tvDefinition.setText(arrayWord.get(position).definition);
            holder.tvType.setText(arrayWord.get(position).type);
            example = arrayWord.get(position).example;
            if(!example.equals("null")){
                holder.tvExample.setVisibility(View.VISIBLE);
                holder.tvExample.setText(Html.fromHtml(example, Html.FROM_HTML_MODE_LEGACY));
            } else {
                holder.tvExample.setVisibility(View.GONE);
            }

            if(arrayWord.get(position).image != null) {
                Picasso.with(MainActivity.this)
                        .load(arrayWord.get(position).image)
                        .into(holder.ivImage);
            }

        }

        @Override
        public int getItemCount() {
            return arrayWord.size();
        }
    }

    /**
     * Provides a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     * each data item is just a string in this case
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvWord;
        TextView tvPronunciation;
        TextView tvDefinition;
        TextView tvType;
        TextView tvExample;
        ImageView ivImage;


        public MyViewHolder(View v) {
            super(v);
            tvWord = v.findViewById(R.id.tv_word);
            tvPronunciation = v.findViewById(R.id.tv_pronunciation);
            tvDefinition = v.findViewById(R.id.tv_definition);
            tvType = v.findViewById(R.id.tv_type);

            tvExample = v.findViewById(R.id.tv_example);
            ivImage = v.findViewById(R.id.iv_image);

            v.setOnClickListener(click ->{

                int position = getAdapterPosition();//which row was clicked.
                WordEntry clicked = arrayWord.get(position);

                Bundle dataToPass = new Bundle();
                dataToPass.putString("WORD_SELECTED", clicked.word);
                dataToPass.putString("WORD_TYPE", clicked.type);
                dataToPass.putInt("ITEM_POSITION", position);
                dataToPass.putString("ITEM_DEF", clicked.definition);

//                DetailsFragment myFragment = new DetailsFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frameL, myFragment).commit();


            });
        }
    }

}
