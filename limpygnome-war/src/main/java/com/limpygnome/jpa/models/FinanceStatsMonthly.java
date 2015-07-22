package com.limpygnome.jpa.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Synchronize;

/**
 *
 * @author limpygnome
 */
@Entity
@Table(name = "finance_stats_monthly")
@Synchronize({"finance_txs"})
public class FinanceStatsMonthly implements Serializable
{
    private static long serialVersionUID = 1L;
    
    @Id
    private int id;
    private int year;
    private int month;
    private int totalTxs;
    private int totalTxsIn;
    private int totalTxsOut;
    private int totalIn;
    private int totalOut;
    private int balance;
    
    public FinanceStatsMonthly() {}

    public int getId()
    {
        return id;
    }

    public int getYear()
    {
        return year;
    }

    public int getMonth()
    {
        return month;
    }

    public int getTotalTxs()
    {
        return totalTxs;
    }

    public int getTotalTxsIn()
    {
        return totalTxsIn;
    }

    public int getTotalTxsOut()
    {
        return totalTxsOut;
    }

    public int getTotalIn()
    {
        return totalIn;
    }

    public int getTotalOut()
    {
        return totalOut;
    }
    
    public int getBalance()
    {
        return balance;
    }
}
