package vn.edu.usth.weather;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        PagerAdapter adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(pager);

        MediaPlayer MP = MediaPlayer.create(this,R.raw.piano);
        MP.start();

        Log.i("create", "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        copyFileToExternalStorage(R.raw.piano,"piano.mp3");
        Log.i("start", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("resume","onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("pause", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("stop", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("destroy", "onDestroy: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                new Refresh().execute();
                return true;
            case R.id.setting:
                Log.d("setting", "onOptionsItemSelected: click");
                Intent intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    /* lab 13
    private void Refresh(){
        final Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage( Message msg) {
                String content = msg.getData().getString("server_response");
                Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
            }
        };
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

                Bundle bundle = new Bundle();
                bundle.putString("server_response","some sample json here");

                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
        t.start();
    }
*/

    private class Refresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids){
            try {
                Thread.sleep(10000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Void... voids){}

        @Override
        protected void onPostExecute(Void voids){
            Toast.makeText(getApplicationContext(), "Refreshing Again...", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
        private final int PAGE_COUNT = 3;
        private String titles[] = new String[] {"Ha Noi", "Paris", "Toulouse"};

        public HomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int page) {
            switch (page) {
                case 0:
                    return new WeatherAndForecastFragment();
                case 1:
                    return new WeatherAndForecastFragment();
                case 2:
                    return new WeatherAndForecastFragment();
                default:
                    return new WeatherAndForecastFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int page) {
            return titles[page];
        }
    }

    private void copyFileToExternalStorage(int resourceId, String resourceName) {
        try {
            File file = new File(getExternalFilesDir(null), resourceName);
            InputStream in = getApplicationContext().getResources().openRawResource(resourceId);
            OutputStream out = new FileOutputStream(file);
            byte[] buff = new byte[1024 * 2];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            }
            finally {
                Toast toast = Toast.makeText(getApplicationContext(), file.getAbsolutePath(), Toast.LENGTH_LONG);
                toast.show();
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_LONG);
            toast.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}