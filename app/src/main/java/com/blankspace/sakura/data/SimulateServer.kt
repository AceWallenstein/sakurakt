package com.blankspace.sakura.data

class SimulateServer {

    fun simulateData(pos: Int, pageSize: Int = 10): List<Int>? {
        val datas = mutableListOf<Int>()
        for (i in 1..100) {
            datas.add(i)
        }

        val total = datas.size / pageSize
        if (pos-1 > total) {
            return null
        }
        var startPos =  (pos-1) * pageSize
        var endPos = startPos + pageSize
        if (endPos > datas.size - 1) {
            endPos = datas.size - 1
        }
        return datas.slice(IntRange(startPos, endPos))
    }
}