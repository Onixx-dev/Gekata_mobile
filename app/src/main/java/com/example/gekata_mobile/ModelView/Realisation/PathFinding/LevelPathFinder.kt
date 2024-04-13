package com.example.gekata_mobile.ModelView.Realisation.PathFinding

import android.graphics.Point
import android.util.Log
import com.example.gekata_mobile.Models.Basic.Level
import com.example.gekata_mobile.Models.Basic.LineWall
import com.example.gekata_mobile.Models.Interfaces.IDataPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.util.ArrayDeque
import kotlin.concurrent.fixedRateTimer
import kotlin.math.pow
import kotlin.math.sqrt

//import kotlin.collections.ArrayDeque

class LevelPathFinder {

    val maxChildSize = 2
    val separator: Double = 10.0

    fun run(
        level: Level,
        startx: Int,
        starty: Int,
        endx: Int,
        endy: Int,
    ) {
        val radius = normalise(level).toInt()
        var counter = 0
        do {
            buildWay(level, level.startPoint.getX(), level.startPoint.getY(), level.endpoint.getX(), level.endpoint.getY(), radius)
            counter++
        }while ( !level.isFinalPathInitialised())

            if (level.isFinalPathInitialised()) {
                optimiseWayAsArray(level)
//                Log.d("tests", "success")
            }

    }

    private fun normalise(level: Level): Double {
        if (!level.isNormalize) {
            var minX: Int = Int.MAX_VALUE
            var minY: Int = Int.MAX_VALUE
            var maxX: Int = Int.MIN_VALUE
            var maxY: Int = Int.MIN_VALUE

            for (wall in level.walls) {
                if (wall.startX!! < minX)
                    minX = wall.startX!!
                if (wall.endX!! < minX)
                    minX = wall.endX!!
                if (wall.startY!! < minY)
                    minY = wall.startY!!
                if (wall.endY!! < minY)
                    minY = wall.endY!!

                if (wall.startX!! > maxX)
                    maxX = wall.startX!!
                if (wall.endX!! > maxX)
                    maxX = wall.endX!!
                if (wall.startY!! > maxY)
                    maxY = wall.startY!!
                if (wall.endY!! > maxY)
                    maxY = wall.endY!!
            }

            for (item in level.walls) {
                item.startX = item.startX?.minus(minX)
                item.endX = item.endX?.minus(minX)
                item.startY = item.startY?.minus(minY)
                item.endY = item.endY?.minus(minY)
            }

            for (item in level.curve) {
                item.startX = item.startX?.minus(minX)
                item.endX = item.endX?.minus(minX)
                item.controlX = item.controlX?.minus(minX)
                item.startY = item.startY?.minus(minY)
                item.endY = item.endY?.minus(minY)
                item.controlY = item.controlY?.minus(minX)
            }

            for (item in level.wayPoints) {
                item.x = item.x?.minus(minX)
                item.y = item.y?.minus(minY)
            }

            for (item in level.interestPoints) {
                item.x = item.x?.minus(minX)
                item.y = item.y?.minus(minY)
            }

            level.maxX = maxX
            level.maxY = maxY

            if (!level.isTreeNodeInitialised())
                level.pathTreeRoot =
                    TreeNode(x = level.startPoint.getX(), y = level.startPoint.getY())

            level.isNormalize = true
//            Log.d("norm", "normalized")
        }
        return (level.maxX + level.maxY) / separator
    }

    private fun buildWay(
        level: Level,
        startx: Int,
        starty: Int,
        endx: Int,
        endy: Int,
        radius: Int
    ) {

        if (!level.isContainsPath) {

            var treeItem: TreeNode
            var distance: Double
            var minDistance: Double = -1.0
            val nearestItems: ArrayList<TreeNode> = arrayListOf()

            val newTreeItem = TreeNode(
                x = (0..level.maxX).random(),
                y = (0..level.maxY).random()
            )


            //определить ближайших соседей в R полным проходом по дереву

            val stack = ArrayDeque<TreeNode>()
            stack.push(level.pathTreeRoot)
            do {
                treeItem = stack.pop()
                distance = sqrt(
                    (newTreeItem.x - treeItem.x).toDouble()
                        .pow(2.0) + (newTreeItem.y - treeItem.y).toDouble().pow(2.0)
                )
                if (distance <= radius && treeItem.childs.size < maxChildSize) {
                    nearestItems.add(treeItem)
                }
                for (child in treeItem.childs)
                    stack.push(child)
            } while (!stack.isEmpty())


            // проверить ближайшие ноды на возможность соединения
            var parentToAdd: TreeNode? = null
            for (item in nearestItems) {
                for (wall in level.walls) {
                    if (isNoCollision(
                            wall,
                            newTreeItem.x.toDouble(),
                            newTreeItem.y.toDouble(),
                            item.x.toDouble(),
                            item.y.toDouble()
                        )
                    ) {
                        parentToAdd = item
                    } else {
                        parentToAdd = null
                        break
                    }
                }
            }


            // Если нашли ноду, к которой можно подключить новую, проверяем возможность соединить новую ноду с конечной точкой

            if (parentToAdd != null) {
                newTreeItem.parent = parentToAdd
                val distanceToEndPoint = sqrt(
                    (newTreeItem.x - endx).toDouble().pow(2.0) + (newTreeItem.y - endy).toDouble()
                        .pow(2.0)
                )
                if (distanceToEndPoint <= radius) {
                    for (wall in level.walls) {
                        if (isNoCollision(
                                wall,
                                newTreeItem.x.toDouble(),
                                newTreeItem.y.toDouble(),
                                endx.toDouble(),
                                endy.toDouble()
                            )
                        ) {
                            newTreeItem.isFindTarget = true
                        } else {
                            newTreeItem.isFindTarget = false
                            break
                        }
                    }

                    if (newTreeItem.isFindTarget) {
                        var parentNode = newTreeItem
                        var currentNode = TreeNode(endx, endy, parent = newTreeItem)
                        parentNode.childs.add(currentNode)

                        while (parentNode.parent != null) {
                            parentNode.wayBuild(currentNode)
                            currentNode = parentNode
                            parentNode = currentNode.parent!!
                        }
                        level.finalPath = parentNode
                        level.isContainsPath = true
                    }
                }
                parentToAdd.childs.add(newTreeItem)
            }


        }
    }

