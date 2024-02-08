package com.example.gekata_mobile.Models.Basic

import com.fasterxml.jackson.annotation.JsonProperty



data class InterestPoint (

  @JsonProperty("id"    ) var id    : Int?    = null,
  @JsonProperty("name"  ) var name  : String? = null,
  @JsonProperty("x"     ) var x     : Int?    = null,
  @JsonProperty("y"     ) var y     : Int?    = null,
  @JsonProperty("r"     ) var radius     : Int?    = null,
  @JsonProperty("descr" ) var description : String? = null,
  @JsonProperty("type"  ) var type  : String? = null

)