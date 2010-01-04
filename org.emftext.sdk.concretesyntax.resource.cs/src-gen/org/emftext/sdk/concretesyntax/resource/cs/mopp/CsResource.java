/*******************************************************************************
 * Copyright (c) 2006-2010 
 * Software Technology Group, Dresden University of Technology
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany 
 *      - initial API and implementation
 ******************************************************************************/

package org.emftext.sdk.concretesyntax.resource.cs.mopp;

public class CsResource extends org.eclipse.emf.ecore.resource.impl.ResourceImpl implements org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource {
	
	public class ElementBasedTextDiagnostic implements org.emftext.sdk.concretesyntax.resource.cs.ICsTextDiagnostic {
		
		private final org.emftext.sdk.concretesyntax.resource.cs.ICsLocationMap locationMap;
		private final org.eclipse.emf.common.util.URI uri;
		private final org.eclipse.emf.ecore.EObject element;
		private final org.emftext.sdk.concretesyntax.resource.cs.ICsProblem problem;
		
		public ElementBasedTextDiagnostic(org.emftext.sdk.concretesyntax.resource.cs.ICsLocationMap locationMap, org.eclipse.emf.common.util.URI uri, org.emftext.sdk.concretesyntax.resource.cs.ICsProblem problem, org.eclipse.emf.ecore.EObject element) {
			super();
			this.uri = uri;
			this.locationMap = locationMap;
			this.element = element;
			this.problem = problem;
		}
		
		public java.lang.String getMessage() {
			return problem.getMessage();
		}
		
		public org.emftext.sdk.concretesyntax.resource.cs.ICsProblem getProblem() {
			return problem;
		}
		
		public java.lang.String getLocation() {
			return uri.toString();
		}
		
		public int getCharStart() {
			return Math.max(0, locationMap.getCharStart(element));
		}
		
		public int getCharEnd() {
			return Math.max(0, locationMap.getCharEnd(element));
		}
		
		public int getColumn() {
			return Math.max(0, locationMap.getColumn(element));
		}
		
		public int getLine() {
			return Math.max(0, locationMap.getLine(element));
		}
		
		public boolean wasCausedBy(org.eclipse.emf.ecore.EObject element) {
			if (this.element == null) {
				return false;
			}
			return this.element.equals(element);
		}
	}
	
	public class PositionBasedTextDiagnostic implements org.emftext.sdk.concretesyntax.resource.cs.ICsTextDiagnostic {
		
		private final org.eclipse.emf.common.util.URI uri;
		
		private int column;
		private int line;
		private int charStart;
		private int charEnd;
		private org.emftext.sdk.concretesyntax.resource.cs.ICsProblem problem;
		
		public PositionBasedTextDiagnostic(org.eclipse.emf.common.util.URI uri, org.emftext.sdk.concretesyntax.resource.cs.ICsProblem problem, int column, int line, int charStart, int charEnd) {
			
			super();
			this.uri = uri;
			this.column = column;
			this.line = line;
			this.charStart = charStart;
			this.charEnd = charEnd;
			this.problem = problem;
		}
		
		public org.emftext.sdk.concretesyntax.resource.cs.ICsProblem getProblem() {
			return problem;
		}
		
		public int getCharStart() {
			return charStart;
		}
		
		public int getCharEnd() {
			return charEnd;
		}
		
		public int getColumn() {
			return column;
		}
		
		public int getLine() {
			return line;
		}
		
		public java.lang.String getLocation() {
			return uri.toString();
		}
		
		public java.lang.String getMessage() {
			return problem.getMessage();
		}
		
		public boolean wasCausedBy(org.eclipse.emf.ecore.EObject element) {
			return false;
		}
	}
	
	private org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolverSwitch resolverSwitch;
	private org.emftext.sdk.concretesyntax.resource.cs.ICsLocationMap locationMap;
	private int proxyCounter = 0;
	private org.emftext.sdk.concretesyntax.resource.cs.ICsTextParser parser;
	private java.util.Map<java.lang.String, org.emftext.sdk.concretesyntax.resource.cs.ICsContextDependentURIFragment<? extends org.eclipse.emf.ecore.EObject>> internalURIFragmentMap = new java.util.HashMap<java.lang.String, org.emftext.sdk.concretesyntax.resource.cs.ICsContextDependentURIFragment<? extends org.eclipse.emf.ecore.EObject>>();
	
