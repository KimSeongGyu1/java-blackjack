import domain.card.providable.CardDeck;
import domain.gamer.AllGamers;
import domain.gamer.Money;
import domain.gamer.Name;
import domain.gamer.Player;
import domain.gamer.action.TurnActions;
import domain.gamer.action.YesNo;
import domain.result.BlackJackRule;
import view.InputView;
import view.OutputView;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class BlackJackApplication {
    public static void main(String[] args) {
        BlackJackRule blackJackRule = new BlackJackRule();
        CardDeck cardDeck = new CardDeck();
        AllGamers allGamers = new AllGamers(enterInitialInformation(), blackJackRule);

        playInitialDrawPhase(allGamers, cardDeck);

        playAdditionalTurns(allGamers, cardDeck);

        showAllScores(allGamers, blackJackRule);

        showAllResults(allGamers);
    }

    private static List<Player> enterInitialInformation() {
        List<Name> playerNames = enterNames();
        OutputView.printEmptyLine();

        return betMoneys(playerNames);
    }

    private static List<Name> enterNames() {
        return InputView.inputPlayerNames()
                .stream()
                .map(Name::new)
                .collect(toList());
    }

    private static List<Player> betMoneys(List<Name> playerNames) {
        return playerNames.stream()
                .map(BlackJackApplication::enterBettingMoney)
                .collect(toList());
    }

    private static Player enterBettingMoney(Name name) {
        return new Player(name, new Money(InputView.inputBettingMoney(name.getValue())));
    }

    private static void playInitialDrawPhase(AllGamers allGamers, CardDeck cardDeck) {
        allGamers.drawInitialCards(cardDeck);

        OutputView.printEmptyLine();
        OutputView.printInitialCards(allGamers.getDealer(), allGamers.getPlayers());
    }

    private static void playAdditionalTurns(AllGamers allGamers, CardDeck cardDeck) {
        TurnActions playerTurnActions = new TurnActions(InputView::askDrawMore, OutputView::printGamerState);
        TurnActions dealerTurnActions = new TurnActions(dealer -> YesNo.YES, OutputView::printDealerCanDrawMore);

        allGamers.playPlayersTurn(cardDeck, playerTurnActions);
        allGamers.playDealerTurn(cardDeck, dealerTurnActions);

        OutputView.printEmptyLine();
    }

    private static void showAllScores(AllGamers allGamers, BlackJackRule blackJackRule) {
        allGamers.joinAllGamers()
                .forEach(gamer -> OutputView.printScore(gamer, gamer.calculateScore(blackJackRule)));

        OutputView.printEmptyLine();
    }

    private static void showAllResults(AllGamers allGamers) {
        OutputView.printResults(allGamers.gainAllResults());
    }
}
