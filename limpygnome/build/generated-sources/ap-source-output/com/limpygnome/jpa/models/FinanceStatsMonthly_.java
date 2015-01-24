package com.limpygnome.jpa.models;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FinanceStatsMonthly.class)
public abstract class FinanceStatsMonthly_ {

	public static volatile SingularAttribute<FinanceStatsMonthly, Integer> id;
	public static volatile SingularAttribute<FinanceStatsMonthly, Integer> balance;
	public static volatile SingularAttribute<FinanceStatsMonthly, Integer> totalTxsIn;
	public static volatile SingularAttribute<FinanceStatsMonthly, Integer> totalTxsOut;
	public static volatile SingularAttribute<FinanceStatsMonthly, Integer> month;
	public static volatile SingularAttribute<FinanceStatsMonthly, Integer> totalTxs;
	public static volatile SingularAttribute<FinanceStatsMonthly, Integer> year;
	public static volatile SingularAttribute<FinanceStatsMonthly, Integer> totalOut;
	public static volatile SingularAttribute<FinanceStatsMonthly, Integer> totalIn;

}

