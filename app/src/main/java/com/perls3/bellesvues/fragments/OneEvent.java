package com.perls3.bellesvues.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.perls3.bellesvues.R;
import com.perls3.bellesvues.utilies.EventsRecAdapter;
import com.perls3.bellesvues.view_models.EventsViewModel;
import com.perls3.bellesvues.view_models.OneEventViewModel;

public class OneEvent extends Fragment {
    ImageView imageView;
    TextView textView;

    private OneEventViewModel mViewModel;

    private EventsViewModel eventsViewModel;

    public static OneEvent newInstance() {
        return new OneEvent();
    }

    @Keep
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventsViewModel = new ViewModelProvider(requireActivity()).get(EventsViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int itemClickedinEvents = eventsViewModel.getPos();
        String imageUrl = AlertFragment.eventsHelper.getListFinalImage().get(EventsRecAdapter.position);
        String textEvent = AlertFragment.eventsHelper.getListFinalText().get(EventsRecAdapter.position);
        imageView = (ImageView) view.findViewById(R.id.imageViewOneEvent);
        textView = (TextView) view.findViewById(R.id.textViewOneEvent);
        Glide.with(this).load(imageUrl).into(imageView);
        textView.setText(textEvent);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.one_event_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}