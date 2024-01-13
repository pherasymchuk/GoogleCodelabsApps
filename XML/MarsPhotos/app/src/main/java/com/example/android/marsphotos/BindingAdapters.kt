package com.example.android.marsphotos

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsphotos.network.MarsPhoto
import com.example.android.marsphotos.overview.MarsApiStatus
import com.example.android.marsphotos.overview.PhotoGridAdapter

@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: List<MarsPhoto>?) {
    val adapter: PhotoGridAdapter = adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("marsApiStatus")
fun ImageView.bindStatus(status: MarsApiStatus) {
    when (status) {
        MarsApiStatus.Loading -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.loading_animation)
        }

        MarsApiStatus.Error -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_connection_error)
        }

        MarsApiStatus.Done -> {
            visibility = View.GONE
        }
    }
}
