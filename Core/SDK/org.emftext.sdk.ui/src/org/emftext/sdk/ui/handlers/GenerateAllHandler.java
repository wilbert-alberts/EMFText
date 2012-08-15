package org.emftext.sdk.ui.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.handlers.HandlerUtil;
import org.emftext.sdk.concretesyntax.resource.cs.mopp.CsMetaInformation;
import org.emftext.sdk.concretesyntax.resource.cs.ui.CsUIPlugin;
import org.emftext.sdk.ui.jobs.GenerateResourcePluginsJob;

public class GenerateAllHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection) {
			IStructuredSelection selection = ((IStructuredSelection) currentSelection);
			Iterator<?> it = selection.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				try {
					if(o instanceof IAdaptable){
						IAdaptable a = (IAdaptable) o;
						IResource adapter = (IResource) a.getAdapter(IResource.class);
						if(adapter != null){
							traverse(adapter);
						} else if(a instanceof IWorkingSet){
							IWorkingSet workingSet = (IWorkingSet) a;
							traverse(workingSet);
						}
					}
				} catch (CoreException e) {
					CsUIPlugin.logError("Exception while traversing selection", e);
				}
			}
		}
		return null;
	}

	private void traverse(IWorkingSet workingSet) throws CoreException {
		IAdaptable[] elements = workingSet.getElements();
		for (IAdaptable adaptable : elements) {
			IResource resource = (IResource) adaptable.getAdapter(IResource.class);
			if(resource != null){
				traverse(resource);
			}
		}
	}

	private void traverse(IResource resource) throws CoreException {
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			process(file);
		} else {
			resource.accept(new IResourceVisitor() {

				@Override
				public boolean visit(IResource resource) throws CoreException {
					process(resource);
					return true;
				}
			});
		}
	}

	private void process(IResource resource) throws CoreException {
		if (resource instanceof IFile) {
			final IFile file = (IFile) resource;
			String fileExtension = file.getFileExtension();
			Job job = null;
			if (fileExtension == null) {
				return;
			}
			if (fileExtension.equals(new CsMetaInformation().getSyntaxName())) {
				job = new GenerateResourcePluginsJob("Generating resource project for " + file.getName(), file);
			} else if (fileExtension.equals("genmodel")) {
				job = new Job("Generate metamodel code job") {

					@Override
					protected IStatus run(IProgressMonitor monitor) {
						URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
						ResourceSet rs = new ResourceSetImpl();
						rs.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap());
						Resource genModelResource = rs.getResource(uri, true);
						EList<EObject> contents = genModelResource.getContents();
						for (EObject eObject : contents) {
							if (eObject instanceof GenModel) {
								GenModel genModel = (GenModel) eObject;
								genModel.reconcile();
								genModel.setCanGenerate(true);
								Generator generator = new Generator();
								generator.setInput(genModel);
								String type = GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE;
								generator.generate(genModel, type, new BasicMonitor());
							}
						}
						return Status.OK_STATUS;
					}

				};
			}
			if (job != null) {
				job.setUser(true);
				job.schedule();
			}
		}
	}
	//
	//	@Override
	//	public boolean isEnabled() {
	//		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
	//		ISelection selection = selectionService.getSelection();
	//		if(selection instanceof IStructuredSelection){
	//			this.selection = (IStructuredSelection) selection;
	//			boolean enabled = true;
	//			Object[] objects = this.selection.toArray();
	//			for (Object object : objects) {
	//				if(!(object instanceof IResource) || !(object instanceof IWorkingSet)){
	//					enabled = false;
	//				}
	//			}
	//			return enabled;
	//		}
	//		return super.isEnabled();
	//	}

}
