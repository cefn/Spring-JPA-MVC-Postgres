<!DOCTYPE html
  PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <link rel="stylesheet" type="text/css" href="style.css"></link>
   </head>
   <body>
      <div class="content">
      	 <#include "header.ftl"/>
      	 <#include "/menu.ftl"/>
         <div class="page">
         	<div>
		         <#if content??>
			     		${content}
		     	 <#else/>
			     		No content available.
		     	</#if>
         	</div>
         </div>
         <#if sidebar??>
	         <div class="menu">
	         	<div>
		         	${sidebar}
	         	</div>
	         </div>
         </#if>
      	 <#include "footer.ftl"/>         
      </div>
   </body>
</html>