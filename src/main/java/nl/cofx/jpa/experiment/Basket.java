package nl.cofx.jpa.experiment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Basket {

    @Builder
    private Basket(UUID id) {
        this.id = id;
    }

    @Id
    private UUID id;

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "basket")
    @Setter(AccessLevel.PRIVATE)
    private List<Apple> apples = new ArrayList<>();

    public Basket addApple(Apple apple) {
        apples.add(apple);
        apple.setBasket(this);
        return this;
    }

    public Basket removeApple(Apple apple) {
        apples.remove(apple);
        apple.setBasket(null);
        return this;
    }
}
