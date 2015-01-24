package com.limpygnome.controllers.finance.misc.parsing;

/**
 *
 * @author limpygnome
 */
public class CsvColumnMappings
{
    public static final int UNKNOWN_MAPPING = -1;
    
    public enum MappingType
    {
        UNKNOWN,
        
        GENERIC_TX_DATE,
        GENERIC_TX_TYPE,
        GENERIC_SORT_CODE,
        GENERIC_ACCOUNT_NUMBER,
        GENERIC_DESCRIPTION,
        GENERIC_DEBIT_AMOUNT,
        GENERIC_CREDIT_AMOUNT,
        GENERIC_BALANCE
    }
    
    public MappingType[]    parts;
    
    public int expectedColumns;
    
    public CsvColumnMappings(int expectedParts)
    {
        // Set default mapping values to unknown
        parts = new MappingType[expectedParts];
        for(int i = 0; i < expectedParts; i++)
        {
            parts[i] = MappingType.UNKNOWN;
        }
        
        // Set other values
        expectedColumns = 0;
    }
}
