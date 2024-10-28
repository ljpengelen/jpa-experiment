package nl.cofx.jpa.experiment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
public class Basket {

    @Id
    private UUID id;

    @Builder.Default
    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "basket")
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