	public CsResource() {
		super();
		resetLocationMap();
	}
	
	public CsResource(org.eclipse.emf.common.util.URI uri) {
		super(uri);
		resetLocationMap();
	}
	
	protected void doLoad(java.io.InputStream inputStream, java.util.Map<?,?> options) throws java.io.IOException {
		java.lang.String encoding = null;
		java.io.InputStream actualInputStream = inputStream;
		java.lang.Object inputStreamPreProcessorProvider = null;
		if (options!=null) {
			inputStreamPreProcessorProvider = options.get(org.emftext.sdk.concretesyntax.resource.cs.ICsOptions.INPUT_STREAM_PREPROCESSOR_PROVIDER);
		}
		if (inputStreamPreProcessorProvider != null) {
			if (inputStreamPreProcessorProvider instanceof org.emftext.sdk.concretesyntax.resource.cs.ICsInputStreamProcessorProvider) {
				org.emftext.sdk.concretesyntax.resource.cs.ICsInputStreamProcessorProvider provider = (org.emftext.sdk.concretesyntax.resource.cs.ICsInputStreamProcessorProvider) inputStreamPreProcessorProvider;
				org.emftext.sdk.concretesyntax.resource.cs.mopp.CsInputStreamProcessor processor = provider.getInputStreamProcessor(inputStream);
				actualInputStream = processor;
				encoding = processor.getOutputEncoding();
			}
		}
		
		parser = getMetaInformation().createParser(actualInputStream, encoding);
		parser.setOptions(options);
		org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolverSwitch referenceResolverSwitch = getReferenceResolverSwitch();
		referenceResolverSwitch.setOptions(options);
		org.emftext.sdk.concretesyntax.resource.cs.ICsParseResult result = parser.parse();
		clearState();
		getContents().clear();
		if (result != null) {
			org.eclipse.emf.ecore.EObject root = result.getRoot();
			if (root != null) {
				getContents().add(root);
			}
			java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ICsCommand<org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource>> commands = result.getPostParseCommands();
			if (commands != null) {
				for (org.emftext.sdk.concretesyntax.resource.cs.ICsCommand<org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource>  command : commands) {
					command.execute(this);
				}
			}
		}
		getReferenceResolverSwitch().setOptions(options);
		if (getErrors().isEmpty()) {
			runPostProcessors(options);
		}
	}
	
	public void reload(java.io.InputStream inputStream, java.util.Map<?,?> options) throws java.io.IOException {
		try {
			isLoaded = false;
			java.util.Map<java.lang.Object, java.lang.Object> loadOptions = addDefaultLoadOptions(options);
			doLoad(inputStream, loadOptions);
		} catch (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsTerminateParsingException tpe) {
			// do nothing - the resource is left unchanged if this exception is thrown
		}
		isLoaded = true;
	}
	
	public void cancelReload() {
		org.emftext.sdk.concretesyntax.resource.cs.ICsTextParser parserCopy = parser;
		parserCopy.terminate();
	}
	
	protected void doSave(java.io.OutputStream outputStream, java.util.Map<?,?> options) throws java.io.IOException {
		org.emftext.sdk.concretesyntax.resource.cs.mopp.CsPrinter printer = new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsPrinter(outputStream, this);
		org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolverSwitch referenceResolverSwitch = getReferenceResolverSwitch();
		referenceResolverSwitch.setOptions(options);
		for(org.eclipse.emf.ecore.EObject root : getContents()) {
			printer.print(root);
		}
	}
	
	protected String getSyntaxName() {
		return "cs";
	}
	
	public org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolverSwitch getReferenceResolverSwitch() {
		if (resolverSwitch == null) {
			resolverSwitch = new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsReferenceResolverSwitch();
		}
		return resolverSwitch;
	}
	