    private fun optimiseWay(level: Level, startNode: TreeNode): TreeNode {
        var wayLenght: Int = 0
        var isRelative: Boolean = true
        var lastNode = startNode

        while (lastNode.childs.isNotEmpty()) {
            lastNode = lastNode.childs.first()
            wayLenght++
        }

        for (i in 0..<wayLenght) {
            for (wall in level.walls) {
                if (isNoCollision(
                        wall,
                        startNode.x.toDouble(),
                        startNode.y.toDouble(),
                        lastNode.x.toDouble(),
                        lastNode.y.toDouble()
                    )
                ) {
                    isRelative = true
                } else {
                    isRelative = false
                    break
                }
            }

            if (isRelative) {
                lastNode.parent = startNode
                startNode.childs = arrayListOf(lastNode)
            } else {
                lastNode = lastNode.parent!!
            }
        }

        return lastNode
    }

    private fun optimiseWayAsArray(level: Level) {
        var isRelative: Boolean = true

        var points = arrayListOf<Point>()
        var lastNode = level.finalPath

        while (lastNode.childs.isNotEmpty()) {
            points.add(Point(lastNode.x, lastNode.y))
            lastNode = lastNode.childs.first()
        }
//        points.add(Point(level.endpoint.getX(), level.endpoint.getY()))

//        val smoothPoints = arrayListOf<Point>()
//        for (i in points.indices){
//            smoothPoints.add(points[i])
//            if(i < points.size-1) {
//                smoothPoints.add(Point((points[i].x + points[i + 1].x) / 3,(points[i].y + points[i + 1].y) / 3))
//                smoothPoints.add(Point((points[i].x + points[i + 1].x) / 3*2,(points[i].y + points[i + 1].y) / 3*2))
//            }
//        }
//        points = smoothPoints

        var currentPoint = points[0]
        val finalPath = arrayListOf(points[0])
        val size = points.size
        var i: Int = 0
        var point2 = points.last()

        while( i < points.size && currentPoint.x != point2.x && currentPoint.y != point2.y) {
            point2 = points[size-i-1]
            for (wall in level.walls) {
                if (isNoCollision(wall, currentPoint.x.toDouble(), currentPoint.y.toDouble(), point2.x.toDouble(), point2.y.toDouble())) {
                    isRelative = true
                } else {
                    isRelative = false
                    break
                }
            }

            if (isRelative) {
                finalPath.add(Point(points[size-i-1].x, points[size-i-1].y))
                currentPoint = points[size-i-1]
                i = 0
                point2 = points[size-i-1]
            }
            else{
                i++
            }
        }




        var root = TreeNode(finalPath[0].x, finalPath[0].y)
        var parentToAdd = root

//        Log.d("final points size", "${finalPath.size}")
        for (point in finalPath) {
//            Log.d("final points", "${point.x} ${point.y}")
            parentToAdd.childs.add(TreeNode(point.x, point.y))
            parentToAdd = parentToAdd.childs[0]
        }
        parentToAdd.childs.add(TreeNode(level.endpoint.getX(), level.endpoint.getY()))

        level.test = root

    }

    private fun isNoCollision(
        wall: LineWall,
        x1: Double,
        y1: Double,
        x2: Double,
        y2: Double
    ): Boolean {
        var denominator: Double
        var numerator_a: Double
        var numerator_b: Double
        var Ua: Double
        var Ub: Double

        if(x1 == x2 && y1 == y2)
            return false

        denominator =
            ((y1 - y2) * (wall.startX!! - wall.endX!!) - (x1 - x2) * (wall.startY!! - wall.endY!!))

        if (denominator == 0.0) {
            if (((wall.startX!! * wall.endY!! - wall.endX!! * wall.startY!!) * (y1 - x2) - (x2 * y1 - y1 * y2) * (wall.endX!! - wall.startX!!)).toDouble() == 0.0 && ((wall.startX!! * wall.endY!! - wall.endX!! * wall.startY!!) * (y1 - y2) - (x2 * y1 - y1 * y2) * (wall.endY!! - wall.startY!!)).toDouble() == 0.0) {
                return false
            } else {
                return true
            }
        } else {
            numerator_a = (x1 - wall.endX!!) * (y1 - y2) - (x1 - x2) * (y1 - wall.endY!!)
            numerator_b =
                (wall.startX!!.toDouble() - wall.endX!!) * (y1 - wall.endY!!) - (x1 - wall.endX!!) * (wall.startY!! - wall.endY!!)
            Ua = numerator_a / denominator
            Ub = numerator_b / denominator
            if (Ua in 0.0..1.0 && Ub >= 0 && Ub <= 1) {
                return false
            } else {
                return true
            }
        }
    }

}





