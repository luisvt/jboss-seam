/*******************************************************************************
 *    JBoss, Home of Professional Open Source
 *    Copyright 2006, JBoss Inc., and individual contributors as indicated
 *    by the @authors tag. See the copyright.txt in the distribution for a
 *    full listing of individual contributors.
 *   
 *    This is free software; you can redistribute it and/or modify it
 *    under the terms of the GNU Lesser General Public License as
 *    published by the Free Software Foundation; either version 2.1 of
 *    the License, or (at your option) any later version.
 *   
 *    This software is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *    Lesser General Public License for more details.
 *   
 *    You should have received a copy of the GNU Lesser General Public
 *    License along with this software; if not, write to the Free
 *    Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *    02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *******************************************************************************/
package org.jboss.seam.tool;

import java.io.IOException;
import java.util.HashMap;

import freemarker.template.TemplateException;

public class FaceletGenerator extends AbstractSeamGen {

	private String pageName;
	private String projectName;
	private String actionName;
   private String componentName;
	
	public FaceletGenerator() {};

	public FaceletGenerator(String[] args) throws IOException {
		this.projectName = args[1];
      this.actionName = args[2];
      this.pageName = args[3];
      //make the component name same as action name but first char lower case
      this.componentName = this.actionName.substring(0,1).toLowerCase() + this.actionName.substring(1);
	}
	
	private HashMap getModel() throws IOException {
		HashMap map = new HashMap();
		map.put("projectName", this.projectName);
		map.put("pageName", this.pageName);
		map.put("actionName", this.actionName);
      map.put("componentName", this.componentName);      
		return map;
	}

	public void newPage() throws IOException, TemplateException {
		logger.info("Generating a new Facelet Page");
		String pageTemplate = getFaceletPath( this.projectProps.getWorkspaceHome(),
				this.projectName, "template", this.projectProps.getWtp());
		String page = getFaceletPath( this.projectProps.getWorkspaceHome(),
				this.projectName, this.pageName, this.projectProps.getWtp());		

		generateFile("page.ftl", page, getModel());
	}	
	
	public void newActionPage() throws IOException, TemplateException {
		logger.info("Generating a new action Facelet Page");
		String pageTemplate = getFaceletPath( this.projectProps.getWorkspaceHome(),
				this.projectName, "template", this.projectProps.getWtp());
		String page = getFaceletPath( this.projectProps.getWorkspaceHome(),
				this.projectName, this.pageName, this.projectProps.getWtp());		

		generateFile("action-page.ftl", page, getModel());
	}	
	
   public void newFormPage() throws IOException, TemplateException {
      logger.info("Generating a new action Facelet Page");
      String pageTemplate = getFaceletPath( this.projectProps.getWorkspaceHome(),
            this.projectName, "template", this.projectProps.getWtp());
      String page = getFaceletPath( this.projectProps.getWorkspaceHome(),
            this.projectName, this.pageName, this.projectProps.getWtp());     

      generateFile("form-page.ftl", page, getModel());
   }  
   
   public void newConversationPage() throws IOException, TemplateException {
      logger.info("Generating a new conversation Facelet Page");
      String pageTemplate = getFaceletPath( this.projectProps.getWorkspaceHome(),
            this.projectName, "template", this.projectProps.getWtp());
      String page = getFaceletPath( this.projectProps.getWorkspaceHome(),
            this.projectName, this.pageName, this.projectProps.getWtp());     

      generateFile("conversation-page.ftl", page, getModel());
   }  
   
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

   public String getComponentName()
   {
      return componentName;
   }

   public void setComponentName(String componentName)
   {
      this.componentName = componentName;
   }
	
	
}
