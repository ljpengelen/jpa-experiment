package nl.cofx.jpa.experiment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import(TestcontainersConfiguration.class)
class BasketRepositoryTest {

    private static final UUID BASKET_ID = UUID.randomUUID();
    private static final UUID CRIPPS_PINK_ID = UUID.randomUUID();
    private static final UUID CRIPPS_RED_ID = UUID.randomUUID();

    @Autowired
    AppleRepository appleRepository;

    @Autowired
    BasketRepository basketRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void savesBasket() {
        var crippsPink = Apple.builder()
                .id(CRIPPS_PINK_ID)
                .cultivar("Cripps Pink")
                .build();
        var crippsRed = Apple.builder()
                .id(CRIPPS_RED_ID)
                .cultivar("Cripps Red")
                .build();
        var basket = Basket.builder()
                .id(BASKET_ID)
                .apples(List.of(crippsPink, crippsRed))
                .build();

        var persistedBasket = basketRepository.save(basket);

        assertThat(persistedBasket)
                .usingRecursiveComparison()
                .isEqualTo(basket);
    }

    @Test
    void deletesApples_whenRemovedFromBasket() {
        var crippsPink = Apple.builder()
                .id(CRIPPS_PINK_ID)
                .cultivar("Cripps Pink")
                .build();
        var crippsRed = Apple.builder()
                .id(CRIPPS_RED_ID)
                .cultivar("Cripps Red")
                .build();
        var basket = Basket.builder()
                .id(BASKET_ID)
                .build()
                .addApple(crippsPink)
                .addApple(crippsRed);
        basketRepository.save(basket);

        var persistedCrippsPink = appleRepository.findById(CRIPPS_PINK_ID);
        assertThat(persistedCrippsPink).contains(crippsPink);
        var persistedCrippsRed = appleRepository.findById(CRIPPS_RED_ID);
        assertThat(persistedCrippsRed).contains(crippsRed);

        basket.removeApple(persistedCrippsPink.get())
                .removeApple(persistedCrippsRed.get());
        basketRepository.save(basket);

        System.out.println(appleRepository.findAll());
        persistedCrippsPink = appleRepository.findById(CRIPPS_PINK_ID);
        System.out.println(persistedCrippsPink);
        persistedCrippsRed = appleRepository.findById(CRIPPS_RED_ID);
        System.out.println(persistedCrippsRed);
        assertThat(appleRepository.findAll()).isEmpty();
    }

    @Test
    void deletesApples_whenBasketIsDeleted() {
        var crippsPink = Apple.builder()
                .id(CRIPPS_PINK_ID)
                .cultivar("Cripps Pink")
                .build();
        var crippsRed = Apple.builder()
                .id(CRIPPS_RED_ID)
                .cultivar("Cripps Red")
                .build();
        var basket = Basket.builder()
                .id(BASKET_ID)
                .apples(List.of(crippsPink, crippsRed))
                .build();
        basketRepository.save(basket);

        var persistedCrippsPink = appleRepository.findById(CRIPPS_PINK_ID);
        assertThat(persistedCrippsPink).contains(crippsPink);
        var persistedCrippsRed = appleRepository.findById(CRIPPS_RED_ID);
        assertThat(persistedCrippsRed).contains(crippsRed);

        basketRepository.delete(basket);

        assertThat(appleRepository.findAll()).isEmpty();
    }
}
