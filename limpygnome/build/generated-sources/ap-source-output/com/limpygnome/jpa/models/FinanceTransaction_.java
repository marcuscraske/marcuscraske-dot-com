package com.limpygnome.jpa.models;

import com.limpygnome.jpa.models.FinanceTransaction.TransactionType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FinanceTransaction.class)
public abstract class FinanceTransaction_ {

	public static volatile SingularAttribute<FinanceTransaction, Integer> amount;
	public static volatile SingularAttribute<FinanceTransaction, Integer> id;
	public static volatile SingularAttribute<FinanceTransaction, String> title;
	public static volatile SingularAttribute<FinanceTransaction, String> details;
	public static volatile SingularAttribute<FinanceTransaction, FinanceAccount> account;
	public static volatile SingularAttribute<FinanceTransaction, Long> epochDateTime;
	public static volatile SingularAttribute<FinanceTransaction, TransactionType> type;

}

