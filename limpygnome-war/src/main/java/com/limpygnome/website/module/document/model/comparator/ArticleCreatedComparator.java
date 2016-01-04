package com.limpygnome.website.module.document.model.comparator;

import com.limpygnome.website.module.document.model.Document;
import java.util.Comparator;

public class ArticleCreatedComparator implements Comparator<Document>
{

    @Override
    public int compare(Document o1, Document o2)
    {
        return o1.getCreated().compareTo(o2.getCreated());
    }

}
