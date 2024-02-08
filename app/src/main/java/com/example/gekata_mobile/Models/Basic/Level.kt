package com.example.gekata_mobile.Models.Basic

import com.fasterxml.jackson.annotation.JsonProperty


data class Level (

  @JsonProperty("id"    ) var id    : Int?              = null,
  @JsonProperty("name"  ) var name  : String?           = null,
  @JsonProperty("img"   ) var img   : String?           = null,
  @JsonProperty("w_p"   ) var wP    : ArrayList<WayPoint>     = arrayListOf(),
  @JsonProperty("i_p"   ) var iP    : ArrayList<InterestPoint>     = arrayListOf(),
  @JsonProperty("walls" ) var walls : ArrayList<LineWall>  = arrayListOf(),
  @JsonProperty("curve" ) var curve : ArrayList<CurveWall> = arrayListOf()

)