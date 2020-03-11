package domain.card;

import domain.CardPossessor;
import domain.CardProvider;
import domain.ScoreCalculator;

import java.util.ArrayList;
import java.util.List;

public class HandCards implements CardPossessor {
    private final List<Card> cards;

    public HandCards() {
        this.cards = new ArrayList<>();
    }

    public HandCards(List<Card> cards) {
        this.cards = cards;
    }

    public int calculateDefaultSum() {
        return cards.stream()
                .mapToInt(Card::extractScore)
                .sum();
    }

    @Override
    public void drawCard(CardProvider cardProvider) {
        cards.add(cardProvider.giveCard());
    }

    @Override
    public int calculateScore() {
        return ScoreCalculator.calculate(this);
    }

    @Override
    public int getCardAmount() {
        return cards.size();
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean hasAce() {
        return cards.stream()
                .anyMatch(Card::isAce);
    }
}