	public org.emftext.sdk.concretesyntax.resource.cs.mopp.CsMetaInformation getMetaInformation() {
		return new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsMetaInformation();
	}
	
	protected void resetLocationMap() {
		locationMap = new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsLocationMap();
	}
	
	public void addURIFragment(java.lang.String internalURIFragment, org.emftext.sdk.concretesyntax.resource.cs.ICsContextDependentURIFragment<? extends org.eclipse.emf.ecore.EObject> uriFragment) {
		internalURIFragmentMap.put(internalURIFragment, uriFragment);
	}
	
	public <ContainerType extends org.eclipse.emf.ecore.EObject, ReferenceType extends org.eclipse.emf.ecore.EObject> void registerContextDependentProxy(org.emftext.sdk.concretesyntax.resource.cs.ICsContextDependentURIFragmentFactory<ContainerType, ReferenceType> factory, ContainerType container, org.eclipse.emf.ecore.EReference reference, java.lang.String id, org.eclipse.emf.ecore.EObject proxyElement) {
		int pos = -1;
		if (reference.isMany()) {
			pos = ((java.util.List<?>)container.eGet(reference)).size();
		}
		org.eclipse.emf.ecore.InternalEObject proxy = (org.eclipse.emf.ecore.InternalEObject) proxyElement;
		java.lang.String internalURIFragment = org.emftext.sdk.concretesyntax.resource.cs.ICsContextDependentURIFragment.INTERNAL_URI_FRAGMENT_PREFIX + (proxyCounter++) + "_" + id;
		org.emftext.sdk.concretesyntax.resource.cs.ICsContextDependentURIFragment<?> uriFragment = factory.create(id, container, reference, pos, proxy);
		proxy.eSetProxyURI(getURI().appendFragment(internalURIFragment));
		addURIFragment(internalURIFragment, uriFragment);
	}
	
	public org.eclipse.emf.ecore.EObject getEObject(String id) {
		if (internalURIFragmentMap.containsKey(id)) {
			org.emftext.sdk.concretesyntax.resource.cs.ICsContextDependentURIFragment<? extends org.eclipse.emf.ecore.EObject> uriFragment = internalURIFragmentMap.get(id);
			boolean wasResolvedBefore = uriFragment.isResolved();
			org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolveResult<? extends org.eclipse.emf.ecore.EObject> result = uriFragment.resolve();
			if (result == null) {
				//the resolving did call itself
				return null;
			}
			if (!wasResolvedBefore && !result.wasResolved()) {
				attachErrors(result, uriFragment.getProxy());
				return null;
			} else if (!result.wasResolved()) {
				return null;
			} else {
				//remove an error that might have been added by an earlier attempt
				removeDiagnostics(uriFragment.getProxy(), getErrors());
				//remove old warnings and attach new
				removeDiagnostics(uriFragment.getProxy(), getWarnings());
				attachWarnings(result, uriFragment.getProxy());
				org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceMapping<? extends org.eclipse.emf.ecore.EObject> mapping = result.getMappings().iterator().next();
				return getResultElement(uriFragment, mapping, uriFragment.getProxy(), result.getErrorMessage());
			}
		} else {
			return super.getEObject(id);
		}
	}
	
