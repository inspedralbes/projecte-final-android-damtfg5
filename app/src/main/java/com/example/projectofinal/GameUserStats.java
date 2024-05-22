package com.example.projectofinal;

public class GameUserStats {
    private int gameId;
    private int playerId;
    private int spikePoints;
    private int spikeErrors;
    private int spikeAttempts;
    private int blockPoints;
    private int blockErrors;
    private int blockAttempts;
    private int servePoints;
    private int serveErrors;
    private int serveAttempts;
    private int setSuccessful;
    private int setErrors;
    private int setAttempts;
    private int receiveSuccessful;
    private int receiveErrors;
    private int receiveAttempts;

    public GameUserStats(int gameId, int playerId, int spikePoints, int spikeErrors, int spikeAttempts, int blockPoints, int blockErrors, int blockAttempts, int servePoints, int serveErrors, int serveAttempts, int setSuccessful, int setErrors, int setAttempts, int receiveSuccessful, int receiveErrors, int receiveAttempts) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.spikePoints = spikePoints;
        this.spikeErrors = spikeErrors;
        this.spikeAttempts = spikeAttempts;
        this.blockPoints = blockPoints;
        this.blockErrors = blockErrors;
        this.blockAttempts = blockAttempts;
        this.servePoints = servePoints;
        this.serveErrors = serveErrors;
        this.serveAttempts = serveAttempts;
        this.setSuccessful = setSuccessful;
        this.setErrors = setErrors;
        this.setAttempts = setAttempts;
        this.receiveSuccessful = receiveSuccessful;
        this.receiveErrors = receiveErrors;
        this.receiveAttempts = receiveAttempts;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getSpikePoints() {
        return spikePoints;
    }

    public void setSpikePoints(int spikePoints) {
        this.spikePoints = spikePoints;
    }

    public int getSpikeErrors() {
        return spikeErrors;
    }

    public void setSpikeErrors(int spikeErrors) {
        this.spikeErrors = spikeErrors;
    }

    public int getSpikeAttempts() {
        return spikeAttempts;
    }

    public void setSpikeAttempts(int spikeAttempts) {
        this.spikeAttempts = spikeAttempts;
    }

    public int getBlockPoints() {
        return blockPoints;
    }

    public void setBlockPoints(int blockPoints) {
        this.blockPoints = blockPoints;
    }

    public int getBlockErrors() {
        return blockErrors;
    }

    public void setBlockErrors(int blockErrors) {
        this.blockErrors = blockErrors;
    }

    public int getBlockAttempts() {
        return blockAttempts;
    }

    public void setBlockAttempts(int blockAttempts) {
        this.blockAttempts = blockAttempts;
    }

    public int getServePoints() {
        return servePoints;
    }

    public void setServePoints(int servePoints) {
        this.servePoints = servePoints;
    }

    public int getServeErrors() {
        return serveErrors;
    }

    public void setServeErrors(int serveErrors) {
        this.serveErrors = serveErrors;
    }

    public int getServeAttempts() {
        return serveAttempts;
    }

    public void setServeAttempts(int serveAttempts) {
        this.serveAttempts = serveAttempts;
    }

    public int getSetSuccessful() {
        return setSuccessful;
    }

    public void setSetSuccessful(int setSuccessful) {
        this.setSuccessful = setSuccessful;
    }

    public int getSetErrors() {
        return setErrors;
    }

    public void setSetErrors(int setErrors) {
        this.setErrors = setErrors;
    }

    public int getSetAttempts() {
        return setAttempts;
    }

    public void setSetAttempts(int setAttempts) {
        this.setAttempts = setAttempts;
    }

    public int getReceiveSuccessful() {
        return receiveSuccessful;
    }

    public void setReceiveSuccessful(int receiveSuccessful) {
        this.receiveSuccessful = receiveSuccessful;
    }

    public int getReceiveErrors() {
        return receiveErrors;
    }

    public void setReceiveErrors(int receiveErrors) {
        this.receiveErrors = receiveErrors;
    }

    public int getReceiveAttempts() {
        return receiveAttempts;
    }

    public void setReceiveAttempts(int receiveAttempts) {
        this.receiveAttempts = receiveAttempts;
    }
}
