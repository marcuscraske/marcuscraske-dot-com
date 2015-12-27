package com.limpygnome.legacy.jpa.models;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "finance_txs")
public class FinanceTransaction implements Serializable
{
    private static long serialVersionUID = 1L;
    
    public enum TransactionType
    {
        Unknown,
        Cashpoint,
        Purchase,
        Transfer,
        Charge,
        Interest,
        StandingOrder,
        Income,
        DirectDebit,
        Deposit
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer         id;
    
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private FinanceAccount  account;
    
    @Column(nullable = false)
    private long            epochDateTime;
    
    @Column(nullable = false)
    private TransactionType type;
    
    private String          title;
    
    private String          details;
    
    @Column(nullable = false)
    private int             amount;
    
    public FinanceTransaction(){}

    public FinanceTransaction(long dateTime, TransactionType type, String title, String details, int amount)
    {
        this.epochDateTime = dateTime;
        this.type = type;
        this.title = title;
        this.details = details;
        this.amount = amount;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public FinanceAccount getAccount()
    {
        return account;
    }

    public void setAccount(FinanceAccount account)
    {
        this.account = account;
    }

    public long getEpochDateTime()
    {
        return epochDateTime;
    }

    public void setEpochDateTime(long epochDateTime)
    {
        this.epochDateTime = epochDateTime;
    }

    public TransactionType getType()
    {
        return type;
    }

    public void setType(TransactionType type)
    {
        this.type = type;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }
}
