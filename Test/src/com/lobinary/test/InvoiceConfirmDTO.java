package com.lobinary.test;

import java.math.BigDecimal;
import java.util.Date;



/**
 * Created by yp-bp-m-7038 on 16/9/30.
 */
public class InvoiceConfirmDTO  {

    private Long id;
    private Long profitRecordId;
    private String invoiceNo;
    private String billType;
    private String invoiceType;
    private Date invoiceDate;
    private BigDecimal invoiceTotalAmount;
    private BigDecimal profitAmountTax;
    private String bussinessEntity;
    private String payType;
    private BigDecimal invoiceAmountTax;
    private String currency;
    private String desc;
    private BigDecimal lineAmount;
    private BigDecimal validationColumn;
    private Date glDate;
    private Date assessmentDate;
    private String num;
    private String name;
    private BigDecimal profitTotalAmount;
    private String saleName;
    private String productionLine;
    private String bizType;
    private String invoiceTypeName;
    private String bizTypeName;
    private String profitYear;
    private String profitMonth;
    private Date lastUpdateTime;
    private String lastUpdateTimeStr;
    private String billConfirm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfitRecordId() {
        return profitRecordId;
    }

    public void setProfitRecordId(Long profitRecordId) {
        this.profitRecordId = profitRecordId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getInvoiceTotalAmount() {
        return invoiceTotalAmount;
    }

    public void setInvoiceTotalAmount(BigDecimal invoiceTotalAmount) {
        this.invoiceTotalAmount = invoiceTotalAmount;
    }

    public BigDecimal getProfitAmountTax() {
        return profitAmountTax;
    }

    public void setProfitAmountTax(BigDecimal profitAmountTax) {
        this.profitAmountTax = profitAmountTax;
    }

    public String getBussinessEntity() {
        return bussinessEntity;
    }

    public void setBussinessEntity(String bussinessEntity) {
        this.bussinessEntity = bussinessEntity;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getInvoiceAmountTax() {
        return invoiceAmountTax;
    }

    public void setInvoiceAmountTax(BigDecimal invoiceAmountTax) {
        this.invoiceAmountTax = invoiceAmountTax;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getLineAmount() {
        return lineAmount;
    }

    public void setLineAmount(BigDecimal lineAmount) {
        this.lineAmount = lineAmount;
    }

    public BigDecimal getValidationColumn() {
        return validationColumn;
    }

    public void setValidationColumn(BigDecimal validationColumn) {
        this.validationColumn = validationColumn;
    }

    public Date getGlDate() {
        return glDate;
    }

    public void setGlDate(Date glDate) {
        this.glDate = glDate;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getProfitTotalAmount() {
        return profitTotalAmount;
    }

    public void setProfitTotalAmount(BigDecimal profitTotalAmount) {
        this.profitTotalAmount = profitTotalAmount;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getProductionLine() {
        return productionLine;
    }

    public void setProductionLine(String productionLine) {
        this.productionLine = productionLine;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getInvoiceTypeName() {
        return invoiceTypeName;
    }

    public void setInvoiceTypeName(String invoiceTypeName) {
        this.invoiceTypeName = invoiceTypeName;
    }

    public String getBizTypeName() {
        return bizTypeName;
    }

    public void setBizTypeName(String bizTypeName) {
        this.bizTypeName = bizTypeName;
    }

    public String getProfitYear() {
        return profitYear;
    }

    public void setProfitYear(String profitYear) {
        this.profitYear = profitYear;
    }

    public String getProfitMonth() {
        return profitMonth;
    }

    public void setProfitMonth(String profitMonth) {
        this.profitMonth = profitMonth;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateTimeStr() {
        return lastUpdateTimeStr;
    }

    public void setLastUpdateTimeStr(String lastUpdateTimeStr) {
        this.lastUpdateTimeStr = lastUpdateTimeStr;
    }

    public String getBillConfirm() {
        return billConfirm;
    }

    public void setBillConfirm(String billConfirm) {
        this.billConfirm = billConfirm;
    }
}