	private org.eclipse.emf.ecore.EObject getResultElement(org.emftext.sdk.concretesyntax.resource.cs.ICsContextDependentURIFragment<? extends org.eclipse.emf.ecore.EObject> uriFragment, org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceMapping<? extends org.eclipse.emf.ecore.EObject> mapping, org.eclipse.emf.ecore.EObject proxy, final java.lang.String errorMessage) {
		if (mapping instanceof org.emftext.sdk.concretesyntax.resource.cs.ICsURIMapping<?>) {
			org.eclipse.emf.common.util.URI uri = ((org.emftext.sdk.concretesyntax.resource.cs.ICsURIMapping<? extends org.eclipse.emf.ecore.EObject>)mapping).getTargetIdentifier();
			if (uri != null) {
				org.eclipse.emf.ecore.EObject result = null;
				try {
					result = this.getResourceSet().getEObject(uri, true);
				} catch (java.lang.Exception e) {
					//we can catch exceptions here, because EMF will try to resolve again and handle the exception
				}
				if (result == null || result.eIsProxy()) {
					//unable to resolve: attach error
					if (errorMessage == null) {
						assert(false);
					} else {
						addProblem(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsProblem(errorMessage, org.emftext.sdk.concretesyntax.resource.cs.CsEProblemType.ERROR), proxy);
					}
				}
				return result;
			}
			return null;
		} else if (mapping instanceof org.emftext.sdk.concretesyntax.resource.cs.ICsElementMapping<?>) {
			org.eclipse.emf.ecore.EObject element = ((org.emftext.sdk.concretesyntax.resource.cs.ICsElementMapping<? extends org.eclipse.emf.ecore.EObject>)mapping).getTargetElement();
			org.eclipse.emf.ecore.EReference reference = uriFragment.getReference();
			org.eclipse.emf.ecore.EReference oppositeReference = uriFragment.getReference().getEOpposite();
			if (!uriFragment.getReference().isContainment() && oppositeReference != null) {
				if (reference.isMany()) {
					org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList.ManyInverse<org.eclipse.emf.ecore.EObject> list = org.emftext.sdk.concretesyntax.resource.cs.util.CsCastUtil.cast(element.eGet(oppositeReference, false));										//avoids duplicate entries in the reference caused by adding to the oppositeReference 
					list.basicAdd(uriFragment.getContainer(),null);
				} else {
					uriFragment.getContainer().eSet(uriFragment.getReference(), element);
				}
			}
			return element;
		} else {
			assert(false);
			return null;
		}
	}
	
	private void removeDiagnostics(org.eclipse.emf.ecore.EObject proxy, java.util.List<org.eclipse.emf.ecore.resource.Resource.Diagnostic> diagnostics) {
		// remove errors/warnings from resource
		for (org.eclipse.emf.ecore.resource.Resource.Diagnostic errorCand : new org.eclipse.emf.common.util.BasicEList<org.eclipse.emf.ecore.resource.Resource.Diagnostic>(diagnostics)) {
			if (errorCand instanceof org.emftext.sdk.concretesyntax.resource.cs.ICsTextDiagnostic) {
				if (((org.emftext.sdk.concretesyntax.resource.cs.ICsTextDiagnostic) errorCand).wasCausedBy(proxy)) {
					diagnostics.remove(errorCand);
				}
			}
		}
	}
	
	private void attachErrors(org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolveResult<?> result, org.eclipse.emf.ecore.EObject proxy) {
		// attach errors to resource
		assert result != null;
		final java.lang.String errorMessage = result.getErrorMessage();
		if (errorMessage == null) {
			assert(false);
		} else {
			addProblem(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsProblem(errorMessage, org.emftext.sdk.concretesyntax.resource.cs.CsEProblemType.ERROR), proxy);
		}
	}
	
	private void attachWarnings(org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolveResult<? extends org.eclipse.emf.ecore.EObject> result, org.eclipse.emf.ecore.EObject proxy) {
		assert result != null;
		assert result.wasResolved();
		if (result.wasResolved()) {
			for (org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceMapping<? extends org.eclipse.emf.ecore.EObject> mapping : result.getMappings()) {
				final java.lang.String warningMessage = mapping.getWarning();
				if (warningMessage == null) {
					continue;
				}
				addProblem(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsProblem(warningMessage, org.emftext.sdk.concretesyntax.resource.cs.CsEProblemType.ERROR), proxy);
			}
		}
	}
	
	// Extends the super implementation by clearing all information about element positions.
	protected void doUnload() {
		super.doUnload();
		clearState();
	}
	
