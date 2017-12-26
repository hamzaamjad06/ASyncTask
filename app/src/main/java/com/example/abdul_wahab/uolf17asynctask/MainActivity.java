package com.example.abdul_wahab.uolf17asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MTAG";

    MyTask mtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtask = new MyTask(MainActivity.this);
                mtask.execute(100);
            }
        });
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtask.cancel(true);
                TextView txtProgress = findViewById(R.id.textView);
                txtProgress.setText("Cancelled ");
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    class MyTask extends AsyncTask<Integer, Integer, Boolean> {
        Context context;
        public MyTask(Context context) {
            this.context = context;
            Log.d(TAG, "MyTask: constructor");
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");

            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Boolean doInBackground(Integer... nums) {

            for (int i = 0; i < nums[0]; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i % 5 == 0) {
                    publishProgress(i);
                }
                Log.d(TAG, "doInBackground: Task Running " + i);
            }
            return true;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            Log.d(TAG, "onProgressUpdate: " + values[0]);

            TextView txtProgress = findViewById(R.id.textView);
            txtProgress.setText("Progress " + values[0]);
        }
        @Override
        protected void onPostExecute(Boolean status) {
            super.onPostExecute(status);
            Log.d(TAG, "onPostExecute: ");
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            TextView txtProgress = findViewById(R.id.textView);
            txtProgress.setText("Task Completed ");
        }
    }
}
