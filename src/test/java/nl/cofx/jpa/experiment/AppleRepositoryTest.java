package nl.cofx.jpa.experiment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import(TestcontainersConfiguration.class)
class AppleRepositoryTest {

    private static final UUID CRIPPS_PINK_ID = UUID.randomUUID();

    @Autowired
    AppleRepository appleRepository;

    @Test
    void savesApple() {
        var crippsPink = Apple.builder()
                .id(CRIPPS_PINK_ID)
                .cultivar("Cripps Pink")
                .build();
        appleRepository.save(crippsPink);

        var persistedCrippsPink = appleRepository.findById(CRIPPS_PINK_ID);
        assertThat(persistedCrippsPink).contains(crippsPink);
    }

}
