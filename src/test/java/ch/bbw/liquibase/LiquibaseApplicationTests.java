package ch.bbw.liquibase;

import ch.bbw.liquibase.domain.article.*;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "spring.liquibase.drop-first=true")
@ActiveProfiles("test")
@Transactional
class LiquibaseApplicationTests implements WithAssertions {

    @Autowired
    private ArticleRepository repository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void insertToDb() {
        var category = Category.builder().name("Test").name("huan").build();
        category = categoryRepository.save(category);

        var article = Article.builder().title("Test").category(category.getId()).build();
        article = repository.save(article);

        var comment = Comment.builder().article(article).build();
        System.out.println(comment.toString());
        comment = commentRepository.save(comment);

        assertThat(article).isNotNull();
        assertThat(comment.getId()).isNotNull();
        assertThat(comment.getArticle().getId()).isEqualTo(article.getId());
    }

}