	protected void runPostProcessors(java.util.Map<?, ?> loadOptions) {
		if (loadOptions == null) {
			return;
		}
		java.lang.Object resourcePostProcessorProvider = loadOptions.get(org.emftext.sdk.concretesyntax.resource.cs.ICsOptions.RESOURCE_POSTPROCESSOR_PROVIDER);
		if (resourcePostProcessorProvider != null) {
			if (resourcePostProcessorProvider instanceof org.emftext.sdk.concretesyntax.resource.cs.ICsResourcePostProcessorProvider) {
				((org.emftext.sdk.concretesyntax.resource.cs.ICsResourcePostProcessorProvider) resourcePostProcessorProvider).getResourcePostProcessor().process(this);
			} else if (resourcePostProcessorProvider instanceof java.util.Collection<?>) {
				java.util.Collection<?> resourcePostProcessorProviderCollection = (java.util.Collection<?>) resourcePostProcessorProvider;
				for (Object processorProvider : resourcePostProcessorProviderCollection) {
					if (processorProvider instanceof org.emftext.sdk.concretesyntax.resource.cs.ICsResourcePostProcessorProvider) {
						org.emftext.sdk.concretesyntax.resource.cs.ICsResourcePostProcessorProvider csProcessorProvider = (org.emftext.sdk.concretesyntax.resource.cs.ICsResourcePostProcessorProvider) processorProvider;
						org.emftext.sdk.concretesyntax.resource.cs.ICsResourcePostProcessor postProcessor = csProcessorProvider.getResourcePostProcessor();
						try {
							postProcessor.process(this);
						} catch (java.lang.Exception e) {
							org.emftext.sdk.concretesyntax.resource.cs.mopp.CsPlugin.logError("Exception while running a post-processor.", e);
						}
					}
				}
			}
		}
	}
	
	public void load(java.util.Map<?, ?> options) throws java.io.IOException {
		java.util.Map<java.lang.Object, java.lang.Object> loadOptions = addDefaultLoadOptions(options);
		super.load(loadOptions);
	}
	
	public void setURI(org.eclipse.emf.common.util.URI uri) {
		//because of the context dependent proxy resolving it is 
		//essential to resolve all proxies before the URI is changed
		//which can cause loss of object identities
		org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(this);
		super.setURI(uri);
	}
	
	public org.emftext.sdk.concretesyntax.resource.cs.ICsLocationMap getLocationMap() {
		return locationMap;
	}
	
	public void addProblem(org.emftext.sdk.concretesyntax.resource.cs.ICsProblem problem, org.eclipse.emf.ecore.EObject element) {
		getDiagnostics(problem.getType()).add(new ElementBasedTextDiagnostic(locationMap, getURI(), problem, element));
	}
	
	public void addProblem(org.emftext.sdk.concretesyntax.resource.cs.ICsProblem problem, int column, int line, int charStart, int charEnd) {
		getDiagnostics(problem.getType()).add(new PositionBasedTextDiagnostic(getURI(), problem, column, line, charStart, charEnd));
	}
	
	public void addError(java.lang.String message, org.eclipse.emf.ecore.EObject cause) {
		addProblem(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsProblem(message, org.emftext.sdk.concretesyntax.resource.cs.CsEProblemType.ERROR), cause);
	}
	
	public void addWarning(java.lang.String message, org.eclipse.emf.ecore.EObject cause) {
		addProblem(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsProblem(message, org.emftext.sdk.concretesyntax.resource.cs.CsEProblemType.WARNING), cause);
	}
	
	private java.util.List<org.eclipse.emf.ecore.resource.Resource.Diagnostic> getDiagnostics(org.emftext.sdk.concretesyntax.resource.cs.CsEProblemType type) {
		if (type == org.emftext.sdk.concretesyntax.resource.cs.CsEProblemType.ERROR) {
			return getErrors();
		} else {
			return getWarnings();
		}
	}
	
