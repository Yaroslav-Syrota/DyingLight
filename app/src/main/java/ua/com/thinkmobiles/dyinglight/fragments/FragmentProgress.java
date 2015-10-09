package ua.com.thinkmobiles.dyinglight.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.thinkmobiles.dyinglight.R;
import ua.com.thinkmobiles.dyinglight.viewes.TempProgress;

/**
 * Created by CAT_Caterpiller on 02.10.2015.
 */
public class FragmentProgress extends Fragment {
    private TempProgress mProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        mProgress = (TempProgress) view.findViewById(R.id.tempProgress);
        mProgress.showAnim();

        return view;
    }
}
