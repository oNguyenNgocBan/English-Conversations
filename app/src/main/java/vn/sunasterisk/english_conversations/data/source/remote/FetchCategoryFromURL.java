package vn.sunasterisk.english_conversations.data.source.remote;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import vn.sunasterisk.english_conversations.constant.Constant;
import vn.sunasterisk.english_conversations.data.model.Category;
import vn.sunasterisk.english_conversations.data.source.CategoryDataSource;
import vn.sunasterisk.english_conversations.utils.ParseCategoryFromJSON;
import vn.sunasterisk.english_conversations.utils.StorageManager;

public class FetchCategoryFromURL extends AsyncTask<String, Void, List<Category>> {

    private CategoryDataSource.OnFetchCategoryListener mListener;
    private Exception mException;

    public FetchCategoryFromURL(CategoryDataSource.OnFetchCategoryListener listener) {
        mListener = listener;
    }

    @Override
    protected List<Category> doInBackground(String... strings) {
        String url = strings[0];

        try {
            String data = getStringDataFromUrl(url);
            // Save content to file local
            StorageManager storageManager = StorageManager.getInstance();
            storageManager.writeStringToInternalStorage(data, Constant.DATA_JSON_NAME);

            ParseCategoryFromJSON parseCategoryFromJSON = new ParseCategoryFromJSON(data);
            return parseCategoryFromJSON.getListCategory();
        } catch (IOException | JSONException e) {
            mException = e;
        }
        return null;
    }

    private String getStringDataFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(Constant.REQUEST_METHOD_GET);
        connection.setConnectTimeout(Constant.CONNECT_TIME_OUT);
        connection.setReadTimeout(Constant.READ_TIME_OUT);
        connection.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(Constant.BREAK_LINE);
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        if (mListener == null) {
            return;
        }
        if (mException == null) {
            mListener.onFetchCategorySuccess(categories);
        } else {
            mListener.onFetchCategoryFailure(mException.getMessage());
        }
    }
}
