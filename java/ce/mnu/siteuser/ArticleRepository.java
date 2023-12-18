package ce.mnu.siteuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT num, title, author ,written_date as writtenDate, photo, views, price, categories, locations, processes FROM article, article_categories, article_locations, article_processes WHERE article.num=article_categories.article_num and article.num=article_locations.article_num and article.num=article_processes.article_num", nativeQuery=true)
    Page<ArticleHeader> findArticleHeaders(Pageable pageable);
}


 