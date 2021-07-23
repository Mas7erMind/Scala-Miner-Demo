// Copyright (c) 2021 Scala
//
// Please see the included LICENSE file for more information.

package io.scalaproject.mobileminerappdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.Config;

public class WizardActivity extends FragmentActivity {
    public static int NUM_SLIDES = 4;

    private ViewPager mViewPager;
    private FloatingActionButton fabBack, fabNext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // Activity was brought to front and not created,
            // Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }

        setContentView(R.layout.fragment_wizard_home);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        int indicatorWidth = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics()) + 0.5f);
        int indicatorHeight = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics()) + 0.5f);
        int indicatorMargin = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                getResources().getDisplayMetrics()) + 0.5f);

        CircleIndicator mCircleIndicator = findViewById(R.id.vpIndicator);
        mViewPager = findViewById(R.id.vpPager);

        me.relex.circleindicator.Config config = new Config.Builder().width(indicatorWidth)
                .height(indicatorHeight)
                .margin(indicatorMargin)
                .animator(R.animator.indicator_animator)
                .animatorReverse(R.animator.indicator_animator_reverse)
                .drawable(R.drawable.black_radius_square)
                .build();
        mCircleIndicator.initialize(config);

        PagerAdapter mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mCircleIndicator.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            final Context context = getApplicationContext();

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fabBack.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        fabBack.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        fabNext.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_arrow_next, context.getTheme()));
                        fabNext.setBackgroundTintList(ResourcesCompat.getColorStateList(context.getResources(), R.color.bg_blue, context.getTheme()));
                        break;
                    case 3:
                        fabNext.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_download, context.getTheme()));
                        fabNext.setBackgroundTintList(ResourcesCompat.getColorStateList(context.getResources(), R.color.bg_green, context.getTheme()));

                        //fabNext.setImageDrawable(getResources().getDrawable(R.drawable.ic_download));
                        //fabNext.setBackgroundTintList(getResources().getColorStateList(R.color.bg_green));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fabBack = findViewById(R.id.fabBack);
        fabBack.setVisibility(View.INVISIBLE); // initial state
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideViewPager(false);
            }
        });

        fabNext = findViewById(R.id.fabNext);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mViewPager.getCurrentItem() == NUM_SLIDES - 1) {
                    Uri uri = Uri.parse(getString(R.string.mobileminer_url));
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else {
                    slideViewPager(true);
                }
            }
        });
    }

    private void slideViewPager(boolean slideNext) {
        int currentItem = mViewPager.getCurrentItem();
        int nextItem;

        if(slideNext) {
            nextItem = currentItem == NUM_SLIDES - 1 ? NUM_SLIDES - 1 : currentItem + 1;
        } else {
            nextItem = currentItem == 0 ? 0 : currentItem - 1;
        }

        mViewPager.setCurrentItem(nextItem);
    }
}