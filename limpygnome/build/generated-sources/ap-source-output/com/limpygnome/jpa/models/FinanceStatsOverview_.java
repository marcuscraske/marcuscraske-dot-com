package com.limpygnome.jpa.models;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FinanceStatsOverview.class)
public abstract class FinanceStatsOverview_ {

	public static volatile SingularAttribute<FinanceStatsOverview, Integer> id;
	public static volatile SingularAttribute<FinanceStatsOverview, Integer> totalBalance;
	public static volatile SingularAttribute<FinanceStatsOverview, Integer> totalTxs;
	public static volatile SingularAttribute<FinanceStatsOverview, Integer> totalOut;
	public static volatile SingularAttribute<FinanceStatsOverview, Integer> totalIn;

}

