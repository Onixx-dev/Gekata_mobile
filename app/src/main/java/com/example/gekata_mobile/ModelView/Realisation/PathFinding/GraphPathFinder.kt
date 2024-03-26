package com.example.gekata_mobile.ModelView.Realisation.PathFinding

import android.util.Log
import com.example.gekata_mobile.Models.Basic.Building
import com.example.gekata_mobile.Models.Basic.InterestPoint
import com.example.gekata_mobile.Models.Basic.WayPoint

class GraphPathFinder() {

    private lateinit var startPoint: InterestPoint
    private lateinit var endPoint: InterestPoint

    private lateinit var startWaypoint: WayPoint
    private lateinit var endWaypoint: WayPoint

    constructor(_startPoint: InterestPoint, _endPoint: InterestPoint, _startWayPoint: WayPoint, _endWayPoint: WayPoint) : this(){
        startPoint = _startPoint
        endPoint = _endPoint
        startWaypoint = _startWayPoint
        endWaypoint = _endWayPoint
    }


    fun pathIndoor(building: Building?, isPathToExit: Boolean): ArrayList<WayPoint> {
        val points = initModel(building)
        setWeights(points, isPathToExit = isPathToExit, end = this.endWaypoint)
        val temp = createPath(points, isPathToExit = isPathToExit)
        Log.d("way point build", building!!.name!!)
        for (item in temp)
            Log.d("way point path", item.name!!)
        return temp
    }

    private fun initModel(building: Building?): HashMap<Int, WayPoint> {
        val points: HashMap<Int, WayPoint> = hashMapOf()

        if (!this::startWaypoint.isInitialized) {
            startWaypoint = WayPoint()
            startWaypoint.isEmpty = true
        }

        if (!this::endWaypoint.isInitialized) {
            endWaypoint = WayPoint()
            endWaypoint.isEmpty = true
        }

        for (level in building!!.levels) {
            for (point in level.wayPoints) {
                point.isClosed = false
                point.isAllPathsClosed = false
                point.simplestPathLength = Int.MAX_VALUE
                point.hostLevelObject = level
                points.put(point.id!!, point)
            }
            if (level.isContainsInterestPoint(startPoint!!.id!!, startPoint!!.name)) {
                startWaypoint = level.wayPoints[0]
            }

            if (level.isContainsInterestPoint(endPoint!!.id!!, endPoint!!.name)) {
                endWaypoint = level.wayPoints[0]
            }
        }
        return points
    }

    private fun setWeights(
        points: HashMap<Int, WayPoint>,
        isPathToExit: Boolean = true,
        end: WayPoint? = null
    ) {
        var counter = 0
        var startTreeIndex: Int
        var currentIndex: Int = 0

        if (isPathToExit) {
            for (point in points)
                if (point.value.isOutdoorConnected!!)
                    currentIndex = point.value.id!!
        } else {
            if (points.contains(end!!.id))
                currentIndex = end.id!!
            else
                currentIndex = points.keys.first()
        }

        points[currentIndex]!!.simplestPathLength = 0
        startTreeIndex = currentIndex
        do {
//            Log.d("current id's", points[currentIndex]!!.getDistanse())
            if (points[currentIndex]!!.isAllPathsClosed) {
                currentIndex = startTreeIndex
                counter--
            }
            points[currentIndex]!!.trySetLenghts(points)
            currentIndex = points[currentIndex]!!.getMinDistancePoint().id!!
            counter++
        } while (counter < points.size)
//        for (item in points)
//            Log.d(points.size.toString(), item.value.getDistanse())
    }

    private fun createPath(
        points: HashMap<Int, WayPoint>,
        isPathToExit: Boolean = true
    ): ArrayList<WayPoint> {
        var currentIndex: Int
        var nextStepIndex: Int
        var exitWayPointId: Int = 0
        val path: ArrayList<WayPoint> = arrayListOf()

        if (isPathToExit)
            currentIndex = startWaypoint.id!!
        else {
            for (point in points)
                if (point.value.isOutdoorConnected!!)
                    exitWayPointId = point.value.id!!
            currentIndex = exitWayPointId
        }

        path.add(points[currentIndex]!!)
        if (points[currentIndex]!!.hostLevel != endWaypoint.hostLevel)// && !isPathToExit)
            do {
                nextStepIndex = points[currentIndex]!!.getMinDistancePoint(true, list = points).id!!
                path.add(points[nextStepIndex]!!)
                currentIndex = nextStepIndex
            } while (points[currentIndex]!!.simplestPathLength != 0)
        return path
    }

}