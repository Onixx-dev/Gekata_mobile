package com.example.gekata_mobile.Models.Basic

import com.fasterxml.jackson.annotation.JsonProperty


data class Project(

  @JsonProperty("pname" ) var pname : String?         = null,
  @JsonProperty("ppath" ) var ppath : String?         = null,
  @JsonProperty("bldg"  ) var building  : List<Building>? = null

)