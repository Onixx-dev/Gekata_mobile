package com.example.gekata_mobile.ModelView.Realisation.PathFinding

import android.util.Log
import com.example.gekata_mobile.Models.Basic.InterestPoint
import com.example.gekata_mobile.Models.Basic.Level
import com.example.gekata_mobile.Models.Basic.WayPoint

data class PathContainer(
    var startBuildingPoints: ArrayList<WayPoint> = arrayListOf(),
    var endBuildingPoints: ArrayList<WayPoint> = arrayListOf(),
    var start: InterestPoint,
    var end: InterestPoint
) {

    private lateinit var levels: ArrayList<Level>

    init {
        levels = wayPointsToLevels()
        val levelPathFinder = LevelPathFinder()

        for (level in levels) {
//            Log.d("from init", "${level.startPoint}  ${level.endpoint}")

            levelPathFinder.run(level,
                startx = level.startPoint.getX(),
                starty = level.startPoint.getY(),
                endx = level.endpoint.getX(),
                endy = level.endpoint.getY()
            )
        }
    }


    fun wayPointsToLevels(): ArrayList<Level> {
        val levels = arrayListOf<Level>()
        val collection: ArrayList<WayPoint> = arrayListOf()

        collection.addAll(startBuildingPoints)
        collection.addAll(endBuildingPoints)

        for (i in collection.indices) {
            if (i == 0) {
                collection[i].hostLevelObject.startPoint = start
                collection[i].hostLevelObject.endpoint = collection[i]
                levels.add(collection[i].hostLevelObject)
                continue
            }
            if (i == collection.size - 1) {
                collection[i].hostLevelObject.startPoint = collection[i]
                collection[i].hostLevelObject.endpoint = end
                levels.add(collection[i].hostLevelObject)
                continue
            }
            if (collection[i].hostLevelObject.id == collection[i - 1].hostLevelObject.id) {
//                Log.d("level", " = ${collection[i].hostLevelObject}")
                collection[i].hostLevelObject.startPoint = collection[i - 1]
                collection[i].hostLevelObject.endpoint = collection[i]
                levels.add(collection[i].hostLevelObject)
            }
        }

        for (i in levels.indices)
            if (i + 1 < levels.size)
                if (levels[i].startPoint._getID() == levels[i + 1].startPoint._getID())
                    if (levels[i].endpoint._getID() == levels[i + 1].endpoint._getID())
                        levels.removeAt(i)

        return levels
    }

    fun getItem(index: Int): Level {
        return levels[index]
    }

    fun getSize(): Int {
    return levels.size
    }

}
