package com.limpygnome.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

/**
 *
 * @author limpygnome
 */
@Entity
@Subselect("SELECT * FROM finance_stats_overview")
@Synchronize({"finance_txs"})
public class FinanceStatsOverview implements Serializable
{
    private static long serialVersionUID = 1L;
    
    @Id
    private int id;
    private int totalOut;
    private int totalIn;
    
    public FinanceStatsOverview() {}
}
