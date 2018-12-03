package com.l.test.springboot.model;

import java.util.Date;

/**
 * 卡片记录
 */
public class CardRecord {
    private int id;

    private int owner;

    private int cardId;

    private String cardName;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Date useTime;

    private boolean used;
    /**
     * 赠送原因
     */
    private String sendReason;
    /**
     * 使用原因
     */
    private String useReason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getSendReason() {
        return sendReason;
    }

    public void setSendReason(String sendReason) {
        this.sendReason = sendReason;
    }

    public String getUseReason() {
        return useReason;
    }

    public void setUseReason(String useReason) {
        this.useReason = useReason;
    }

    @Override
    public String toString() {
        return "CardRecord{" +
                "id=" + id +
                ", owner=" + owner +
                ", cardId=" + cardId +
                ", cardName='" + cardName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", useTime=" + useTime +
                ", used=" + used +
                ", sendReason='" + sendReason + '\'' +
                ", useReason='" + useReason + '\'' +
                '}';
    }
}
