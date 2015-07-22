package com.limpygnome.jpa.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a project.
 * 
 * @author limpygnome
 */
@Entity
@Table(name = "projects")
public class Project implements Serializable
{
    // Enums
    // *************************************************************************
    public enum Status
    {
        ACTIVE("Active"),
        MAINTAINED("Maintained"),
        EXPERIMENT("Experiment"),
        INACTIVE("Inactive")
        ;
        
        private final String statusText;
        private Status(String statusText)
        {
            this.statusText = statusText;
        }
        
        public String getText()
        {
            return statusText;
        }
    }
    
    // Constants
    // *************************************************************************
    private static long serialVersionUID = 1L;
    
    // Fields
    // *************************************************************************
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int     id;
    
    @Column(nullable = false)
    private String  title;
    
    @Column(nullable = true)
    private String  description;
    
    @Column(nullable = false, unique = true)
    private String  urlPart;
    
    @Column(nullable = false)
    private Status  status;
    
    @Column(nullable = false)
    private String  thumbnailUrl;
    
    // Constructors
    // *************************************************************************
    public Project() {}
    
    public Project(String title, String description, String urlPart, Status status, String thumbnail)
    {
        this.title = title;
        this.description = description;
        this.urlPart = urlPart;
        this.status = status;
        this.thumbnailUrl = thumbnail;
    }
    
    // Methods - Mutators
    // *************************************************************************
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public void setUrlPart(String urlPart)
    {
        this.urlPart = urlPart;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }
    
    public void setThumbnailUrl(String thumbnailUrl)
    {
        this.thumbnailUrl = thumbnailUrl;
    }
    
    // Methods - Accessors
    // *************************************************************************

    public int getId()
    {
        return id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public String getUrlPart()
    {
        return urlPart;
    }
    
    public Status getStatus()
    {
        return status;
    }
    
    public String getStatusText()
    {
        return status.statusText;
    }

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }
    
}
