package org.newsfeed.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DisplayNews(
    val id: Int,
    val displayTitle: String,
    val timeLabel: String
)

class NewsViewModel(private val repository: NewsRepository) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Semua")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _allNews = MutableStateFlow<List<News>>(emptyList())
    
    private val readNewsIds = mutableSetOf<Int>()

    init {
        viewModelScope.launch {
            repository.newsStream.collect { newNews ->
                _allNews.update { currentList ->
                    listOf(newNews) + currentList
                }
            }
        }
    }

    val displayNewsFeed: StateFlow<List<DisplayNews>> = combine(
        _allNews,
        _selectedCategory
    ) { newsList, category ->
        val filtered = if (category == "Semua") {
            newsList
        } else {
            newsList.filter { it.category == category }
        }

        filtered.map { news ->
            DisplayNews(
                id = news.id,
                displayTitle = "[${news.category.uppercase()}] ${news.title}",
                timeLabel = "Ditambahkan: ${news.timestamp}"
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun readNews(id: Int) {
        if (readNewsIds.contains(id)) return

        viewModelScope.launch {
            readNewsIds.add(id)
            
            val detail = repository.fetchNewsDetail(id)
            println("Berita Dibaca: $detail")

            _readCount.update { it + 1 }
        }
    }
}
