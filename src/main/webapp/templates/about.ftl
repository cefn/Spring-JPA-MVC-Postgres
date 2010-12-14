<#include "defaults.ftl" />

<#assign content>
	<h2>About this project</h2>	       
	<p>It's designed just to provide a proof of concept of all the following technologies combined</p>
	<table>
		<tr><td>Dependency Injection</td><td>google.com's Guice Framework</td></tr>
		<tr><td>Persistence</td><td>JPA backed by Hibernate and Postgres, wired by persistence.xml and Guice-persistence</td></tr>
		<tr><td>Web Dynamic and Static Hosting</td><td>eclipse.org's Jetty Servlet Engine, loaded by Java invocation</td></tr>
		<tr><td>Web Routing</td><td>Guice-servlets</td></tr>
		<tr><td>Web Templating</td><td>Freemarker</td></tr>
	</table>
	<p>
		The codebase is modular and dynamically plugged, supporting unit testing and reuse.
	</p>
</#assign>

<#assign sidebar>
	<p>
		At present, the following is only implemented to a cursory or no degree, but 
		full support for these aspects is anticipated.
	</p>
	<table>
		<tr><td>Scheduling</td><td>java.util.Timer</td></tr>
		<tr><td>Forms and ActionBeans</td><td>Stripes</td></tr>
	</table>		
</#assign>

<@serve/>