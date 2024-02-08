package com.example.gekata_mobile.Models.Basic

import com.fasterxml.jackson.annotation.JsonProperty


data class Building (

  @JsonProperty("id"   ) var id   : Int?            = null,
  @JsonProperty("name" ) var name : String?         = null,
  @JsonProperty("adr"  ) var address  : String?         = null,
  @JsonProperty("g_a"  ) var googleAddress   : String?         = null,
  @JsonProperty("y_a"  ) var yandexAddress   : String?         = null,
  @JsonProperty("o_a"  ) var osmAddress   : String?         = null,
  @JsonProperty("2_a"  ) var twoGisAddress   : String?         = null,
  @JsonProperty("lvls" ) var levels : ArrayList<Level> = arrayListOf()

)