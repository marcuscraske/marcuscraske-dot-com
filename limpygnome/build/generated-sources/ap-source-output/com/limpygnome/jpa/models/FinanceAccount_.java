package com.limpygnome.jpa.models;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FinanceAccount.class)
public abstract class FinanceAccount_ {

	public static volatile SingularAttribute<FinanceAccount, String> accountIdentifier;
	public static volatile SingularAttribute<FinanceAccount, Integer> id;
	public static volatile SingularAttribute<FinanceAccount, String> alias;

}

