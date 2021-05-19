package sk.upjs.ics.android.koncovyprojekt2;
import android.app.AlertDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import java.util.Calendar;
import sk.upjs.ics.android.koncovyprojekt2.provider.NoteContentProvider;
import sk.upjs.ics.android.koncovyprojekt2.provider.Provider;

import static sk.upjs.ics.android.koncovyprojekt2.Defaults.DISMISS_ACTION;
import static sk.upjs.ics.android.koncovyprojekt2.Defaults.NO_COOKIE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final int TEST_LOADER_ID = 0;
    private static final int INSERT_TEST_TOKEN = 0;
    private static final int DELETE_TEST_TOKEN = 0;
    private static final int UPDATE_TEST_TOKEN = 0;
    private DrawerLayout drawer;
    public static SharedPreferences settings;
    public static String meno;
    public static String priezvisko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        meno = settings.getString("MENO", "");
        priezvisko = settings.getString("PRIEZVISKO","");
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("MENO", meno);
        editor.putString("PRIEZVISKO", priezvisko);
        editor.apply();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_favorites:
                    selectedFragment = new FavoritesFragment();
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.testy:
                selectedFragment=new InfoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;
            case R.id.karantena:
                selectedFragment=new Karantena();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;
            case R.id.telefon:
                selectedFragment=new Kontakty();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;
            case R.id.qaa:
                selectedFragment=new Otazky();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;
            case R.id.info:
                selectedFragment=new InfoONas();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item) {
            createNewTest();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNewTest() {
        LayoutInflater inflanter = this.getLayoutInflater();
        View allertDialogView = inflanter.inflate(R.layout.dialog, null);
        AlertDialog.Builder builder= new AlertDialog.Builder(this,R.style.DialogTheme);

        builder.setTitle("Pridaj nový test");
        //builder.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, title.length, 0);
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog, null);
        builder.setView(customLayout);
        builder.create();
        final RadioGroup tests = customLayout.findViewById(R.id.tests);
        final RadioGroup days  = customLayout.findViewById(R.id.days);
        final CalendarView calendar= customLayout.findViewById(R.id.calendar);
        final Calendar cal = Calendar.getInstance();
        final String[] Date = new String[3];

        Date[0]= String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        Date[1]= String.valueOf(cal.get(Calendar.MONTH)+1);
        Date[2]= String.valueOf(cal.get(Calendar.YEAR));
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Date[0] = String.valueOf(dayOfMonth);
                Date[1] = String.valueOf(month+1);
                Date[2] = String.valueOf(year);
            }
        });

        builder.setPositiveButton("ULOŽ", (dialog, which) -> {
                String typ;
                String dlzka;
                String datum;
                int radioID=days.getCheckedRadioButtonId();
                switch (radioID){
                    case R.id.day7:  dlzka="7";  break;
                    case R.id.day14: dlzka="14"; break;
                    case R.id.day21: dlzka="21"; break;
                    default:dlzka="7";
                }
                radioID=tests.getCheckedRadioButtonId();
                switch (radioID){
                    case R.id.Anti_test: typ="antigen"; break;
                    case R.id.PCR_test: typ="PCR"; break;
                    default:typ="antigen";
                }
                datum= Date[0]+" "+Date[1]+" "+Date[2];

                insertIntoContentProvider(typ,dlzka, datum);

        });
                builder.setNegativeButton("Zrušiť", DISMISS_ACTION);
                builder.show();
    }

    private void insertIntoContentProvider(String typ, String dlzka, String datum) {
        Uri uri = NoteContentProvider.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(Provider.Test.TEST_LENGTH, dlzka);
        values.put(Provider.Test.TEST_TYPE, typ);
        values.put(Provider.Test.TEST_DATE, datum);
        AsyncQueryHandler insertHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Toast.makeText(MainActivity.this, "Test bol uložený", Toast.LENGTH_SHORT).show();
            }
        };
        insertHandler.startInsert(INSERT_TEST_TOKEN, NO_COOKIE, uri, values);
    }
}