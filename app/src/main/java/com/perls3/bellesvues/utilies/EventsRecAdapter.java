package com.perls3.bellesvues.utilies;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.perls3.bellesvues.R;
import com.perls3.bellesvues.fragments.EventsDirections;

import java.util.List;

public class EventsRecAdapter extends RecyclerView.Adapter<EventsRecAdapter.ViewHolder>  {
    private List<String> eventImageUrls;
    private List<String> eventTitle;

    public int getPosition() {
        return position;
    }

  public  static int position;


    public EventsRecAdapter( List<String> eventImageUrls,  List<String> eventTitle) {
        this.eventImageUrls = eventImageUrls;
        this.eventTitle = eventTitle;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        ViewHolder viewHolder=new ViewHolder(cv);
        cv.setOnClickListener(v -> {
            position = viewHolder.getAdapterPosition();
            NavDirections action = EventsDirections.actionEventsToOneEvent();
            Navigation.findNavController(v).navigate(action);




        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView=(ImageView)cardView.findViewById(R.id.info_image);

        Glide.with(holder.cardView).load(eventImageUrls.get(position)).into(imageView);
        TextView textView =(TextView)cardView.findViewById(R.id.info_text);
        textView.setText(eventTitle.get(position));

    }

    @Override
    public int getItemCount() {
        return eventImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView=itemView;
        }
    }
}
