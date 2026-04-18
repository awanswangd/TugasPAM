package org.newsreader.project

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import org.newsreader.project.data.ArticleRepository
import org.newsreader.project.data.ArticleRepositoryImplementation
import org.newsreader.project.network.HttpClientFactory
import org.newsreader.project.ui.detail.ArticleDetailScreen
import org.newsreader.project.ui.detail.ArticleDetailViewModel
import org.newsreader.project.ui.list.ArticleListScreen
import org.newsreader.project.ui.list.ArticleListViewModel

private val httpClient = HttpClientFactory.create()
private val repository: ArticleRepository = ArticleRepositoryImplementation(httpClient)

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "articles"
    ) {
        composable("articles") {
            val viewModel: ArticleListViewModel = viewModel {
                ArticleListViewModel(repository)
            }
            ArticleListScreen(
                viewModel = viewModel,
                onArticleClick = { id -> navController.navigate("articles/$id") }
            )
        }

        composable(
            route = "articles/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val viewModel: ArticleDetailViewModel = viewModel {
                ArticleDetailViewModel(repository)
            }
            ArticleDetailScreen(
                articleId = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
