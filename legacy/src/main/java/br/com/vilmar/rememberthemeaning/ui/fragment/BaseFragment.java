package br.com.vilmar.rememberthemeaning.ui.fragment;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by vilmar on 1/28/15.
 */
public class BaseFragment extends Fragment{

    private Fragment currentFragment;

    protected void openActivity(Context context, Class activityClass) {
        Intent intent = new Intent(context, activityClass);
        startActivity(intent);
    }

    public void setFragmentContent(Fragment fragment, int resourceID, String label, boolean toBack) {

        if (currentFragment != fragment) {

            currentFragment = fragment;
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

            if (toBack) {
                transaction.addToBackStack(label);
            }

            transaction.replace(resourceID, fragment, label).commitAllowingStateLoss();
        }
    }
}
