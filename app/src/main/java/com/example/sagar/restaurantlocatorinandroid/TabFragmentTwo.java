package com.example.sagar.restaurantlocatorinandroid;

/**
 * Created by Sagar on 7/18/2016.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;


public class TabFragmentTwo extends Fragment {

    private static final String ARG_EXAMPLE = "this_is_a_constant";
    private String example_data;
    private static final String API_KEY = "AIzaSyAUOx1zt79BHU9g0uFA00OydezvAd3UU1Q";

    private static Context mContext;
    private static RestaurantAdapter mAdapter;
    private static RecyclerView recyclerView;
    private static ArrayList<Places> mPlacesList;
    public TabFragmentTwo() {

    }

    public static TabFragmentTwo newInstance(ArrayList<Places> restaurantList) {
        Log.d("checking","fragment is getting called "+restaurantList.size());
        TabFragmentTwo fragment = new TabFragmentTwo();
        mPlacesList = restaurantList;
        for(Places places : restaurantList){
            Log.d("checking",places.toString());
        }

        Collections.sort(restaurantList);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_two,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.expense_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        updateUI();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    public void updateUI() {
        Log.d("Inside*************** ", " updateUI");

            mAdapter = new RestaurantAdapter(getContext(),mPlacesList);
            Log.d("Adapter class " , mAdapter.toString());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private class RestaurantListHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mNameTextView;
        private ImageView mImageViewIcon;
        private RatingBar mRatingsView;
        private ImageView mIsRestaurantOpen;

        private Places mPlaces;

        public RestaurantListHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            mImageViewIcon = (ImageView)  itemView.findViewById(R.id.imageViewHolder);
            mRatingsView = (RatingBar) itemView.findViewById(R.id.listrating);
            mIsRestaurantOpen = (ImageView) itemView.findViewById(R.id.opennow);
        }

        public void bindRestaurants(Places places) {
            String isOpen = "false";
            mPlaces = places;
            mNameTextView.setText(mPlaces.getName());
            float ratingvalue = (Float) mPlaces.getRating();
            mRatingsView.setRating(ratingvalue);
            isOpen  =  mPlaces.getOpennow();
            if(isOpen!=null){
                Log.d("IsOpen value in final" ,isOpen);
            } else {
                Log.d("IsOpen " ,"value in final");
            }


            if(isOpen == "true"){
                mIsRestaurantOpen.setImageDrawable(getResources().getDrawable(R.drawable.open_sign));

            } else {
                mIsRestaurantOpen.setImageDrawable(getResources().getDrawable(R.drawable.is_not_open));
            }

            String restaurantpic ;
            String url = mPlaces.getIcon();
            String photoreference = mPlaces.getPhotoreference();
            if(photoreference==null){
                Log.d("NO ", "Photo found");
                restaurantpic = "http://il1.picdn.net/shutterstock/videos/1679470/thumb/1.jpg?i10c=img.resize(height:160)";
            } else {
                restaurantpic = "https://maps.googleapis.com/maps/api/place/photo?" +
                        "maxwidth=400" +
                        "&photoreference=" +photoreference +
                        "&key="+API_KEY;
            }

        Log.d("Loading restaurantpic" , restaurantpic);

            Glide
                    .with(mContext)
                    .load(restaurantpic)
                    .centerCrop()
                    .crossFade()
                    .into(mImageViewIcon);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(getActivity(), ExpenseDetail.class);
//            intent.putExtra("Expense_Id",mPlaces.getId());
//            startActivity(intent);
        }


    }


    private class RestaurantAdapter extends RecyclerView.Adapter<RestaurantListHolder> {
        private ArrayList<Places> mRestaurantLists;


        public RestaurantAdapter(Context context,ArrayList<Places> restaurants) {
            mContext = context;
            mRestaurantLists = restaurants;

        }

        @Override
        public RestaurantListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_restaurants, parent, false);
            return new RestaurantListHolder(view);
        }

        @Override
        public void onBindViewHolder(RestaurantListHolder holder, int position) {
            Places restaurants = mRestaurantLists.get(position);
            holder.bindRestaurants(restaurants);
        }

        @Override
        public int getItemCount() {
            return mRestaurantLists.size();
        }

    }
}
