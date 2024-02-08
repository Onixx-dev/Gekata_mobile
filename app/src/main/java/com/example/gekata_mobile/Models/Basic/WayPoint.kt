package com.example.gekata_mobile.Models.Basic

import com.fasterxml.jackson.annotation.JsonProperty


data class WayPoint (

  @JsonProperty("id"   ) var id   : Int?           = null,
  @JsonProperty("name" ) var name : String?        = null,
  @JsonProperty("x"    ) var x    : Int?           = null,
  @JsonProperty("y"    ) var y    : Int?           = null,
  @JsonProperty("r"    ) var radius    : Int?           = null,
  @JsonProperty("out"  ) var isOutdoorConnected  : Boolean?       = null,
  @JsonProperty("lvl"  ) var hostLevel  : Int?           = null,
  @JsonProperty("fnsh" ) var finishWayPointsID : ArrayList<Int> = arrayListOf()

)