package com.example.gekata_mobile.ModelView.Realisation.PathFinding

data class TreeNode(
    val x: Int,
    val y: Int,
    var isFindTarget: Boolean = false,
    var parent: TreeNode? = null,
    var childs: ArrayList<TreeNode> = arrayListOf(),
    var deep: Int = 0
) {


    fun wayBuild(treeNode: TreeNode) {
        childs.clear()
        childs.add(treeNode)
    }

}