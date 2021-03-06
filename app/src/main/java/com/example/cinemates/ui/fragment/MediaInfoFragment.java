package com.example.cinemates.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemates.adapter.MovieRecyclerViewAdapter;
import com.example.cinemates.adapter.YoutubeVideoRecyclerViewAdapter;
import com.example.cinemates.databinding.FragmentMediaInfoBinding;
import com.example.cinemates.model.Movie;
import com.example.cinemates.model.Video;
import com.example.cinemates.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;


public class MediaInfoFragment extends Fragment {

    private FragmentMediaInfoBinding mBinding;
    private MovieViewModel mViewModel;
    private MovieRecyclerViewAdapter mAdapter;
    private YoutubeVideoRecyclerViewAdapter mVideoAdapter;
    private Movie mMovie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MovieRecyclerViewAdapter();
        mVideoAdapter = new YoutubeVideoRecyclerViewAdapter();
        mViewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        mViewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentMediaInfoBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMovie = (Movie) getArguments().getSerializable("movie");
        mBinding.recommendedRecyclerView.setAdapter(mAdapter);
        mBinding.recommendedRecyclerView.setEmptyView(mBinding.emptyViewRecommended.getRoot());
        mBinding.videosRecyclerView.setAdapter(mVideoAdapter);

        observe();
        mViewModel.getMovieDetails(mMovie.getId());
        mViewModel.getSimilar(mMovie.getId());
        mViewModel.getVideos(mMovie.getId());

        mBinding.collectionName.collectionName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CollectionDialogFragment collectionDialogFragment = CollectionDialogFragment.newInstance(mMovie.getBelongs_to_collection());
                collectionDialogFragment.show(fm, "fragment_show_collection");
            }
        });


    }

    private void observe() {
        mViewModel.getMovie().observe(getViewLifecycleOwner(), new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                mBinding.setMovie(movie);
                mMovie = movie;
            }
        });
        mViewModel.getMovieSimilar().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mAdapter.addItems(movies);
            }
        });
        mViewModel.getMovieVideos().observe(getViewLifecycleOwner(), new Observer<List<Video>>() {
            @Override
            public void onChanged(List<Video> videos) {
                if (!videos.isEmpty()) {
                    mBinding.textSectionVideo.setVisibility(View.VISIBLE);
                    mVideoAdapter.setDataList((ArrayList<Video>) videos);
                } else {
                    mBinding.textSectionVideo.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;

    }


}