package io.scalaproject.mobileminerappdemo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public int getCount() {
        return WizardActivity.NUM_SLIDES;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1 : {
                return new WizardPage2();
            }
            case 2 : {
                return new WizardPage3();
            }
            case 3 : {
                return new WizardPage4();
            }
            case 0:
            default: {
                return new WizardPage1();
            }
        }
    }
}
