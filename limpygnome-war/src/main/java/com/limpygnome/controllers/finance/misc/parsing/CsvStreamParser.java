package com.limpygnome.controllers.finance.misc.parsing;

import com.limpygnome.jpa.models.FinanceAccount;
import com.limpygnome.jpa.models.FinanceTransaction;
import com.limpygnome.jpa.providers.FinanceProvider;
import com.limpygnome.controllers.finance.misc.parsing.BaseStreamParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.IIOException;

/**
 * A CSV parser for Lloyds, TSB and Halifax online banking sites.
 * 
 * @author limpygnome
 */
public class CsvStreamParser extends BaseStreamParser
{
    @Override
    public int parse(FinanceProvider provider, FinanceAccount account, InputStream streamData) throws IOException
    {
        // Begin parsing stream
        BufferedReader br = new BufferedReader(new InputStreamReader(streamData));
        String line;
        int counterParsed = 0;
        int counterLine = 0;
        boolean headerParsed = false;
        CsvColumnMappings mappings = null;
        FinanceTransaction tx;

        // Read line-by-line from stream until no data is available
        while((line = br.readLine()) != null)
        {
            // Trim to avoid tailing environment artifacts/chars
            line = line.trim();

            // Check if to parse header
            if(!headerParsed)
            {
                // First line - header/column-name data expected...
                mappings = parseLineHeader(line);
                headerParsed = true;
            }
            // Check the line is not empty
            else if(line.length() > 0)
            {
                try
                {
                    // Parse line as TX
                    tx = parseLineTx(provider, account, mappings, counterLine, line);

                    // Persist tx
                    provider.transactionCreate(tx);
                }
                catch(IOException ex)
                {
                    throw new IOException("Failed to parse line " + counterLine + " in CSV file.", ex);
                }
                counterParsed++;
            }

            counterLine++;
        }
        
        return counterParsed;
    }
    
    private CsvColumnMappings parseLineHeader(String line) throws IOException
    {
        String[] labels = line.split(",");
        CsvColumnMappings mappings = new CsvColumnMappings(labels.length);
        
        String label;
        for(int i = 0; i < labels.length; i++)
        {
            label = labels[i];
            
            // Parse column and set type for inex
            if(label.length() > 0)
            {
                switch(label)
                {
                    case "Transaction Date":
                        mappings.parts[i] = CsvColumnMappings.MappingType.GENERIC_TX_DATE;
                        break;
                    case "Transaction Type":
                        mappings.parts[i] = CsvColumnMappings.MappingType.GENERIC_TX_TYPE;
                        break;
                    case "Sort Code":
                        mappings.parts[i] = CsvColumnMappings.MappingType.GENERIC_SORT_CODE;
                        break;
                    case "Account Number":
                        mappings.parts[i] = CsvColumnMappings.MappingType.GENERIC_ACCOUNT_NUMBER;
                        break;
                    case "Transaction Description":
                        mappings.parts[i] = CsvColumnMappings.MappingType.GENERIC_DESCRIPTION;
                        break;
                    case "Debit Amount":
                        mappings.parts[i] = CsvColumnMappings.MappingType.GENERIC_DEBIT_AMOUNT;
                        break;
                    case "Credit Amount":
                        mappings.parts[i] = CsvColumnMappings.MappingType.GENERIC_CREDIT_AMOUNT;
                        break;
                    case "Balance":
                        mappings.parts[i] = CsvColumnMappings.MappingType.GENERIC_BALANCE;
                        break;
                    default:
                        throw new IOException("Unknown column/header '" + label + "', column " + i + " whilst parsing header.");
                }
                mappings.expectedColumns++;
            }
            else
            {
                throw new IOException("Empty column at index " + i + ", columns must have a name; whilst parsing header.");
            }
        }
        
        return mappings;
    }
    
