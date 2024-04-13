package com.example.gekata_mobile.Models.Basic

import com.example.gekata_mobile.Models.Interfaces.IDataPoint
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty


data class WayPoint(

    @JsonProperty("id") var id: Int? = null,
    @JsonProperty("name") var name: String? = null,
    @JsonProperty("x") var x: Int? = null,
    @JsonProperty("y") var y: Int? = null,
    @JsonProperty("r") var radius: Int? = null,
    @JsonProperty("out") var isOutdoorConnected: Boolean? = null,
    @JsonProperty("lvl") var hostLevel: Int? = null,
    @JsonProperty("fnsh") var finishWayPointsID: ArrayList<Int> = arrayListOf()
) :IDataPoint {

    @JsonIgnore
    var connectedPoints: ArrayList<WayPoint> = arrayListOf()
    @JsonIgnore
    var simplestPathLength: Int = Int.MAX_VALUE
    @JsonIgnore
    var isClosed: Boolean = false
    @JsonIgnore
    var isAllPathsClosed: Boolean = false
    @JsonIgnore
    var isEmpty: Boolean = false
    @JsonIgnore
    lateinit var hostLevelObject: Level

    fun trySetLenghts(list: HashMap<Int, WayPoint>) {
        connectedPoints = getPointsArray(list)
        for (point in connectedPoints) {
            if (!point.isClosed)
                if (simplestPathLength + 1 < point.simplestPathLength)
                    point.simplestPathLength = simplestPathLength + 1
        }
        isClosed = true
    }

    fun getMinDistancePoint(isClosedFlagIgnored: Boolean = false, list: HashMap<Int, WayPoint>? = null): WayPoint {
        var min = Int.MAX_VALUE
        var counter = 0
        var index = 0

        isAllPathsClosed = true

        if(connectedPoints.size != finishWayPointsID.size && list != null)
            this.trySetLenghts(list)

        if (isClosedFlagIgnored) {
            isAllPathsClosed = false
            for (point in connectedPoints) {
                if (point.simplestPathLength <= min) {
                    min = point.simplestPathLength
                    index = counter
                }
                counter++
            }
        } else {
            for (point in connectedPoints) {
                if (point.simplestPathLength <= min && !point.isClosed) {
                    min = point.simplestPathLength
                    index = counter
                    isAllPathsClosed = false
                }
                counter++
            }
        }
//        Log.d("ingetMinDist", "$name + $isAllPathsClosed")
        return connectedPoints[index]
    }


    fun getPointsArray(list: HashMap<Int, WayPoint>): ArrayList<WayPoint> {
        if (!connectedPoints.isEmpty())
            return connectedPoints
        else {
            val points: ArrayList<WayPoint> = arrayListOf()
            for (point in list)
                for (id in finishWayPointsID)
                    if (point.value.id == id)
                        points.add(point.value)
            return points
        }
    }

    fun getDistanse(): String {
        return "$name = $simplestPathLength"
    }

    fun getConnectedNames(): String {
        var str =""
        for(point in connectedPoints)
            str += "${point.id}=${point.name} "
        return str
    }

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