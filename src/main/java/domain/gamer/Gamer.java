package domain.gamer;

import domain.card.possessable.CardPossessable;
import domain.card.providable.CardProvidable;
import domain.result.WinLose;

import java.util.Objects;

public abstract class Gamer implements BlackJackGameable {
    protected final String name;
    protected final CardPossessable cardPossessable;

    public Gamer(String name, CardPossessable cards) {
        this.name = name;
        this.cardPossessable = cards;
    }

    @Override
    public WinLose determineWinLose(BlackJackGameable counterParts) {
        if (this.calculateScore() > counterParts.calculateScore()) {
            return WinLose.WIN;
        }

        return WinLose.LOSE;
    }

    @Override
    public void drawCard(CardProvidable cardProvidable) {
        cardPossessable.drawCard(cardProvidable);
    }

    @Override
    public int calculateScore() {
        return cardPossessable.calculateScore();
    }

    @Override
    public abstract boolean canDrawMore();

    public CardPossessable getCardsOnHand() {
        return cardPossessable;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gamer that = (Gamer) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}