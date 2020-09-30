package com.demon.easyjetpack.module.paging

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.demon.easyjetpack.R
import com.demon.easyjetpack.bean.ArticleBean
import com.demon.easyjetpack.list.DataViewHolder
import kotlinx.android.synthetic.main.list_paging.view.*

/**
 * @author DeMon
 * Created on 2020/9/28.
 * E-mail 757454343@qq.com
 * Desc:
 */
class PagingAdapter : PagingDataAdapter<ArticleBean, DataViewHolder>(DiffComparator) {
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
                val item = getItem(position)
                holder.itemView.run {
                    tv_text.text = item?.title
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(R.layout.list_paging, parent)
    }

}


object DiffComparator : DiffUtil.ItemCallback<ArticleBean>() {
    override fun areItemsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
        return oldItem.id == newItem.id
    }
}