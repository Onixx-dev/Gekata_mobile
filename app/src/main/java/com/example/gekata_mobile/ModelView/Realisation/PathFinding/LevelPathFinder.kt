package com.example.gekata_mobile.ModelView.Realisation.PathFinding

import android.util.Log
import com.example.gekata_mobile.Models.Basic.Level
import java.util.ArrayDeque
import kotlin.math.pow
import kotlin.math.sqrt

//import kotlin.collections.ArrayDeque

class LevelPathFinder {

    fun run(
        level: Level,
        startx: Int,
        starty: Int,
        endx: Int,
        endy: Int,
    ) {
        normalise(level)
        for (i in 0..500)
            buildWay(level, startx, starty, endx, endy, 100)
    }

    private fun normalise(level: Level) {
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
            level.isNormalize = true
            Log.d("norm", "normalized")
        }
    }

    private fun buildWay(
        level: Level,
        startx: Int,
        starty: Int,
        endx: Int,
        endy: Int,
        radius: Int
    ) {

        var treeItem: TreeNode
        var distance: Double
        var minDistance: Double = -1.0
        val nearestItems: ArrayList<TreeNode> = arrayListOf()

        val newTreeItem = TreeNode(
//            x = (0..level.maxX).random(),
//            y = (0..level.maxY).random()
            x = (0..level.maxX).random(),
            y = (0..level.maxY).random()
        )


        //TODO определить ближайших соседей в R полным проходом по дереву

        val stack = ArrayDeque<TreeNode>()
        stack.push(level.pathTreeRoot)
        do {
            treeItem = stack.pop()
            distance = sqrt((newTreeItem.x - treeItem.x).toDouble().pow(2.0) + (newTreeItem.y - treeItem.y).toDouble().pow(2.0)            )
            if (distance <= radius) {
                nearestItems.add(treeItem)
            }
            for (child in treeItem.childs)
                stack.push(child)
        } while (!stack.isEmpty())

        //TODO проверить ближайшие ноды на возможность соединения

        var denominator: Double
        var numerator_a: Double
        var numerator_b: Double
        var Ua: Double
        var Ub: Double
        var parentToAdd: TreeNode? = null

        for (item in nearestItems) {
            for (wall in level.walls) {

                denominator = ((newTreeItem.y.toDouble() - item.y) * (wall.startX!! - wall.endX!!) - (newTreeItem.x.toDouble() - item.x) * (wall.startY!! - wall.endY!!)).toDouble()

                if (denominator == 0.0) {
                    if (((wall.startX!! * wall.endY!! - wall.endX!! * wall.startY!!) * (newTreeItem.x - item.x) - (item.x * newTreeItem.y - newTreeItem.x * item.y) * (wall.endX!! - wall.startX!!)).toDouble() == 0.0 && ((wall.startX!! * wall.endY!! - wall.endX!! * wall.startY!!) * (newTreeItem.y - item.y) - (item.x * newTreeItem.y - newTreeItem.x * item.y) * (wall.endY!! - wall.startY!!)).toDouble() == 0.0) {
                        parentToAdd = null
                        break
                    }
                    else{
                        parentToAdd = item
                    }
                } else {
                    numerator_a = (newTreeItem.x.toDouble() - wall.endX!!) * (newTreeItem.y - item.y) - (newTreeItem.x.toDouble() - item.x) * (newTreeItem.y - wall.endY!!)
                    numerator_b = (wall.startX!!.toDouble() - wall.endX!!) * (newTreeItem.y - wall.endY!!) - (newTreeItem.x.toDouble() - wall.endX!!) * (wall.startY!! - wall.endY!!)
                    Ua = numerator_a / denominator
                    Ub = numerator_b / denominator
                    if (Ua in 0.0..1.0 && Ub >= 0 && Ub <= 1) {
                        parentToAdd = null
                        break
                    }
                    else {
                        parentToAdd = item
                    }
                }
            }
        }

        if(parentToAdd != null) {
            parentToAdd.childs.add(newTreeItem)
        }
    }


}