package com.example.cinemates.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.cinemates.R;
import com.example.cinemates.model.Genre;
import com.example.cinemates.model.ProductionCompany;
import com.example.cinemates.util.Constants;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author Antonio Di Nuzzo
 * Created 20/05/2022 at 11:40
 */
public class BindingAdapters {

    private static final String TAG = BindingAdapters.class.getSimpleName();

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        //ImageView: Using Glide Library
        Glide.with(view.getContext())
                .load(Constants.ImageBaseURLw500
                        + url)
                .error(R.drawable.ic_round_error_outline_24)
                .centerCrop()
                .into(view);

    }

    @BindingAdapter({"imageUrlLong"})
    public static void loadImageLong(ImageView view, String url) {
        //ImageView: Using Glide Library
        Glide.with(view.getContext())
                .load(Constants.ImageBaseURLw780
                        + url)
                .error(R.drawable.ic_round_error_outline_24)
                .centerCrop()
                .into(view);
    }




    @BindingAdapter({"runtime"})
    public static void loadRuntime(TextView view, long runtime) {
        String value = "";
        if (runtime > 0) {
            int hours = (int) (runtime / 60); //since both are ints, you get an int
            int minutes = (int) (runtime % 60);
            value = String.format("%d h %02d min", hours, minutes);
        }
        loadText(view, value);
    }

    @BindingAdapter({"loadText"})
    public static void loadText(TextView view, String value) {

        if (TextUtils.isEmpty(value)) {
            view.setText("Not specified");
        } else {
            view.setText(value);
        }

    }

    @BindingAdapter({"currency"})
    public static void loadBudget(TextView view, long budget) {
        Locale current = Locale.getDefault();
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance(Currency.getInstance(current).getCurrencyCode()));
        loadText(view, format.format(budget));
    }

    @BindingAdapter("knowAs")
    public static void setKnownAs(TextView view, String[] names) {
        try {
            loadText(view, Arrays.stream(names).collect(Collectors.joining(" - ")));
        } catch (NullPointerException e) {
            view.setText("Not specified");
        }

    }

    @BindingAdapter({"genres"})
    public static void setGenresChip(ChipGroup chipGroup, @NonNull ArrayList<Genre> genres) {
        if (genres != null) {
            for (Genre genre : genres) {

                Chip chip = new Chip(chipGroup.getContext());
                ChipDrawable drawable = ChipDrawable.createFromAttributes(chipGroup.getContext(), null,
                        0, R.style.Widget_MaterialComponents_Chip_Action);
                chip.setChipDrawable(drawable);
                chip.setText(genre.getName());
                chip.setId(genre.getId());
                chipGroup.addView(chip);
            }
        }
    }

    @BindingAdapter({"production_companies"})
    public static void setProductionCompanies(TextView view, ArrayList<ProductionCompany> productionCompanies) {
        if (productionCompanies != null) {
            ArrayList<String> result = new ArrayList<>();
            for (ProductionCompany pc : productionCompanies) {
                result.add(pc.getName());
            }
            view.setText(result.toString());
        }
    }

    @BindingAdapter({"loadOfficial"})
    public static void setIsOfficial(TextView textView, boolean isOfficial) {
        textView.setText(isOfficial ? "Official" : "Not Official");
    }

    @BindingAdapter({"loadThumbnail"})
    public static void loadThumbnail(YouTubeThumbnailView youTubeThumbnailView, String key) {
        /*  initialize the thumbnail image view , we need to pass Developer Key */
        youTubeThumbnailView.initialize(Constants.YT_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                //when initialization is success, set the video id to thumbnail to load
                youTubeThumbnailLoader.setVideo(key);

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        //print or show error when thumbnail load failed
                        Log.e(TAG, "Youtube Thumbnail Error");
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //print or show error when initialization failed
                Log.e(TAG, "Youtube Initialization Failure");

            }
        });
    }

    @BindingAdapter({"hideNSeek"})
    public static void hideNSeek(View view, boolean value) {
        if (value)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.INVISIBLE);

    }

    @BindingAdapter({"voteAverage"})
    public static void loadVoteAverage(TextView textView, Number number) {
        textView.setText(String.format("%,.1f", number.doubleValue()));
    }


}
