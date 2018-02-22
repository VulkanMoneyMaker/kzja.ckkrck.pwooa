package gak.hafawq.nva.ui;


public interface BaseLogicView {

    void createdGame(long gameId);
    long getGameId();

    void dataUpdates(boolean success);
    void needReload(boolean isNeed);
}
