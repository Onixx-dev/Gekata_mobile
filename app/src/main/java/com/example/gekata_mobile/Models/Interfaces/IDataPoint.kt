package com.example.gekata_mobile.Models.Interfaces

interface IDataPoint {

    fun getX(): Int

    fun getY(): Int

    fun _getID(): Int

    fun normalizeBy(changeAtX:Int, changeAtY:Int)

}