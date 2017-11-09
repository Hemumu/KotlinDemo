package com.helin.kotlindemo.fragment.pic

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.helin.kotlindemo.R
import com.helin.kotlindemo.commen.ScreenUtil
import com.helin.kotlindemo.model.bean.Huaban

/**
 * Created by qyhl2 on 2017/11/9.
 */
class  PicPageAdapter(var items: List<Huaban>?) : RecyclerView.Adapter<PicPageAdapter.MyViewHolder>()  {
    private var mHeights: MutableMap<Int, Int> = HashMap()


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.imageView)
                .asBitmap()
                .load(items?.get(position)?.thumb)
                .transition(BitmapTransitionOptions().crossFade(800))
                .listener(object : RequestListener<Bitmap> {
                    override fun onResourceReady(bitmap: Bitmap, p1: Any?, p2: Target<Bitmap>?, p3: DataSource?, p4: Boolean): Boolean {
                        val imageViewWidth = (ScreenUtil.w(holder.imageView.context)
                                - holder.imageView.context.resources.getDimensionPixelSize(R.dimen.picCardMargin) * 4) / 2
                        val imageViewHeight: Int = ((imageViewWidth.toDouble() / bitmap.width) * bitmap.height).toInt()
                        mHeights.put(position, imageViewHeight)
                        holder.imageView.layoutParams.height = imageViewHeight
                        holder.imageView.layoutParams.width = imageViewWidth
                        return false
                    }

                    override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Bitmap>?, p3: Boolean): Boolean {
                        return false
                    }

                })
                .into(holder.imageView)
        holder.imageView.setOnClickListener({
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onClick(holder.imageView, items?.get(position)?.thumb!!)
            }

        })
        if (mHeights.containsKey(position)) {
            holder.imageView.layoutParams.height = mHeights[position]!!
        }
    }

    interface OnItemClickListener {
        fun onClick(view: View, url: String)
    }

    fun setOnItemCLick(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    var mOnItemClickListener: OnItemClickListener? = null

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder? {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pick, parent, false))
    }

    class MyViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        val imageView: ImageView = item.findViewById(R.id.pic)
    }
}