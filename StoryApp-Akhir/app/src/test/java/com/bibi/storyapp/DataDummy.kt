package com.bibi.storyapp

import com.bibi.storyapp.data.remote.response.ListStory

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStory> {
        val items: MutableList<ListStory> = arrayListOf()
        for (i in 0..100) {
            val story = ListStory(
                i.toString(),
                "name $i",
                "desc $i",
                "photo $i",
                "createdat $i",
                i.toDouble(),
                i.toDouble()
            )
            items.add(story)
        }
        return items
    }
}