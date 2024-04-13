package com.example.gekata_mobile.Models.Basic

import com.example.gekata_mobile.Models.Interfaces.IDataPoint
import com.fasterxml.jackson.annotation.JsonProperty



data class InterestPoint (

  @JsonProperty("id"    ) var id    : Int?    = null,
  @JsonProperty("name"  ) var name  : String? = null,
  @JsonProperty("x"     ) var x     : Int?    = null,
  @JsonProperty("y"     ) var y     : Int?    = null,
  @JsonProperty("r"     ) var radius     : Int?    = null,
  @JsonProperty("descr" ) var description : String? = null,
  @JsonProperty("type"  ) var type  : String? = null

) : IDataPoint {
  override fun getX(): Int {
    return x!!
  }

  override fun getY(): Int {
    return y!!
  }

  override fun _getID(): Int {
    return id!!
  }

  override fun normalizeBy(changeAtX: Int, changeAtY: Int) {
    x = x?.minus(changeAtX)
    y = y?.minus(changeAtY)
  }

}