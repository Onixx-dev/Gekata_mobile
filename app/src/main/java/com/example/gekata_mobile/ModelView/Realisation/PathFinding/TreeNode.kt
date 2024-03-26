package com.example.gekata_mobile.ModelView.Realisation.PathFinding

data class TreeNode(
    val x: Int,
    val y: Int,
    val parent: TreeNode? = null,
    val childs: ArrayList<TreeNode> = arrayListOf()
) {


}