package com.bearm.owlbotdictionary.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bearm.owlbotdictionary.Model.Word;
import com.bearm.owlbotdictionary.Model.WordEntry;
import com.bearm.owlbotdictionary.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
* This class takes references from the course Labs, Slides, and also the android wiki
 * It is the custom adapter that is used elsewhere in the project
*/

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public ArrayList<WordEntry> mDataset;
    public Context context;
    private String example;

    /**
     * This class references information from the course's labs, slides, and the android wiki
     * Provides a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         *  Each data item is just a string in this case
         */
        TextView tvWord;
        TextView tvPronunciation;
        TextView tvDefinition;
        TextView tvType;
        TextView tvExample;
        ImageView ivImage;

        /*
        * This function is explaining about find the pronunciation & definition and type information
        */

        public MyViewHolder(View v) {
            super(v);
            tvWord = v.findViewById(R.id.tv_word);
            tvPronunciation = v.findViewById(R.id.tv_pronunciation);
            tvDefinition = v.findViewById(R.id.tv_definition);
            tvType = v.findViewById(R.id.tv_type);

            tvExample = v.findViewById(R.id.tv_example);
            ivImage = v.findViewById(R.id.iv_image);
        }
    }

    /**
     * Provides a suitable constructor (depends on the kind of dataset)
      */
    public MyAdapter(ArrayList<WordEntry> myDataset, Context myContext) {
        mDataset = myDataset;
        context = myContext;
    }

    /**
     * New views are created (invoked by the layout manager)
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        /**
         * A new view is created here
         */
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(v);
    }

    /**
     * The contents of the view are replaced (invoked by the layout manager)
      * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvWord.setText(mDataset.get(position).word);
        if(!mDataset.get(position).pronunciation.equals("null")){
            holder.tvPronunciation.setText(mDataset.get(position).pronunciation);
        }
        holder.tvDefinition.setText(mDataset.get(position).definition);
        holder.tvType.setText(mDataset.get(position).type);
        example = mDataset.get(position).example;
        if(!example.equals("null")){
            holder.tvExample.setVisibility(View.VISIBLE);
            holder.tvExample.setText(Html.fromHtml(example, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.tvExample.setVisibility(View.GONE);
        }

        if(mDataset.get(position).image != null) {
            Picasso.with(context)
                    .load(mDataset.get(position).image)
                    .into(holder.ivImage);
        }

    }

    /**
     * Returns the size of the dataset (invoked by the layout manager)
      * @return
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

