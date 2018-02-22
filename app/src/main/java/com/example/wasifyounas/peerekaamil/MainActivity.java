package com.example.wasifyounas.peerekaamil;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    PDFView pdfView;
  //  ScrollHandle scrollHandle;
    EditText edSearch;
    Button btnShare, btnAddBookmark,btnBookMarks,btnBrightness;
    TextView tVTotalPages;
    int pageNo = -1;
    int totalPages;
    LinearLayout liHeader, liFooter;

    boolean isPdfViewClicked = false;
    SharedPreferences prefs;

    String lastPageNo = "LastPageNo";
    DBhandler dBhandler;

    int defaultPage;
    ArrayList <Bookmarks> localbookmarksList;

    android.app.FragmentManager fm = getFragmentManager();

    private SeekBar seekBar;
    //Variable to store brightness value
    private int brightness;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;

    boolean brightnessVisibility = false;

    @Override
    protected void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        dBhandler = DBhandler.getinstance(this);


         defaultPage = getIntent().getIntExtra("default_page", 2);

        //Instantiate seekbar object
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        //Get the content resolver
        cResolver =  getContentResolver();


        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS);

        if (result != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_SETTINGS},
                    1);
        }

        //Get the current window
        window = getWindow();

        //Set the seekbar range between 0 and 255
        //seek bar settings//
        //sets the range between 0 and 255
        seekBar.setMax(255);
        //set the seek bar progress to 1
        seekBar.setKeyProgressIncrement(1);

        try

        {
            //Get the current system brightness
            brightness = android.provider.Settings.System.getInt(cResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS);
        }

        catch (Settings.SettingNotFoundException e)

        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }
        //Set the progress of the seek bar based on the system's brightness
        seekBar.setProgress(brightness);
        seekBar.setVisibility(View.GONE);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

        {
            public void onStopTrackingTouch(SeekBar seekBar)

            {
                //Set the system brightness using the brightness variable value
                android.provider.Settings.System.putInt(cResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
                //Get the current window attributes
                WindowManager.LayoutParams layoutpars = window.getAttributes();
                //Set the brightness of this window
                layoutpars.screenBrightness = brightness / (float)255;
                //Apply attribute changes to this window
                window.setAttributes(layoutpars);
            }

            public void onStartTrackingTouch(SeekBar seekBar)

            {
                //Nothing handled here
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)

            {
                    brightness = progress;
                float perc = (brightness /(float)255)*100;
            }

        });


        prefs = this.getSharedPreferences(
                "myPref", Context.MODE_PRIVATE);

        btnAddBookmark = (Button) findViewById(R.id.btnAddbookMark);
         btnBookMarks= (Button) findViewById(R.id.btnBookMarks);
         btnBrightness= (Button) findViewById(R.id.btnfinish);

        btnBrightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!brightnessVisibility)
                {
                    seekBar.setVisibility(View.VISIBLE);
                    brightnessVisibility = true;
                }
                else
                {
                    seekBar.setVisibility(View.GONE);
                    brightnessVisibility = false;
                }
            }
        });


        btnAddBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dBhandler.AddBookmarks(pdfView.getCurrentPage());
                Toast.makeText(MainActivity.this, "Bookmark Added", Toast.LENGTH_SHORT).show();
            }
        });

        btnBookMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localbookmarksList= dBhandler.getBookmarks();

                showDialogue();
               // Toast.makeText(MainActivity.this, "Size is" + localbookmarksList.size(), Toast.LENGTH_SHORT).show();
            }
        });

        pdfView =(PDFView) findViewById(R.id.pdfView);
     //   ScrollBar scrollBar = (ScrollBar) findViewById(R.id.scrollBar);
      //  pdfView.setScrollBar(scrollBar);

       // scrollBar.setHorizontal(true);
        edSearch = (EditText) findViewById(R.id.edSearch);
        tVTotalPages = (TextView) findViewById(R.id.tVTotalPages);

      //  pdfView.setSwipeVertical(false);
     //   pdfView.canScrollVertically(0);
     //   pdfView.scroll


        liHeader = (LinearLayout) findViewById(R.id.header);
        liFooter = (LinearLayout) findViewById(R.id.footer1);

        liHeader.postDelayed(new Runnable() {
            @Override
            public void run() {
                liHeader.setVisibility(View.GONE);
            }
        }, 3000);


        liFooter.postDelayed(new Runnable() {
            @Override
            public void run() {
                liFooter.setVisibility(View.GONE);
            }
        }, 3000);



        getSupportActionBar().hide();

        pdfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPdfViewClicked)
                {
                    liHeader.setVisibility(View.VISIBLE);
                    liFooter.setVisibility(View.VISIBLE);
                    isPdfViewClicked = true;
                }
                else
                {
                    liHeader.setVisibility(View.GONE);
                    liFooter.setVisibility(View.GONE);
                    seekBar.setVisibility(View.GONE);
                    brightnessVisibility = false;
                    isPdfViewClicked = false;
                }
            }
        });



     /*   if (prefs.contains(lastPageNo))
        {
            defaultPage = prefs.getInt(lastPageNo , 2);
        }
*/


        pdfView.fromAsset("peer e kamil.pdf").
                defaultPage(defaultPage).swipeHorizontal(true).enableDoubletap(true)
                .scrollHandle(new DefaultScrollHandle(this)).enableAntialiasing(true).pageFitPolicy(FitPolicy.HEIGHT).spacing(0).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {
                tVTotalPages.setText("of " + String.valueOf(pdfView.getPageCount()));
            }
        }).
                load();




        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                  //  performSearch();
                  //  Toast.makeText(MainActivity.this, "search pressed", Toast.LENGTH_SHORT).show();
                    if (!edSearch.getText().toString().equals("")) {
                     //   if (pageNo)
                        pageNo = Integer.parseInt(edSearch.getText().toString());
                        pdfView.jumpTo(pageNo-1);
                    }
                    Toast.makeText(MainActivity.this, "total pages are " + totalPages, Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

    }


  /*  private void nextPage ()
    {
        pdfView.jumpTo(pdfView.getCurrentPage()+1 , true);
    }

    private void prePage ()
    {
        pdfView.jumpTo(pdfView.getCurrentPage()-1 , true);
    }*/


    @Override
    protected void onStop() {
        super.onStop();

        GlobalBus.getBus().unregister(this);
        prefs.edit().putInt(lastPageNo, pdfView.getCurrentPage()).apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, welcome_screen.class);
        startActivity(intent);
        finish();
    }

    @Subscribe
    public void goToPage (Events.PageChangeListener pageChangeListener)
    {
        pdfView.jumpTo(pageChangeListener.getPageNo(),true);
    }


    private void showDialogue ()
    {
        BookmarkFragment dFragment = new BookmarkFragment();

        // Show DialogFragment
        dFragment.show(fm, "");
      //  dFragment.show(fm, "Dialog Fragment");
    }
}
