package com.dimetris.uploadmultiimage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.tk_rec_item_photo.view.*
import java.io.File

class PhotosRecView (val data : ArrayList<TkPhoto>, val context: Context) : RecyclerView.Adapter<PhoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoViewHolder {
        return PhoViewHolder(LayoutInflater.from(context).inflate(R.layout.tk_rec_item_photo, parent, false))    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PhoViewHolder, i: Int) {
//        holder?.image?.setOnClickListener {
//            Log.e("imagedata",data.get(position).PhotoName+"")
//        }
        Glide
            .with(context)
            .load(File(data[i].PhotoURI.toString()))
            .centerCrop()
            .placeholder(R.color.black)
            .into(holder.PhotoIV)
    }
}
class PhoViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val PhotoIV = view.PhotoIV

}