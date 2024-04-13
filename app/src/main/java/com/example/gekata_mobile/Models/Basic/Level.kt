package com.example.gekata_mobile.Models.Basic

import android.telecom.CallEndpoint
import com.example.gekata_mobile.ModelView.Realisation.PathFinding.TreeNode
import com.example.gekata_mobile.Models.Interfaces.IDataPoint
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty


data class Level (

  @JsonProperty("id"    ) var id    : Int?              = null,
  @JsonProperty("name"  ) var name  : String?           = null,
  @JsonProperty("img"   ) var img   : String?           = null,
  @JsonProperty("w_p"   ) var wayPoints    : ArrayList<WayPoint>     = arrayListOf(),
  @JsonProperty("i_p"   ) var interestPoints    : ArrayList<InterestPoint>     = arrayListOf(),
  @JsonProperty("walls" ) var walls : ArrayList<LineWall>  = arrayListOf(),
  @JsonProperty("curve" ) var curve : ArrayList<CurveWall> = arrayListOf()

){

  @JsonIgnore
  var isNormalize: Boolean = false
  @JsonIgnore
  var isContainsPath: Boolean = false

  @JsonIgnore
  var maxX: Int = Int.MIN_VALUE
  @JsonIgnore
  var maxY: Int = Int.MIN_VALUE


  lateinit var startPoint: IDataPoint
  lateinit var endpoint: IDataPoint

  @JsonIgnore
  lateinit var pathTreeRoot: TreeNode
  @JsonIgnore
  lateinit var finalPath: TreeNode
  @JsonIgnore
  var test: TreeNode = TreeNode(0, 0)
  


  fun isContainsInterestPoint(id: Int, name: String?): Boolean {
    for (point in interestPoints)
      if (point.id == id && point.name.equals(name))
        return true
    return false
  }

  fun isTreeNodeInitialised() = ::pathTreeRoot.isInitialized

  fun isFinalPathInitialised() = ::finalPath.isInitialized

}