	protected java.util.Map<java.lang.Object, java.lang.Object> addDefaultLoadOptions(java.util.Map<?, ?> loadOptions) {
		java.util.Map<java.lang.Object, java.lang.Object> loadOptionsCopy = org.emftext.sdk.concretesyntax.resource.cs.util.CsMapUtil.copySafelyToObjectToObjectMap(loadOptions);
		if (org.eclipse.core.runtime.Platform.isRunning()) {
			// find default load option providers
			org.eclipse.core.runtime.IExtensionRegistry extensionRegistry = org.eclipse.core.runtime.Platform.getExtensionRegistry();
			org.eclipse.core.runtime.IConfigurationElement configurationElements[] = extensionRegistry.getConfigurationElementsFor(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsPlugin.EP_DEFAULT_LOAD_OPTIONS_ID);
			for (org.eclipse.core.runtime.IConfigurationElement element : configurationElements) {
				try {
					org.emftext.sdk.concretesyntax.resource.cs.ICsOptionProvider provider = (org.emftext.sdk.concretesyntax.resource.cs.ICsOptionProvider) element.createExecutableExtension("class");
					final java.util.Map<?, ?> options = provider.getOptions();
					final java.util.Collection<?> keys = options.keySet();
					for (java.lang.Object key : keys) {
						addLoadOption(loadOptionsCopy, key, options.get(key));
					}
				} catch (org.eclipse.core.runtime.CoreException ce) {
					org.emftext.sdk.concretesyntax.resource.cs.mopp.CsPlugin.logError("Exception while getting default options.", ce);
				}
			}
		}
		return loadOptionsCopy;
	}
	
	// Adds a new key,value pair to the list of options. If there
	// is already an option with the same key, the two values are 
	// collected in a list.
	private void addLoadOption(java.util.Map<java.lang.Object, java.lang.Object> options,java.lang.Object key, java.lang.Object value) {
		// check if there is already an option set
		if (options.containsKey(key)) {
			java.lang.Object currentValue = options.get(key);
			if (currentValue instanceof java.util.List<?>) {
				// if the current value is a list, we add the new value to
				// this list
				java.util.List<?> currentValueAsList = (java.util.List<?>) currentValue;
				java.util.List<java.lang.Object> currentValueAsObjectList = org.emftext.sdk.concretesyntax.resource.cs.util.CsListUtil.copySafelyToObjectList(currentValueAsList);
				if (value instanceof java.util.Collection<?>) {
					currentValueAsObjectList.addAll((java.util.Collection<?>) value);
				} else {
					currentValueAsObjectList.add(value);
				}
				options.put(key, currentValueAsObjectList);
			} else {
				// if the current value is not a list, we create a fresh list
				// and add both the old (current) and the new value to this list
				java.util.List<java.lang.Object> newValueList = new java.util.ArrayList<java.lang.Object>();
				newValueList.add(currentValue);
				if (value instanceof java.util.Collection<?>) {
					newValueList.addAll((java.util.Collection<?>) value);
				} else {
					newValueList.add(value);
				}
				options.put(key, newValueList);
			}
		} else {
			options.put(key, value);
		}
	}
	
	// Extends the super implementation by clearing all information about element positions.
	protected void clearState() {
		//clear concrete syntax information
		resetLocationMap();
		internalURIFragmentMap.clear();
		getErrors().clear();
		getWarnings().clear();
		proxyCounter = 0;
		resolverSwitch = null;
	}
	
	public org.eclipse.emf.common.util.EList<org.eclipse.emf.ecore.EObject> getContents() {
		return new org.emftext.sdk.concretesyntax.resource.cs.util.CsCopiedEObjectInternalEList((org.eclipse.emf.ecore.util.InternalEList<org.eclipse.emf.ecore.EObject>) super.getContents());
	}
	
	public org.eclipse.emf.common.util.EList<org.eclipse.emf.ecore.resource.Resource.Diagnostic> getWarnings() {
		return new org.emftext.sdk.concretesyntax.resource.cs.util.CsCopiedEList<org.eclipse.emf.ecore.resource.Resource.Diagnostic>(super.getWarnings());
	}
	
	public org.eclipse.emf.common.util.EList<org.eclipse.emf.ecore.resource.Resource.Diagnostic> getErrors() {
		return new org.emftext.sdk.concretesyntax.resource.cs.util.CsCopiedEList<org.eclipse.emf.ecore.resource.Resource.Diagnostic>(super.getErrors());
	}
	
}
