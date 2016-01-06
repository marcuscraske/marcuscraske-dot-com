package com.limpygnome.website.module.document.model.comparator;

import com.limpygnome.website.module.document.model.Document;
import java.util.Comparator;

public class DocumentCreatedComparator implements Comparator<Document>
{

    @Override
    public int compare(Document o1, Document o2)
    {
        int value = o2.getCreated().compareTo(o1.getCreated());

        if (value == 0)
        {
            return o1.getTitle().compareTo(o2.getTitle());
        }
        else
        {
            return value;
        }
    }

}
