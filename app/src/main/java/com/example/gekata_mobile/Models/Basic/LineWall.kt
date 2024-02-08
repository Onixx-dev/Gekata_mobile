package com.example.gekata_mobile.Models.Basic

import com.fasterxml.jackson.annotation.JsonProperty


data class LineWall (

  @JsonProperty("id"  ) var id  : Int? = null,
  @JsonProperty("spx" ) var startX : Int? = null,
  @JsonProperty("spy" ) var startY : Int? = null,
  @JsonProperty("epx" ) var endX : Int? = null,
  @JsonProperty("epy" ) var endY : Int? = null

)