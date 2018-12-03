package com.l.test.springboot.model;

import com.l.test.springboot.enums.CardStatus;

public class CardInfo {

    private int id;
    private String name;
    private String rules;
    private String productorId;
    private CardStatus cardStatus;
    private String backgroundColor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getProductorId() {
        return productorId;
    }

    public void setProductorId(String productorId) {
        this.productorId = productorId;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rules='" + rules + '\'' +
                ", productorId='" + productorId + '\'' +
                ", cardStatus=" + cardStatus +
                ", backgroundColor='" + backgroundColor + '\'' +
                '}';
    }
}
