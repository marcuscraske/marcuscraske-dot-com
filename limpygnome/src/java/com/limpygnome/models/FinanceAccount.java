package com.limpygnome.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author limpygnome
 */
@Entity
@Table(name = "finance_account")
public class FinanceAccount implements Serializable
{
    private static long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer         id;
    
    @Column(nullable = false)
    private String          alias;
    
    @Column(unique = true, nullable = false)
    private String          accountIdentifier;
    
    public FinanceAccount() {}
    
    public FinanceAccount(String accountIdentifier)
    {
        this.alias = "Default Alias";
        this.accountIdentifier = accountIdentifier;
    }
    
    public FinanceAccount(String sortCode, String accountNumber)
    {
        this(
                convertUkToAccountIdentifier(sortCode, accountNumber)
        );
    }
    
    public static String convertUkToAccountIdentifier(String sortCode, String accountNumber)
    {
        return sortCode + " " + accountNumber;
    }
}
