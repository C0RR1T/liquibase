package ch.bbw.liquibase;

import ch.bbw.liquibase.domain.article.*;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "spring.liquibase.drop-first=true")
@ActiveProfiles("test")
// Return to test data state in db
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        comment = commentRepository.save(comment);

        assertThat(article.getId()).isNotNull();
        assertThat(comment.getId()).isNotNull();
        assertThat(comment.getArticle().getId()).isEqualTo(article.getId());
    }

    @Test
    void verifyTestData() {
        var comments = commentRepository.findAll();
        var articles = repository.findAll();
        var categories = repository.findAll();

        // Check if database contains all the test data
        assertThat(comments).hasSize(3);
        assertThat(articles).hasSize(3);
        assertThat(categories).hasSize(2);
    }

}
