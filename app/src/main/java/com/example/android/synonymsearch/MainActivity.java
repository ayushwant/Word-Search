package com.example.android.synonymsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.synonymsearch.synonym.fetchSynonym;
import com.example.android.synonymsearch.synonym.synonymWord;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private EditText SearchBoxET;

    private TextView UrlDisplayTV;

    private TextView SearchResultsTV;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchBoxET = (EditText) findViewById(R.id.et_search_box);
        UrlDisplayTV = (TextView) findViewById(R.id.tv_url_display);
        SearchResultsTV = (TextView) findViewById(R.id.tv_api_search_results);
    }

    private void makeWordSearchQuery()
    {
        String wordQuery = SearchBoxET.getText().toString();
        URL wordSearchUrl = fetchSynonym.buildUrl(wordQuery);
        UrlDisplayTV.setText(wordSearchUrl.toString());

        new wordQueryTask().execute(wordSearchUrl);
    }

    public class wordQueryTask extends AsyncTask<URL, Void, ArrayList<String> >
    {
        @Override
        protected ArrayList<String> doInBackground(URL... urls)
        {
            URL searchUrl = urls[0];
            synonymWord[] synonymResults = null;
            //String[] resultsToPrint=null;
            ArrayList<String> resultsToPrint = new ArrayList<String>();

            try
            {
                synonymResults = fetchSynonym.getResponseFromUrl(searchUrl);

                resultsToPrint.add("Synonyms  ");
                int i = 1;
                for (synonymWord sr : synonymResults)
                {
                    resultsToPrint.add(sr.getWord() + "  ");
                    i++;
                }
            }
            catch (IOException e) { e.printStackTrace(); }

            /*if(synonymResults!=null)
            {

                String resultsToPrint[synonymResults.length];
                resultsToPrint[0] = "Synonyms  ";
                int i = 1;
                for (synonymWord sr : synonymResults) {
                    resultsToPrint[i] = sr.getWord() + "  ";
                    i++;
                }

                return resultsToPrint;
            }*/
            return resultsToPrint;
        }

        @Override
        protected void onPostExecute(ArrayList<String> results)
        {
            for(String s : results)
            {
                if(s!=null && !s.equals(""))
                {
                    SearchResultsTV.setText(s);
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeWordSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}