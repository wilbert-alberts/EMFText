package org.emftext.sdk.ui.jobs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.emftext.sdk.EMFTextSDKPlugin;
import org.emftext.sdk.IPluginDescriptor;
import org.emftext.sdk.codegen.AbstractCreatePluginJob;
import org.emftext.sdk.codegen.GenerationProblem;
import org.emftext.sdk.codegen.IProblemCollector;
import org.emftext.sdk.codegen.IResourceMarker;
import org.emftext.sdk.codegen.newproject.NewProjectGenerationContext;
import org.emftext.sdk.codegen.newproject.NewProjectParameters;
import org.emftext.sdk.codegen.newproject.creators.NewProjectContentsCreator;
import org.emftext.sdk.codegen.resource.GenerationContext;

/**
 * An Eclipse job that create a new EMFText project. This job runs when using the 
 * EMFText New Project Wizard.
 */
public class CreateNewEMFTextProjectJob extends AbstractCreatePluginJob {

	private NewProjectParameters parameters;
	private IResourceMarker resourceMarker;
	
	public CreateNewEMFTextProjectJob(NewProjectParameters parameters, IResourceMarker resourceMarker) {
		super();
		this.parameters = parameters;
		this.resourceMarker = resourceMarker;
	}

	public void run(IProgressMonitor monitor2) throws Exception {
		SubMonitor progress = SubMonitor.convert(monitor2, 100);

		final List<GenerationProblem> problems = new ArrayList<GenerationProblem>();
		final IProblemCollector problemCollector = new IProblemCollector() {
			
			public void addProblem(GenerationProblem problem) {
				problems.add(problem);
			}
		};
		final String newProjectName = parameters.getProjectName();
		IPluginDescriptor newProjectPlugin = new IPluginDescriptor() {

			public String getName() {
				return newProjectName;
			}
		};
		IProject project = createProject(progress.newChild(2), newProjectPlugin, parameters.getProjectName());
		NewProjectGenerationContext context = new NewProjectGenerationContext(new WorkspaceConnector(), problemCollector, project, newProjectPlugin, parameters);
		context.setMonitor(progress.newChild(92));
		context.setResourceMarker(resourceMarker);
		
		new NewProjectContentsCreator().create(newProjectPlugin, context, null, null);
		refresh(progress.newChild(2), project, "new project");
		GenerationContext generationContext = context.getGenerationContext();
		refresh(progress.newChild(2), getProject(generationContext.getResourcePlugin().getName()), "resource plug-in");
		refresh(progress.newChild(2), getProject(generationContext.getResourceUIPlugin().getName()), "resource UI plug-in");
		refresh(progress.newChild(2), getProject(generationContext.getAntlrPlugin().getName()), "ANTLR plug-in");
		
		// write problems to error log
		for (GenerationProblem problem : problems) {
			int severity = problem.getSeverity() == GenerationProblem.Severity.WARNING ? IStatus.WARNING : IStatus.ERROR;
			String message = problem.getMessage();
			EMFTextSDKPlugin.logProblem(severity, message, null);
		}
	}

	public IProject createProject(IProgressMonitor progress, IPluginDescriptor plugin, String pluginName) throws Exception {
		progress.beginTask("Creating project", 2);
		IProject getProject = getProject(pluginName);
		IProject project = getProject;
		if (!project.exists()) {
			project.create(new NullProgressMonitor());
		}
		project.open(new NullProgressMonitor());
		progress.worked(1);
		JavaCore.create(project);
		progress.worked(1);
		return project;
	}

	private IProject getProject(String pluginName) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(pluginName);
	}
}
