package com.droidknights.app2020.binding

import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.droidknights.app2020.R
import com.droidknights.app2020.data.Speaker
import com.droidknights.app2020.data.Sponsor
import com.droidknights.app2020.ui.home.HomeAdapter
import com.droidknights.app2020.ui.home.HomeViewModel
import com.droidknights.app2020.ui.home.SponsorAdapter
import com.droidknights.app2020.ui.home.SponsorItemDecoration
import com.droidknights.app2020.ui.model.UiHomeModel
import com.droidknights.app2020.ui.schedule.detail.DetailTagAdapter
import com.droidknights.app2020.util.clearItemDecoration

@BindingAdapter(value = ["bindImgRes"])
fun ImageView.bindSetImage(resId: Int) = setImageResource(resId)

@BindingAdapter(value = ["isRefreshing"])
fun SwipeRefreshLayout.bindRefreshing(isRefreshing: Boolean) {
    this.isRefreshing = isRefreshing
}

@BindingAdapter(value = ["onRefresh"])
fun SwipeRefreshLayout.bindRefreshListener(onRefreshListener: SwipeRefreshLayout.OnRefreshListener) =
    setOnRefreshListener(onRefreshListener)

@BindingAdapter("webUrl")
fun WebView.bindUrl(value: String?) = value?.let(::loadUrl)

@BindingAdapter("speakerName")
fun TextView.bindSpeakerName(value: Speaker?) {
    text = value?.name
}

@BindingAdapter("speakerImage")
fun ImageView.bindProfile(speaker: Speaker?) {
    Glide.with(this)
        .load(speaker?.profileImage)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.img_droid_space)
                .circleCrop()
        ).into(this)
}

@BindingAdapter("sessionTags")
fun RecyclerView.bindSessionTags(tags: List<String>?) {
    if (tags?.isNotEmpty() == true) {
        isVisible = true
        adapter = (adapter as? DetailTagAdapter ?: DetailTagAdapter()).apply {
                this.tags = tags
            }
    } else {
        isGone = true
    }
}

@BindingAdapter("homeVm", "homeItems")
fun RecyclerView.bindHome(vm: HomeViewModel, items: List<UiHomeModel>?) {
    if (items?.isNotEmpty() == true) {
        isVisible = true
        adapter = HomeAdapter(vm, items)
    } else {
        isGone = true
    }
}

@BindingAdapter("sponsors")
fun RecyclerView.bindSponsors(items: List<Sponsor>?) {
    if (items?.isNotEmpty() == true) {
        isVisible = true
        adapter = SponsorAdapter(items)
        clearItemDecoration()
        addItemDecoration(SponsorItemDecoration())
    } else {
        isGone = true
    }
}

@BindingAdapter("sponsorLogo")
fun ImageView.bindSponsorLogo(@DrawableRes imageResId: Int?) {
    Glide.with(this)
        .load(imageResId)
        .into(this)
}

@BindingAdapter("isActiveEvent")
fun ImageView.isActiveEvent(_isActivated: Boolean?) {
    this.isActivated = _isActivated ?: false
}

@BindingAdapter("sessionSpeakersIntroduce")
fun TextView.bindSpeakersIntroduce(speakers: List<Speaker>?) {
    val builder = StringBuilder()
    for (speaker in speakers.orEmpty()) {
        if ((speakers?.size ?: 0) > 1) {
            builder.append("${speaker.belong} ${speaker.name}\n\n")
        }
        builder.append("${speaker.introduce}\n\n")
    }
    text = builder.toString()
}
