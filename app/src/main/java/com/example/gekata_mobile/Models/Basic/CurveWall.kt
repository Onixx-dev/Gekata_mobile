package com.example.gekata_mobile.Models.Basic

import com.fasterxml.jackson.annotation.JsonProperty

data class CurveWall(
    @JsonProperty("id"  ) var id  : Int? = null,
    @JsonProperty("spx" ) var startX : Int? = null,
    @JsonProperty("spy" ) var startY : Int? = null,
    @JsonProperty("epx" ) var endX : Int? = null,
    @JsonProperty("epy" ) var endY : Int? = null,
    @JsonProperty("cpx" ) var controlX : Int? = null,
    @JsonProperty("cpy" ) var controlY : Int? = null
)
