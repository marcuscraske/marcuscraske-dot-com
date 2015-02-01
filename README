# limpygnome.com source code
## Introduction
This repository hosts the source code behind my website. At present, the code lacks tests and some documentation, since
the site has been recoded within a short period of time.

## Requirements
This project uses Maven for pulling dependencies and compilation. The schema for JPA entities is automatically
generated from the `compile` lifecycle phase, which is outputted to the target classes dir as `schema.sql`. The project
should remain IDE agnostic, with Eclipse and Netbeans supporting Maven projects without additional configuration.

## Architecture
FreeMarker is used as the template engine, with data persisted using JPA and Hibernate as the engine. Typical servlets
from Java EE Web 7 are used, but extended using a custom class called `ExtendedHttpServlet`. This results in less
boiler-plate code being required, as well as template settings and a hashmap for template data to be available. Pages can
be produced very fast with a huge amount of template control, such as changing the global layout.
