package com.limpygnome.legacy.controllers.finance.misc.parsing;

import com.limpygnome.legacy.jpa.models.FinanceAccount;
import com.limpygnome.legacy.jpa.providers.FinanceProvider;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.Part;

/**
 *
 * @author limpygnome
 */
public abstract class BaseStreamParser
{
    /**
     * 
     * @param provider
     * @param account Optional; only needed where the account cannot be
     * auto-detected.
     * @param fileUpload
     * @return
     * @throws IOException 
     */
    public int parse(FinanceProvider provider, FinanceAccount account, Part fileUpload) throws IOException
    {
        return parse(provider, account, fileUpload.getInputStream());
    }
    
    public abstract int parse(FinanceProvider provider, FinanceAccount account, InputStream streamData) throws IOException;
    
    /**
     * The name of the stream parser.
     * 
     * @return The name.
     */
    public abstract String getName();
}