    private FinanceTransaction parseLineTx(FinanceProvider provider, FinanceAccount account, CsvColumnMappings mappings, int lineNumber, String line) throws IOException
    {
        String[] parts = line.split(",");
        
        // Check we have the expected number of parts
        if(parts.length != mappings.expectedColumns)
        {
            throw new IIOException("Invalid line '" + line + "', expected " + mappings.expectedColumns + " parts.");
        }
        
        // Create new model and map parts
        FinanceTransaction tx = new FinanceTransaction();
        
        // Iterate and parse each value
        String accountNumber = null;
        String sortCode = null;
        
        String v;
        int amount;
        for(int i = 0; i < parts.length; i++)
        {
            v = parts[i].trim();
            
            // Handle base on expected mapping type
            try
            {
                switch(mappings.parts[i])
                {
                    case GENERIC_ACCOUNT_NUMBER:
                        accountNumber = v;
                        break;
                    case GENERIC_BALANCE:
                        // Not used
                        break;
                    case GENERIC_CREDIT_AMOUNT:
                        if((amount = parsePartAmount(v, false)) != 0)
                        {
                            tx.setAmount(amount);
                        }
                        break;
                    case GENERIC_DEBIT_AMOUNT:
                        if((amount = parsePartAmount(v, true)) != 0)
                        {
                            tx.setAmount(amount);
                        }
                        break;
                    case GENERIC_DESCRIPTION:
                        tx.setTitle(v);
                        break;
                    case GENERIC_SORT_CODE:
                        // Check it doesn't start with '
                        if(v.charAt(0) == '\'' && v.length() > 1)
                        {
                            sortCode = v.substring(1);
                        }
                        else
                        {
                            sortCode = v;
                        }
                        break;
                    case GENERIC_TX_DATE:
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = sdf.parse(v);
                        tx.setEpochDateTime(date.getTime());
                        break;
                    case GENERIC_TX_TYPE:
                        FinanceTransaction.TransactionType tt;
                        switch(v)
                        {
                            case "DEP":
                                tt = FinanceTransaction.TransactionType.Deposit;
                                break;
                            case "DD":
                                tt = FinanceTransaction.TransactionType.DirectDebit;
                                break;
                            case "CPT":
                                tt = FinanceTransaction.TransactionType.Cashpoint;
                                break;
                            case "CHG": // ?
                                tt = FinanceTransaction.TransactionType.Charge;
                                break;
                            case "CR": // ?
                            case "FPI":
                            case "BGC":
                                tt = FinanceTransaction.TransactionType.Income;
                                break;
                            case "INT": // ?
                                tt = FinanceTransaction.TransactionType.Interest;
                                break;
                            case "DEB":
                                tt = FinanceTransaction.TransactionType.Purchase;
                                break;
                            case "SO":
                                tt = FinanceTransaction.TransactionType.StandingOrder;
                                break;
                            case "FPO":
                            case "TFR":
                                tt = FinanceTransaction.TransactionType.Transfer;
                                break;
                            case "":
                                tt = FinanceTransaction.TransactionType.Unknown;
                                break;
                            default:
                                throw new IOException("Unknown transaction type '" + v + "', line " + lineNumber + ", column " + i);
                        }
                        tx.setType(tt);
                        break;
                    default:
                    case UNKNOWN:
                        throw new IIOException("Unhandled value '" + mappings.parts[i] + "'; line " + lineNumber + ", col " + i + ".");
                }
            }
            catch(Exception ex)
            {
                throw new IOException("Failed to parse line " + line + ", column " + i + " - " + ex.getMessage(), ex);
            }
        }
        
        // Check we have sort code / account number
        if(sortCode == null)
        {
            throw new IOException("Sort code not present, line " + lineNumber);
        }
        else if(accountNumber == null)
        {
            throw new IOException("Account number not present, line " + lineNumber);
        }
        
        // Fetch account if null
        if(account == null)
        {
            account = provider.accountFetch(sortCode, accountNumber);
            
            // Check if the account could be found, else create a new one...
            if(account == null)
            {
                // Create new account
                account = new FinanceAccount(sortCode, accountNumber);
                provider.accountCreate(account);
            }
        }
        
        // Set account
        tx.setAccount(account);
        
        return tx;
    }
    
    private int parsePartAmount(String amount, boolean negative)
    {
        int len = amount.length();
        
        if(len > 0)
        {
            // Remove decimal symbol, if present
            if(amount.charAt(len-3) == '.')
            {
                StringBuilder sb = new StringBuilder(amount);
                sb.deleteCharAt(len-3);
                amount = sb.toString();
            }
            
            int result = Integer.parseInt(amount);
            if(negative)
            {
                result *= -1;
            }
            return result;
        }
        return 0;
    }

    @Override
    public String getName()
    {
        return "CSV File (Lloyds, TSB, Halifax)";
    }
}
