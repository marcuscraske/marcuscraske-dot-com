package com.limpygnome.legacy.jpa.models;

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
//@Subselect("SELECT * FROM finance_stats_overview")
@Table(name = "finance_stats_overview")
@Synchronize({"finance_txs"})
public class FinanceStatsOverview implements Serializable
{
    private static long serialVersionUID = 1L;
    
    @Id
    private int id;
    private int totalTxs;
    private int totalOut;
    private int totalIn;
    private int totalBalance;
    
    public FinanceStatsOverview() {}

    public int getTotalTxs()
    {
        return totalTxs;
    }

    public int getTotalOut()
    {
        return totalOut;
    }

    public int getTotalIn()
    {
        return totalIn;
    }
    
    public int getTotalBalance()
    {
        return totalBalance;
    }
}
