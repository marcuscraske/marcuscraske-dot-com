package com.limpygnome.controllers.finance;

import com.limpygnome.servlet.ExtendedHttpServlet;
import com.limpygnome.controllers.finance.misc.parsing.BaseStreamParser;
import com.limpygnome.controllers.finance.misc.parsing.CsvStreamParser;
import com.limpygnome.jpa.ConnectionFactory;
import com.limpygnome.jpa.providers.FinanceProvider;
import com.limpygnome.servlet.Auth;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author limpygnome
 */
@WebServlet(urlPatterns = {"/finance/upload"})
public class Upload extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Check the user is authorised
        if(!Auth.isValid(this))
        {
            return;
        }
        
        // Get instance of entity manager
        EntityManager em = ConnectionFactory.getInstance().createFinance();
        
        try
        {
            String success = null;
            String error = null;
            String info = null;

            // Check if files uploaded
            String requestContentType = request.getContentType();
            Collection<Part> parts;

            if(
                requestContentType != null && 
                requestContentType.toLowerCase().contains("multipart/form-data") &&
                !(parts = request.getParts()).isEmpty())
            {
                // Setup
                FinanceProvider provider = new FinanceProvider(em);
                provider.begin();

                StringBuilder rawInfo = new StringBuilder();

                try
                {
                    // Iterate each file and parse the data
                    BaseStreamParser parser = new CsvStreamParser();

                    int txs;
                    int totalTxs = 0;
                    String fileName;
                    String zipFileName;
                    String tempDir = request.getServletContext().getRealPath("/temp");
                    boolean tempDirExists = false;

                    for(Part p  : parts)
                    {
                        if(p.getSize() > 0)
                        {
                            fileName = p.getSubmittedFileName();

                            try
                            {
                                // Check if file is a zip we need to extract...
                                if(fileName.endsWith(".zip"))
                                {
                                    rawInfo .append("Processing zip upload '").append(fileName)
                                            .append("', [content type: '").append(p.getContentType())
                                            .append("', size: ").append(p.getSize()).append(" bytes")
                                            .append("]").append("\n");

                                    zipFileName = tempDir + "/" + System.currentTimeMillis() + "-" + (int)(Math.random()*1000)+".zip";

                                    // Save file to temp store
                                    try
                                    {
                                        // Check temp dir exists
                                        File fTempDir = new File(tempDir);
                                        if(!tempDirExists && (!fTempDir.exists() || !fTempDir.isDirectory()))
                                        {
                                            if(fTempDir.mkdir())
                                            {
                                                rawInfo.append("Created temp dir for zip archive.").append("\n");
                                            }
                                            else
                                            {
                                                rawInfo.append("Failed to create temp dir for zip archive.").append("\n");
                                            }
                                        }
                                        tempDirExists = true; // Avoid checking repeatedly during a request

                                        // Write file
                                        p.write(zipFileName);
                                    }
                                    catch(IOException ex)
                                    {
                                        throw new IOException("Failed to write zip archive to temporary storage.", ex);
                                    }

                                    // Read as zip
                                    try
                                    {
                                        ZipFile zf = new ZipFile(zipFileName);
                                        Enumeration<?> entries = zf.entries();

                                        ZipEntry entry;
                                        InputStream is;
                                        while(entries.hasMoreElements())
                                        {
                                            entry = (ZipEntry)entries.nextElement();

                                            rawInfo .append("- Processing file '").append(entry.getName())
                                                    .append("' [").append(entry.getSize()).append(" bytes]")
                                                    .append("\n");

                                            is = zf.getInputStream(entry);
                                            txs = parser.parse(provider, null, is);

                                            rawInfo.append("- ").append(txs).append(" TXs parsed").append("\n");
                                            totalTxs += txs;
                                        }
                                        zf.close();
                                    }
                                    catch(Exception ex)
                                    {
                                        throw new IOException("Failed to read zip archive.", ex);
                                    }
                                    finally
                                    {
                                        // Delete file
                                        try
                                        {
                                            File f = new File(zipFileName);
                                            if(f.exists())
                                            {
                                                f.delete();
                                                rawInfo.append("Removed zip archive from temp dir.").append("\n");
                                            }
                                            else
                                            {
                                                rawInfo.append("Failed to remove zip archive from temp dir.").append("\n");
                                            }
                                        }
                                        catch(Exception ex2)
                                        {
                                            Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to delete zip archive at '" + zipFileName + "'!", ex2);
                                            rawInfo.append("Exception when removing zip archive from temp dir.").append("\n");
                                        }
                                    }
                                }
                                else
                                {
                                    // Process part
                                    txs = parser.parse(provider, null, p);

                                    // Output results
                                    rawInfo .append("Processed file upload '").append(fileName)
                                            .append("', [content type: '").append(p.getContentType())
                                            .append("', size: ").append(p.getSize()).append(" bytes")
                                            .append("] - ").append(txs).append(" TXs parsed").append("\n");

                                    totalTxs += txs;
                                }
                            }
                            catch(Exception ex)
                            {
                                rawInfo .append("Failed to parse file '").append(p.getName()).append("' - ")
                                        .append(ex.getMessage())
                                        .append("\n");
                                error = ex.getMessage();

                                throw ex;
                            }
                        }
                    }

                    // Persist all the new TXs
                    provider.commit();
                    success = "Successfully parsed a total of " + totalTxs + " transactions.";
                }
                catch(Exception ex)
                {
                    provider.rollback();
                    Logger.getAnonymousLogger().log(Level.WARNING, "Failed to parse transaction data", ex);
                }
                
                // Set raw info
                if(rawInfo.length() > 0)
                {
                    info = rawInfo.toString();
                }
            }

            // Setup page
            templateSettings.setTemplatePageContent("finance/#upload");
            templateData.put("finance_info", info);
            templateData.put("finance_error", error);
            templateData.put("finance_success", success);
        }
        finally
        {
            em.close();
        }
    }
    
}
