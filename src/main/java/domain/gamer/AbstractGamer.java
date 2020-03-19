package domain.gamer;

import domain.card.Card;
import domain.card.providable.CardProvidable;
import domain.gamer.action.TurnActions;
import domain.result.score.Score;
import domain.result.score.ScoreCalculable;

import java.util.List;
import java.util.Objects;

public abstract class AbstractGamer implements Gamer {
    private static final int INITIAL_CARDS_AMOUNT = 2;

    protected final Hand hand;
    private final Name name;

    AbstractGamer(Name name, Hand hand) {
        this.name = name;
        this.hand = hand;
    }

    @Override
    public void drawInitialCards(CardProvidable cardProvidable) {
        validateEmptyHand();

        for (int i = 0; i < INITIAL_CARDS_AMOUNT; i++) {
            hand.drawCard(cardProvidable);
        }
    }

    private void validateEmptyHand() {
        if (!hand.isEmpty()) {
            throw new IllegalStateException("손패가 비어있지 않습니다.");
        }
    }

    @Override
    public void playTurn(CardProvidable cardProvidable, ScoreCalculable scoreCalculable, TurnActions turnActions) {
        while (turnActions.isHit(this)) {
            hand.drawCard(cardProvidable);
            if (!scoreCalculable.checkCanDrawMore(this)) {
                break;
            }
            turnActions.showHand(this);
        }

        turnActions.showHand(this);
    }

    @Override
    public List<Card> openAllCards() {
        return hand.getCards();
    }

    @Override
    public Score calculateScore(ScoreCalculable scoreCalculable) {
        return scoreCalculable.calculateScore(this);
    }

    public Name getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractGamer that = (AbstractGamer) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
