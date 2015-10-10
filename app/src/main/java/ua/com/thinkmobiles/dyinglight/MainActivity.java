package ua.com.thinkmobiles.dyinglight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ua.com.thinkmobiles.dyinglight.fragments.FragmentMenu;
import ua.com.thinkmobiles.dyinglight.fragments.FragmentProgress;
import ua.com.thinkmobiles.dyinglight.fragments.FragmentUtil;

/**
 * Created by CAT_Caterpiller on 02.10.2015.
 */
public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        FragmentUtil.startFragmentTransaction(getFragmentManager().beginTransaction(), new FragmentProgress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_progress:
                FragmentUtil.startFragmentTransaction(getFragmentManager().beginTransaction(), new FragmentProgress());
                break;
            case R.id.action_menu:
                FragmentUtil.startFragmentTransaction(getFragmentManager().beginTransaction(), new FragmentMenu());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
