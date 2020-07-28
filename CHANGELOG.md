Changelog
============

-  Change the finish dialog to show more simplified execution result
-  Enable users to rerun a process once it has finished
-  Fix schema verification for Oracle
-  Fix common paramers: traceEnabled, logFile...
-  Update to ili2db v4.4.3. Main changes:
   -  ili2ora: add tablespace support (#335)
   -  fix Export with empty geometry fails with NPE (#338)
   -  Set constraint into structure is not allowed
   -  See more: ili2db [changelog](https://github.com/claeis/ili2db/blob/stable/docs/CHANGELOG.txt)

v1.3.3 (2020-03-03)
------------------------------

-	Workaround: Add checkbox to skip schema verification for Oracle

v1.3.2 (2020-02-25)
------------------------------

-	Add support for Oracle tablespace
-	Fix models search and sort models, topics, baskets and datasets (workaround resolved)

v1.3.1 (2019-12-19)
------------------------------

-	Add support for Oracle service
-	Workaround: the textbox of model is now editable because the model list load failed 
-	Code refactoring

v1.3.0 (2019-10-16)
------------------------------

-   Updated to ili2db4 (v4.3.0)
-   ilivalidator v1.11.1 
-   Added option --createEnumTabsWithId
-   Added option --disableRounding
-   UML/INTERLIS Editor v3.6.6