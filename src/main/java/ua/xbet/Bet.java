package ua.xbet;

public class Bet {
    private String carMark;
    private int amountOfMoney;

    public Bet(String carMark, int amountOfMoney) {
        this.carMark = carMark;
        this.amountOfMoney = amountOfMoney;
    }

    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }
}
