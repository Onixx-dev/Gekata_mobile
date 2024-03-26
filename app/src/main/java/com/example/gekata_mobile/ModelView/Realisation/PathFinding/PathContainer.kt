package com.example.gekata_mobile.ModelView.Realisation.PathFinding

import com.example.gekata_mobile.Models.Basic.Level
import com.example.gekata_mobile.Models.Basic.WayPoint
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

data class PathContainer(
    var startBuildingPoints: ArrayList<WayPoint> = arrayListOf(),
    var endBuildingPoints: ArrayList<WayPoint> = arrayListOf()
) {

    fun wayPointsToLevels(): ArrayList<Level> {
        val levels = arrayListOf<Level>()
        val collection: ArrayList<WayPoint> = arrayListOf()
        var currentId: Int

        collection.addAll(startBuildingPoints)
        collection.addAll(endBuildingPoints)

        currentId = collection.first().id!!

        for (point in collection){
            if (currentId != point.hostLevelObject.id)
            {
                levels.add(point.hostLevelObject)
                currentId = point.hostLevelObject.id!!
            }
        }
            return levels
    }

    fun getItem(index: Int) : WayPoint {
        if (index < startBuildingPoints.size)
            return startBuildingPoints[index]
        if (index >= startBuildingPoints.size && index < endBuildingPoints.size + startBuildingPoints.size)
            return endBuildingPoints[index - startBuildingPoints.size ]
        else return startBuildingPoints.first()
    }

    fun getSize(): Int{
        val rez = startBuildingPoints.size + endBuildingPoints.size
        return rez
    }

}
