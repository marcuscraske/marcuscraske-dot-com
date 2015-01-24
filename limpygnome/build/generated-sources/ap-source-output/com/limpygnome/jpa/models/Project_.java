package com.limpygnome.jpa.models;

import com.limpygnome.jpa.models.Project.Status;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Project.class)
public abstract class Project_ {

	public static volatile SingularAttribute<Project, Integer> id;
	public static volatile SingularAttribute<Project, String> title;
	public static volatile SingularAttribute<Project, Status> status;
	public static volatile SingularAttribute<Project, String> description;
	public static volatile SingularAttribute<Project, String> thumbnailUrl;
	public static volatile SingularAttribute<Project, String> urlPart;

}

