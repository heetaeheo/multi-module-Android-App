import com.example.news_data.model.ArticleDTO
import com.example.news_domain.model.Article

fun ArticleDTO.toDomainArticle(): Article {
    return Article(
        author,content, description, title, urlToImage
    )
}