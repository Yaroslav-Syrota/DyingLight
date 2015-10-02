package ua.com.thinkmobiles.dyinglight.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;

import ua.com.thinkmobiles.dyinglight.R;

/**
 * Created by CAT_Caterpiller on 02.10.2015.
 */
public class FragmentUtil {
    public static void startFragmentTransaction(FragmentTransaction fragmentTransaction, Fragment fragment) {
        fragmentTransaction.replace(R.id.containerForFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
