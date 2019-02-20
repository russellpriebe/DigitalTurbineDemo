package com.penda.digitalturbinedemo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Ad( val appId: String,
               val averageRatingImageURL: String,
               val bidRate: String,
               val billingTypeId: String,
               val callToAction: String,
               val campaignDisplayOrder: String,
               val campaignId: String,
               val campaignTypeId: String,
               val categoryName: String,
               val clickProxyURL: String,
               val creativeId: String,
               val homeScreen: String,
               val impressionTrackingURL: String,
               val isRandomPick: String,
               val NumberOfRatings: String,
               val productDescription: String,
               val productId: String,
               val productName: String,
               val productThumbnail: String,
               val rating: String): Parcelable {
}