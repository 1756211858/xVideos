package com.night.xvideos.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.night.xvideos.R
import com.night.xvideos.bean.Creampie
import kotlinx.android.synthetic.main.video_item.view.*

/**
 * 热门视频界面
 */
class CreampieAdapter(private var context: Context,
                      var dataList: MutableList<Creampie>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mClickListener: ((View, Int) -> Unit)? = null
    private var mLongClickListener: ((View, Int) -> Unit)? = null

    val options = RequestOptions()
            .error(R.drawable.thumb2)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //造成性能下降，先删除
        //holder.setIsRecyclable(false)
        with(holder as ViewHolder) {
            holder.bind(dataList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.video_item, parent, false)
                , mClickListener, mLongClickListener)

    }

    fun setOnItemClickListener(listener: ((View, Int) -> Unit)?,
                               listener2: ((View, Int) -> Unit)?) {
        mClickListener = listener
        mLongClickListener = listener2
    }

    fun addFooter(position: Int, list: MutableList<Creampie>) {
        dataList.addAll(position, list)
        notifyItemInserted(position)
        notifyItemRangeChanged(dataList.size - 100, 100)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    inner class ViewHolder(itemView: View,
                           private var mClickListener: ((View, Int) -> Unit)?,
                           private var mLongClickListener: ((View, Int) -> Unit)?)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {


        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            if (mLongClickListener != null && v != null) {
                mLongClickListener?.invoke(v, layoutPosition)
            }
            return true
        }

        override fun onClick(v: View?) {
            if (v != null) {
                mClickListener?.invoke(v, layoutPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(bean: Creampie) {
            Glide.with(context)
                    .load(bean.imgUrl)
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemView.video_imageView)
            itemView.video_title.text = bean.title
            itemView.video_duration.text = "视频时长：${bean.duration}"
        }
    }
}