package org.notes.project.domain.model

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val createdAt: Long
)
