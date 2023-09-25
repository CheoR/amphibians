package com.example.amphibians.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//{
//    "name": "Great Basin Spadefoot",
//    "type": "Toad",
//    "description": "This toad spends most of its life underground due to the arid desert conditions in which it lives. Spadefoot toads earn the name because of their hind legs which are wedged to aid in digging. They are typically grey, green, or brown with dark spots.",
//    "img_src": "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/great-basin-spadefoot.png"
//}

@Serializable
data class AmphibianPhoto(
    // Note: each variable corresponds to key name in response JSON object.
    val name: String,
    val type: String,
    val description: String,
    // where img_src comes from response but want to use imgSrc
    @SerialName(value = "img_src")
    val imgSrc: String
)
